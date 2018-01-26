package com.sizatn.springdemo.common.utils;

import org.junit.Test;

public class IdUtilsTest {

	@Test
	public void testUuid() {
		System.out.println(IDUtils.uuid());
	}

	@Test
	public void testRandomLong() {
		System.out.println(IDUtils.randomLong());
	}

	@Test
	public void testRandomBase62() {
		System.out.println(IDUtils.randomBase62(32));
	}

}
