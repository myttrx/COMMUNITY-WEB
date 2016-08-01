package com.jos.security.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.jos.security.core.LoginUserDetails;

public class JosSecurityUtils extends SecurityUtils{

	/**
	 * @funName: getCurrentUser 
	 * @description: get the current login user details
	 * @return LoginUserDetails 
	 */
	public static LoginUserDetails getCurrentUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			LoginUserDetails loginUserDetails = (LoginUserDetails) principal;
			return loginUserDetails;
		}
		return null;
	}
}
