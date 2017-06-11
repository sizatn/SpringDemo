package com.sizatn.springdemo.lambdatest;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @desc 使用Lambda实现Map 和 Reduce
 * @author sizatn
 * @date Dec 19, 2016
 */
public class MapAndReduce {

	public static void main(String[] args) {

		// applying 12% VAT on each purchase

		// Without lambda expressions:
		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		for (Integer cost : costBeforeTax) {
			double price = cost + .12 * cost;
			System.out.println(price);
		}

		// With Lambda expression:
		costBeforeTax.stream().map((cost) -> cost + .12 * cost).forEach(System.out::println);

		double total = 0;
		for (Integer cost : costBeforeTax) {
			double price = cost + .12 * cost;
			total = total + price;

		}
		System.out.println("Total : " + total);

		// New way:
		double bill = costBeforeTax.stream().map((cost) -> cost + .12 * cost).reduce((sum, cost) -> sum + cost).get();
		System.out.println("Total : " + bill);

	}

}
