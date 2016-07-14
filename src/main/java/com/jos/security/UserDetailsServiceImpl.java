package com.jos.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jos.community.module.service.MessageService;
import com.jos.community.module.service.UserService;
import com.jos.community.utils.StrUtils;
import com.jos.security.core.LoginUserDetails;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserService userService;
	
	@Autowired
	MessageService messageService;
	
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
		String authenticationMsg = this.messageService.getMessage("authentication.invalidUserNameOrPassword", "Bad credentials");
		if (StrUtils.isBlank(username)) {
			throw new UsernameNotFoundException(authenticationMsg);
		}
		LoginUserDetails loginUserDetails = this.userService.findUserDetail(username);
		if (loginUserDetails==null) {
			throw new UsernameNotFoundException(authenticationMsg);
		}
		return loginUserDetails;
	}

}
