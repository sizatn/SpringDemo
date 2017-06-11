package com.sizatn.springdemo.common.utils;

import org.junit.Test;

public class Md5UtilTest {

	@Test
	public void test() {
		String md5String = Md5Util.getMd5("87456");
		System.out.println(md5String.length());
		System.out.println(md5String);
	}

}
