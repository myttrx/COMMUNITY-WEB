package com.jos.security;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class JosAccessDecisionManager implements AccessDecisionManager{

	protected final Log logger = LogFactory.getLog(getClass());
	
	//判断用户是否有权限访问资源(即URL),抛出异常说明没有权限
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes) throws AccessDeniedException,
			InsufficientAuthenticationException {
		
		logger.info("-----JosAccessDecisionManager : decide-----");
		if (configAttributes==null) {
			return ;
		}
		for(ConfigAttribute ca : configAttributes){//循环该资源所需要的权限(ROLE)
			String needRole = ((SecurityConfig)ca).getAttribute();
			for(GrantedAuthority ga : authentication.getAuthorities()){//循环用户所拥有的权限
				if(needRole.trim().equals(ga.getAuthority().trim())){
					return ;
				}
			}
		}
		
		throw new AccessDeniedException("");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

}
