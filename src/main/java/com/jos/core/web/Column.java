package com.jos.core.web;

import java.io.Serializable;

public class Column implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7652703969588293912L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
