package com.jos.community.module.validator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jos.community.module.vo.LoginUserVo;
import com.jos.community.utils.StrUtils;

@Component("loginUserValidator")
public class LoginUserValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return clazz.equals(LoginUserVo.class);
	}

	public void validate(Object target, Errors errors) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);  
		
		ValidationUtils.rejectIfEmpty(errors, "userName", "user.username.required","用户名不能为空");
		ValidationUtils.rejectIfEmpty(errors, "password", "user.password.required","密码不能为空");
		ValidationUtils.rejectIfEmpty(errors, "verifyCode", "user.verifyCode.required","验证码不能为空");
		LoginUserVo loginUserVo = (LoginUserVo) target;
		if (StrUtils.isNotBlank(loginUserVo.getCaptcha()) && !kaptchaExpected.equals(loginUserVo.getCaptcha())) {
			errors.rejectValue("captcha", null, "验证码错误");
		}
	}

}
