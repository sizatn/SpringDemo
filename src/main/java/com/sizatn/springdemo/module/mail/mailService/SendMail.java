package com.sizatn.springdemo.module.mail.mailService;

import java.io.File;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 
 * @desc 
 * @author sizatn
 * @date Jun 10, 2017
 */
@Service("sendMail")
public class SendMail {
	
	private JavaMailSenderImpl mailSender;
	
	@Resource(name="simpleMailMessage")
	private SimpleMailMessage simpleMailMessage;
	
	public void SimpleMail(String subject, String text) {
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		mailSender.send(simpleMailMessage);
	}
	
	public void AttachmentMail(String subject, String text, String fileName, File file) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mmhelper;
		try {
			mmhelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			mmhelper.setFrom(simpleMailMessage.getFrom());
			mmhelper.setSubject(subject);
			mmhelper.setText(text);
			mmhelper.setTo(simpleMailMessage.getTo());
			mmhelper.addAttachment(fileName, file);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mailSender.send(mimeMessage);
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	
}
