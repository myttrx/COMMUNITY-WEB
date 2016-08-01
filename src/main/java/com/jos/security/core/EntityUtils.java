package com.jos.security.core;

import java.util.Date;

import com.jos.community.base.BaseEntity;
import com.jos.security.utils.JosSecurityUtils;

public class EntityUtils {

	public static void insertValue(BaseEntity entity){
		LoginUserDetails currentUser = JosSecurityUtils.getCurrentUser();
		entity.setCreatedBy(currentUser.getUsername());
		entity.setCreatedDate(new Date());
		updateValue(entity);
	}
	
	public static void updateValue(BaseEntity entity){
		LoginUserDetails currentUser = JosSecurityUtils.getCurrentUser();
		entity.setModifiedBy(currentUser.getUsername());
		entity.setModifiedDate(new Date());
	}
}
