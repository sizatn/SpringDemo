package com.sizatn.springdemo.common.utils;

import java.util.Random;

/**
 * 
 * @desc 字母数字字符串工具类
 * @author sizatn
 * @date Jan 26, 2018
 */
public class AlphabetNumStrUtils {

	public static String getRandomString(int length) {
		String str = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(62);
			sb.append(str.charAt(num));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String str = AlphabetNumStrUtils.getRandomString(32);
		System.out.println(str);
	}

}
