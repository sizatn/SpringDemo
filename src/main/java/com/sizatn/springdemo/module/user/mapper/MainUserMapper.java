package com.sizatn.springdemo.module.user.mapper;

import java.util.List;

import com.sizatn.springdemo.module.user.entity.User;

/**
 * 
 * @desc interface for sync main user table
 * @author sizatn
 * @date Jun 10, 2017
 */
public interface MainUserMapper {
	
	/**
	 * @return list
	 * @desc query main table
	 * @author sizatn
	 * @date Jun 10, 2017
	 */
	public List<User> selectMainTable();

}
