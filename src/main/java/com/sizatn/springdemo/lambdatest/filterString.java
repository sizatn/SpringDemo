package com.sizatn.springdemo.lambdatest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @desc 通过filtering 创建一个字符串String的集合
 * @author sizatn
 * @date Dec 19, 2016
 */
public class filterString {

	public static void main(String[] args) {

		// Create a List with String more than 2 characters
		List<String> strList = Arrays.asList("abc", "bcd", "defg", "jk");
		List<String> filtered = strList.stream().filter(x -> x.length() > 2).collect(Collectors.toList());
		
		System.out.printf("Original List : %s, filtered list : %s %n", strList, filtered);

	}

}
