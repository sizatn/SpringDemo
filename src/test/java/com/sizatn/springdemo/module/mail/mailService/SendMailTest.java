package com.sizatn.springdemo.module.mail.mailService;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:spring-context-mail.xml"})
public class SendMailTest {
	
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void test() {
		SendMail sendMail = (SendMail) applicationContext.getBean(SendMail.class);
		sendMail.SimpleMail("Spring SMTP Mail Subject", "Spring SMTP Mail Text");
		sendMail.AttachmentMail("Spring SMTP Mail Subject", "Spring SMTP Mail Text", "index.jpg", new File("/Users/sizatn/Pictures/index.jpg"));
		
	}

}
