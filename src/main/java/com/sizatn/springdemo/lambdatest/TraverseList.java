package com.sizatn.springdemo.lambdatest;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @desc 遍历List集合
 * @author sizatn
 * @date Dec 19, 2016
 */
public class TraverseList {

	public static void main(String[] args) {

		// Before Java 8
		List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
		for (String feature : features) {
			System.out.println(feature);
		}

		// In Java 8
		features.forEach(n -> System.out.println(n));

		// Even better use Method reference feature of Java 8
		// method reference is denoted by :: (double colon) operator
		// looks similar to score resolution operator of C++
		features.forEach(System.out::println);
	}

}
