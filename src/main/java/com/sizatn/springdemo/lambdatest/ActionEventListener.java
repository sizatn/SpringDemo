package com.sizatn.springdemo.lambdatest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * 
 * @desc 实现事件处理
 * @author sizatn
 * @date Dec 19, 2016
 */
public class ActionEventListener {

	public static void main(String[] args) {

		// Before Java 8
		JButton show = new JButton("Show");
		show.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("without lambda expression is boring");
			}
		});

		// Java 8 way
		show.addActionListener((e) -> {
			System.out.println("Action !! Lambda expressions Rocks");
		});
	}

}
