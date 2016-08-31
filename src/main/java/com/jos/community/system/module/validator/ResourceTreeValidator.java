package com.jos.community.system.module.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jos.community.system.module.model.ResourceTreeModel;

@Component("resourceTreeValidator")
public class ResourceTreeValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return clazz.equals(ResourceTreeModel.class);
	}

	public void validate(Object target, Errors errors) {
//		CodeTableModel codeTableModel = (CodeTableModel) target;
		ValidationUtils.rejectIfEmpty(errors, "treeNodeName", "","Node Name can not be empty");
		ValidationUtils.rejectIfEmpty(errors, "treeNodeOrder", "","Node Order can not be empty");
		//ValidationUtils.rejectIfEmpty(errors, "parentId", "","Parent Id can not be empty");
	}
}
