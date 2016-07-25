package com.jos.community.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Reflections {

	private static Logger logger = LoggerFactory.getLogger(Reflections.class);
	
	/**
	 * read properties directly , not consider private/protected, not via getter, support object.name.last
	 */
	public static Object getFieldValueWithDot(final Object obj, final String fieldName) {
		String[] fieldNames = fieldName.split("\\.");
		Object result = null;
		if (fieldNames.length>1) {
			for(int i=0;i<fieldNames.length;i++){
				String f = fieldNames[i];
				if (i==0) {
					result = getFieldValue(obj, f);
				}else {
					result = getFieldValue(result, f);
				}
			}
		}else {
			result = getFieldValue(obj,fieldName);
		}
		return result;
	}
	
	/**
	 * read properties directly , not consider private/protected, not via getter
	 */
	public static Object getFieldValue(Object obj,String fieldName){
		Field field = getAccessibleField(obj, fieldName);
		if (field==null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}
		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("impossible exception{}", e.getMessage());
		} 
		return result;
	}
	
	/**
	 * get super class recursively, get object's DeclaredField, and force it accessible.
	 * 
	 * if can't find Object, return null.
	 */
	public static Field getAccessibleField(Object obj,String fieldName){
		Validate.notNull(obj, "object can't be null");
		Validate.notBlank(fieldName, "fieldName can't be blank");
		//子类获取不到方法，从父类中获取字段对应的方法
		for(Class<?> superClass =obj.getClass();superClass != Object.class ;superClass=superClass.getSuperclass()){
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				//not found , continue find in super class
			}
		}
		return null;
	}
	
	/**
	 * change private/protected field to public，avoid call the real modifier，avoid SecurityManager warning of JDK.
	 */
	public static void makeAccessible(Field field){
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) 
				&& !field.isAccessible()) {
			field.setAccessible(true);
		}
	}
	
}
