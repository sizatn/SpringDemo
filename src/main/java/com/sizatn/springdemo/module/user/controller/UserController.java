package com.sizatn.springdemo.module.user.controller;

import java.sql.Timestamp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sizatn.springdemo.module.user.entity.User;

@Controller
@RequestMapping(value = "/")
public class UserController {

	@Value("#{sd_prop['redis.host']}")
	private String redisHost;

	@Value("${redis.port}")
	private String redisPort;

	@Value("${helloWorld}")
	private String helloWorld;

	@RequestMapping(value = { "", "user" }, method = RequestMethod.GET)
	public String hello(@CookieValue(value = "refreshCount", defaultValue = "0") Long refreshCount,
			@CookieValue(value = "SESSION", defaultValue = "0") String sessionid, HttpServletResponse response,
			Model model) {

		// increment refresh count
		refreshCount++;

		// create cookie and set it in response
		Cookie cookie = new Cookie("refreshCount", refreshCount.toString());
		response.addCookie(cookie);

		model.addAttribute("sessionId", sessionid);
		model.addAttribute("redisHost", redisHost);
		model.addAttribute("redisPort", redisPort);
		model.addAttribute("helloWorld", helloWorld);

		// render hello.jsp page
		return "hello";
	}

	@ResponseBody
	@RequestMapping(value = "getUser")
	public User getUser(HttpServletResponse response) {
		User user = new User();
		user.setUserName("aa");
		user.setPassword("password");
		user.setCreateDate(new Timestamp(System.currentTimeMillis()));
		return user;
	}

}
