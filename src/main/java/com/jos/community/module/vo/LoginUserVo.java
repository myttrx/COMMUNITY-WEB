package com.jos.community.module.vo;

import java.io.Serializable;

public class LoginUserVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7437659775577951668L;
	private String userName;
	private String password;
	private String captcha;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
