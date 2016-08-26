package com.jos.community.system.module.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jos.community.system.module.model.ResourceModel;

public class ResourceValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return clazz.equals(ResourceModel.class);
	}

	public void validate(Object target, Errors errors) {
//		CodeTableModel codeTableModel = (CodeTableModel) target;
//		ValidationUtils.rejectIfEmpty(errors, "codeTableType", "","Code Table Type can not be empty");
//		ValidationUtils.rejectIfEmpty(errors, "code", "","Code can not be empty");
//		ValidationUtils.rejectIfEmpty(errors, "description", "","Description can not be empty");
	}
}
