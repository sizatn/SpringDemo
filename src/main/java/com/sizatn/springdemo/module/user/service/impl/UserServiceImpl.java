package com.sizatn.springdemo.module.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sizatn.springdemo.common.database.DataBaseSwitcher;
import com.sizatn.springdemo.module.user.entity.User;
import com.sizatn.springdemo.module.user.mapper.MainUserMapper;
import com.sizatn.springdemo.module.user.mapper.TargetUserMapper;
import com.sizatn.springdemo.module.user.service.UserService;

/**
 * 
 * @desc sync user table from main database to target database with manual switching database or auto(spring-aop) switching database
 * @author sizatn
 * @date Jun 10, 2017
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	private static final Log log = LogFactory.getLog(UserServiceImpl.class);
	
	private static int runOneTimesCount = 0;
	private String oneTimeRun;
	private String run;

	@Resource
	private MainUserMapper mainUserMapper;
	
	@Resource
	private TargetUserMapper targetUserMapper;
	
	@Override
	public void execute() {
		if ( run.equals("false")) {
			return;
		}
		
		if (oneTimeRun.equals("true") && runOneTimesCount++ > 0) {
			return;
		}
		
		int updateCount = 0;
		int insertCount = 0;
		
		/*
		 * manual switch to main database
		 * 
		 * =========================================================================================================
		 * === comment -->DataBaseSwitcher.setDbType("dataSource")<-- if use auto(spring-aop) switching database ===
		 * =========================================================================================================
		 */
		DataBaseSwitcher.setDbType("dataSource");
		
		List<User> mainUserList = mainUserMapper.selectMainTable();
		log.info("count of main user record: " + mainUserList.size());
		
		/*
		 * manual switch to target database
		 * 
		 * ===========================================================================================================
		 * = comment -->DataBaseSwitcher.setDbType("targetDataSource")<-- if use auto(spring-aop) switching database =
		 * ===========================================================================================================
		 */
		DataBaseSwitcher.setDbType("targetDataSource");
		
		List<User> targetUserList = targetUserMapper.selectTargetTable();
		log.info("count of target user record: " + targetUserList.size());
		
		List<String> targetUserNoList = new ArrayList<String>(targetUserList.size());
		for (User user : targetUserList) {
			targetUserNoList.add(user.getUserNo());
		}
		
		if (mainUserList.size() != 0) {
			for (User user : mainUserList) {
				if (targetUserNoList.contains(user.getUserNo())) {
					log.info(user.getUserNo() + " need to update");
					targetUserMapper.updateTargetTable(user);
					updateCount++;
				} else {
					log.info(user.getUserNo() + " is a new record");
					targetUserMapper.insertTargetTable(user);
					targetUserNoList.add(user.getUserNo());
					insertCount++;
				}
			}
			log.info("count is " + mainUserList.size() + ", update count:" + updateCount + " insert count:" + insertCount);
		} else {
			log.info("no data for sync");
		}
	}

	public String getOneTimeRun() {
		return oneTimeRun;
	}

	public void setOneTimeRun(String oneTimeRun) {
		this.oneTimeRun = oneTimeRun;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

}
