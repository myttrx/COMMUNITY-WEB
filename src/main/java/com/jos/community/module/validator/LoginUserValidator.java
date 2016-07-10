package com.jos.community.module.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jos.community.module.vo.LoginUserVo;

@Component("loginUserValidator")
public class LoginUserValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return clazz.equals(LoginUserVo.class);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "userName", "user.username.required","用户名不能为空");
		ValidationUtils.rejectIfEmpty(errors, "password", "user.password.required","密码不能为空");
		
	}

}
