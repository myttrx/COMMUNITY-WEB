package com.jos.community.system.module.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jos.community.system.module.model.ResourceModel;

@Component("resourceValidator")
public class ResourceValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return clazz.equals(ResourceModel.class);
	}

	public void validate(Object target, Errors errors) {
//		CodeTableModel codeTableModel = (CodeTableModel) target;
		ValidationUtils.rejectIfEmpty(errors, "resourceName", "","Resource Name can not be empty");
		ValidationUtils.rejectIfEmpty(errors, "resourceType", "","Resource Type can not be empty");
		ValidationUtils.rejectIfEmpty(errors, "resourceContent", "","Resource Content can not be empty");
		ValidationUtils.rejectIfEmpty(errors, "resourceOrder", "","Resource Order can not be empty");
	}
}
