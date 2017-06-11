package com.sizatn.springdemo.common.utils;

import org.junit.Test;

public class MacUtilsTest {

	@Test
	public void testGetOSName() {
		System.out.println(MacUtils.getOSName());
	}

	@Test
	public void testGetUnixMACAddress() {
		System.out.println(MacUtils.getUnixMACAddress());
	}

}
