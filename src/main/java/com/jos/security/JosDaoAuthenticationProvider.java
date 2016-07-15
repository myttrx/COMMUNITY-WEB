package com.jos.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.jos.community.module.service.MessageService;
import com.jos.community.utils.StrUtils;

public class JosDaoAuthenticationProvider extends DaoAuthenticationProvider{

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private MessageService messageService;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		logger.info("-------additionalAuthenticationChecks--------");
		String presentedPassword = authentication.getCredentials().toString();
		if (StrUtils.isBlank(presentedPassword)) {
			throw new JosLoginAuthenticationException(messageService.getMessage("authentication.requiredPassword","Password is required"));
		}
		super.additionalAuthenticationChecks(userDetails, authentication);
		
	}

	
}
