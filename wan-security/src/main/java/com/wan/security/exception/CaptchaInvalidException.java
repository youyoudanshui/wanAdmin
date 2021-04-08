package com.wan.security.exception;

import org.springframework.security.core.AuthenticationException;

public class CaptchaInvalidException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CaptchaInvalidException(String msg) {
		super(msg);
	}
	
	public CaptchaInvalidException(String msg, Throwable t) {
		super(msg, t);
	}

}
