package com.jos.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jos.community.module.service.MessageService;
import com.jos.community.utils.StrUtils;

/**
 * @author Jet
 * custom filter than extends UsernamePasswordAuthenticationFilter to authentication the captcha, Username and Password
 */
public class JosLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";//screen captcha control ID
	
	private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;
	
	@Autowired
	private MessageService messageService;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		String expectedCaptcha =  obtainExpectedCaptcha(request);
		String actualCaptcha = obtainActualCaptcha(request);
		if (StrUtils.isBlank(actualCaptcha)) {
			throw new JosLoginAuthenticationException(messageService.getMessage("authentication.requiredCaptcha", "Captcha is required"));
		}
		if (!expectedCaptcha.equals(actualCaptcha)) {
			throw new JosLoginAuthenticationException(messageService.getMessage("authentication.invalidCaptcha", "Invalid Captcha"));
		}
		
		return super.attemptAuthentication(request, response);
	}

	protected String obtainActualCaptcha(HttpServletRequest request){  
        return request.getParameter(this.captchaParameter);  
    }
	
	protected String obtainExpectedCaptcha(HttpServletRequest request){  
        return (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
    }
	
}
