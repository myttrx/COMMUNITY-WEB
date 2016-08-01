package com.jos.security.utils;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

	/**
	 * @funName: getUsername 
	 * @description: get the login user name.
	 * @return String 
	 */
	public static String getUsername(){
		String userName = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		}else {
			userName = principal.toString();
		}
		return userName;	
	}
	
	/**
	 * @funName: getRoles 
	 * @description: get the login user role list.
	 * @return List<String> 
	 */
	public static List<String> getRoles(){
		return null;
	}
	
	/**
	 * @funName: isAuthenticated 
	 * @description: judge the login user is authenticated or not.
	 * @return boolean 
	 */
	public static boolean isAuthenticated(){
		return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}
	
	
}
