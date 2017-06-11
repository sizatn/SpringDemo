package com.sizatn.springdemo.module.user.mapper;

import java.util.List;

import com.sizatn.springdemo.module.user.entity.User;

/**
 * 
 * @desc interface for sync target user table
 * @author sizatn
 * @date Jun 10, 2017
 */
public interface TargetUserMapper {

	/**
	 * @return list
	 * @desc query target table
	 * @author sizatn
	 * @date Jun 10, 2017
	 */
	public List<User> selectTargetTable();

	/**
	 * @param user
	 * @desc update target table
	 * @author sizatn
	 * @date Jun 10, 2017
	 */
	public void updateTargetTable(User user);
	
	/**
	 * @param map
	 * @desc insert new date to target table
	 * @author sizatn
	 * @date Jun 10, 2017
	 */
	public void insertTargetTable(User user);
	
}
