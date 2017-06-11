package com.sizatn.springdemo.lambdatest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 
 * @desc java.util.function.Predicate提供and(), or() 和 xor()可以进行逻辑操作
 * @author sizatn
 * @date Dec 19, 2016
 */
public class FunctionPredicate {

	public static void main(String[] args) {
		
		List<String> names = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

		// We can even combine Predicate using and(), or() And xor() logical
		// functions for example to find names, which starts with J and four letters long,
		// you can pass combination of two Predicate
		Predicate<String> startsWithJ = (str) -> str.startsWith("J");
		Predicate<String> fourLetterLong = (n) -> n.length() == 4;
		
		names.stream().filter(startsWithJ.and(fourLetterLong))
			.forEach((n) -> System.out.print("Name, which starts with 'J' and four letter long is : " + n));

		
	}
	
}
