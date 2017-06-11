package com.sizatn.springdemo.common.utils;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.sizatn.springdemo.module.user.entity.User;

/**
 * Unit test for simple App.
 */
public class CollectionsUtilsTest {

	@Test
	public void test() {
		
		List<User> userList = Lists.newArrayList();
		userList.add(new User("10001", "aa"));
		userList.add(new User("10002", "bb"));
		userList.add(new User("10003", "cc"));
		userList.add(new User("10004", "dd"));
		
		List<User> userList1 = Lists.newArrayList();
		userList1.add(new User("10003", "cc"));
		userList1.add(new User("10004", "dd"));
		userList1.add(new User("10005", "ee"));
		userList1.add(new User("10007", "gg"));
		
		System.out.println("extractToMap: " + CollectionsUtils.extractToMap(userList, "userNo", "userName").toString());
		System.out.println("extractToList: " + CollectionsUtils.extractToList(userList, "userNo"));
		System.out.println("extractToString: " + CollectionsUtils.extractToString(userList, "userName", ", "));
		System.out.println("convertToString: " + CollectionsUtils.convertToString(CollectionsUtils.extractToList(userList, "userNo"), ", "));
		System.out.println("convertToString: " + CollectionsUtils.convertToString(CollectionsUtils.extractToList(userList, "userNo"), "<userNo>", "</userNo>"));
		System.out.println("isEmpty: " + CollectionsUtils.isEmpty(userList));
		System.out.println("getFirst: " + ToStringBuilder.reflectionToString(CollectionsUtils.getFirst(userList)));
		System.out.println("getLast: " + ToStringBuilder.reflectionToString(CollectionsUtils.getLast(userList)));
		System.out.println("union: " + CollectionsUtils.union(userList, userList1));
		System.out.println("subtract: " + CollectionsUtils.subtract(userList, userList1));
		System.out.println("intersection: " + CollectionsUtils.intersection(userList, userList1));
	}

}
