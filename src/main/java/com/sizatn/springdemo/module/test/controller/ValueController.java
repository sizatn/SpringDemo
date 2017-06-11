package com.sizatn.springdemo.module.test.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Configuration
@RequestMapping(value = "/")
@PropertySource("classpath:value.properties")
public class ValueController {
	
	@Value("${datasource.driver}")
	private String dataSourceDriver;

	@Value("${datasource.url}")
	private String dataSourceUrl;

	@RequestMapping(value = "value", method = RequestMethod.GET)
	public String value(HttpServletResponse response, Model model) {

		model.addAttribute("dataSourceDriver", dataSourceDriver);
		model.addAttribute("dataSourceUrl", dataSourceUrl);

		return "value";
	}

}
