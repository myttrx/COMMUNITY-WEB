package com.jos.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class JosDaoAuthenticationProvider extends DaoAuthenticationProvider{

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		super.additionalAuthenticationChecks(userDetails, authentication);
		logger.info("-------additionalAuthenticationChecks : skip password check--------");
		
	}

	
}
