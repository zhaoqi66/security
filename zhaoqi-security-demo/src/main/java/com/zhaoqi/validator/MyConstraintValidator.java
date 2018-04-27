package com.zhaoqi.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhaoqi.service.HelloService;

public class MyConstraintValidator implements ConstraintValidator<MyConstraint, String> {

	@Autowired
	private HelloService HelloService;
	
	@Override
	public void initialize(MyConstraint constraintAnnotation) {
		System.out.println("my init ");
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		HelloService.greeting("tom");
		System.out.println(value);
		return false;
	}

}
