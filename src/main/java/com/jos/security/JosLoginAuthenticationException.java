package com.jos.security;

import org.springframework.security.core.AuthenticationException;

public class JosLoginAuthenticationException extends AuthenticationException{

	public JosLoginAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

	public JosLoginAuthenticationException(String msg) {
		super(msg);
	}
}
