package com.jos.community.module.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Service
public class MessageService implements MessageSource{

	@Autowired
	private MessageSource messageSource;
	
	private final static String DEFAULT_MESSAGE="No resource information, please contact the system administrator to add.";
	
	public String getMessage(String code, Object[] args, String defaultMessage,
			Locale locale) {
		return messageSource.getMessage(code, args, defaultMessage, locale);
	}

	public String getMessage(String code, Object[] args, Locale locale)
			throws NoSuchMessageException {
		return messageSource.getMessage(code, args, locale);
	}
	
	public String getMessage(MessageSourceResolvable resolvable, Locale locale)
			throws NoSuchMessageException {
		return messageSource.getMessage(resolvable, locale);
	}

	public String getMessage(String code, Object[] args, String defaultMessage) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Locale selectedLocale = (Locale)request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if(selectedLocale==null) selectedLocale=request.getLocale();
		return messageSource.getMessage(code, args, defaultMessage, selectedLocale);
	}
	
	public String getMessage(String code, String defaultMessage) {
		return this.getMessage(code, null, defaultMessage);
	}
	
	public String getMessage(String code) {
		return this.getMessage(code, null, DEFAULT_MESSAGE);
	}
}
