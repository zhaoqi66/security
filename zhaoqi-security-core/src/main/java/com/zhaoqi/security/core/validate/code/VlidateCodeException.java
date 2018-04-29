package com.zhaoqi.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

public class VlidateCodeException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VlidateCodeException(String msg) {
		super(msg);
		
	}

}
