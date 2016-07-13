package com.jos.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.jos.community.system.module.service.ResourceService;
import com.jos.community.system.module.service.RoleService;


public class JosInvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = null;

	private RoleService roleService;
	private ResourceService resourceService;
	
	public JosInvocationSecurityMetadataSourceService() {
		
	}

	//加载不同用户的资源(即URL)
	public void loadResourceDefine() {
		logger.info("-----loadResourceDefine-----");
		this.requestMap = new HashMap<RequestMatcher, Collection<ConfigAttribute>>();
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca = new SecurityConfig("ROLE_ADMIN");
		atts.add(ca);
		RequestMatcher matcher = new AntPathRequestMatcher("/main.shtml");
		requestMap.put(matcher, atts);
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Collection<ConfigAttribute> allAttributes = new ArrayList<ConfigAttribute>();
		for(RequestMatcher matcher : requestMap.keySet()){
			allAttributes.addAll(requestMap.get(matcher));
		}
		return allAttributes;
	}

	public Collection<ConfigAttribute> getAttributes(Object object)throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation)object).getHttpRequest();
		for(Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()){
			if (entry.getKey().matches(request)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

}
