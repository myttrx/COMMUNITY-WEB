package com.jos.security.core;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUserDetails extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1132346172158904836L;

	public LoginUserDetails(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

}
