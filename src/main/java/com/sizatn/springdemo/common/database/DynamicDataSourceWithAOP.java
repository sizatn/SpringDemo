package com.sizatn.springdemo.common.database;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.sizatn.springdemo.module.user.mapper.MainUserMapper;
import com.sizatn.springdemo.module.user.mapper.TargetUserMapper;

/**
 * 
 * @desc 
 * @author sizatn
 * @date Jun 10, 2017
 */
@Aspect
@Component
public class DynamicDataSourceWithAOP {
	
	/*
	 * ===============================================================================================
	 * = comment -->@Around(value = "execution(* *.*Table(..))")<-- if use manual switching database =
	 * ===============================================================================================
	 */
//	@Around(value = "execution(* *.*Table(..))")
	public Object doAround(ProceedingJoinPoint jp) throws Throwable {
		if (jp.getTarget() instanceof MainUserMapper) {
			DataBaseSwitcher.setDbType("dataSource");
		} else if (jp.getTarget() instanceof TargetUserMapper) {
			DataBaseSwitcher.setDbType("targetDataSource");
		}
		return jp.proceed();
	}

}
