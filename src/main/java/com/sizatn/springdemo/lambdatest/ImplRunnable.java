package com.sizatn.springdemo.lambdatest;

/**
 * 
 * @desc 实现Runnable线程案例
 * @author sizatn
 * @date Dec 19, 2016
 */
public class ImplRunnable {

	/*
	 * (params) -> expression 
	 * (params) -> statement 
	 * (params) -> { statements }
	 */

	public static void main(String[] args) {
		
		// Before Java 8
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Before Java8 ");
			}
		}).start();

		// In Java 8
		new Thread(() -> System.out.println("In Java8!")).start();
		
	}

}
