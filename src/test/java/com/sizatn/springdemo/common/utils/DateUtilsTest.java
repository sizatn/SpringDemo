package com.sizatn.springdemo.common.utils;

import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void test() {
		System.out.println(DateUtils.getDate());
		System.out.println(DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}

}
