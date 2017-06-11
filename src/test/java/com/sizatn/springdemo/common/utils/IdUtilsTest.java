package com.sizatn.springdemo.common.utils;

import org.junit.Test;

public class IdUtilsTest {

	@Test
	public void testUuid() {
		System.out.println(IdUtils.uuid());
	}

	@Test
	public void testRandomLong() {
		System.out.println(IdUtils.randomLong());
	}

	@Test
	public void testRandomBase62() {
		System.out.println(IdUtils.randomBase62(32));
	}

}
