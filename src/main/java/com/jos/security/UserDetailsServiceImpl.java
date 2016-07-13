package com.jos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jos.community.module.service.UserService;
import com.jos.security.core.LoginUserDetails;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserService userService;
	
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
		LoginUserDetails loginUserDetails = this.userService.findUserDetail(username);
		return loginUserDetails;
	}

}
