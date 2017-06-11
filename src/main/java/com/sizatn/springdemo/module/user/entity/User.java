package com.sizatn.springdemo.module.user.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @desc user model
 * @author sizatn
 * @date Jun 10, 2017
 */
public class User implements Serializable {

	private static final long serialVersionUID = -1666005991691679694L;
	
	private int id;
	private String userNo;
	private String userName;
	private String password;
	private String gender;	// male:"0" female:"1"
	private String phone;
	private String mobile;
	private String enabled;	// yes:"0" no:"1"
	private Timestamp createDate;
	private Timestamp updateDate;

	public User() {
		
	}
	
	public User(String userNo, String userName) {
		this.userNo = userNo;
		this.userName = userName;
	}

	public User(int id, String userNo, String userName, String password, String gender, String phone, String mobile,
			String enabled, Timestamp createDate, Timestamp updateDate) {
		this.id = id;
		this.userNo = userNo;
		this.userName = userName;
		this.password = password;
		this.gender = gender;
		this.phone = phone;
		this.mobile = mobile;
		this.enabled = enabled;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public User(String userName, String password, Timestamp createDate) {
		this.userName = userName;
		this.password = password;
		this.createDate = createDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
}
