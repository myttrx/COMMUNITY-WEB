package com.jos.core.web;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class BaseResponseEvent implements Serializable{

	public static final int SUCCESS_STATUS = 1;
	public static final int FAIL_STATUS = 0;
	public static final int VALIDATION_FAIL_STATUS = 2;
	public static final String VALIDATION_ERROR = "ValidationError";
	
	private int status; //0-fails 1-success   2..99(success) remain, -1..-99(fail) remain , 100~199(success, -199..-100(fail) user custom defined code
	
	private Object data;
	
	private Object errors;//Validation error message
	
	private String tag;
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public BaseResponseEvent() {
	}
	
	public BaseResponseEvent(boolean  isSuccess) {
		if(isSuccess){
			this.status = SUCCESS_STATUS;
		}else{
			this.status = FAIL_STATUS;
		}
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setSuccess() {
		this.status = SUCCESS_STATUS;
	}
	
	public void setFail() {
		this.status = FAIL_STATUS;
	}
	
	public boolean isSuccess(){
		return this.status == SUCCESS_STATUS;
	}
	
	public void setValidationFailStatus(){
		this.status = VALIDATION_FAIL_STATUS;
		this.tag = VALIDATION_ERROR;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}
	
}
