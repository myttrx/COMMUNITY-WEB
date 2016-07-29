package com.jos.core.web;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 8061159078393240909L;
	
	private String id; // id or num
	private String type; //error , warning, info ...
	private String text;

	public Message() {
		super();
		this.id = "";
		this.type = "E";
		this.text = "";
	}

	public Message(String text) {
		super();
		this.id = "";
		this.type = "E";
		this.text = text;
	}

	public Message(String type, String text) {
		super();
		this.id = "";
		this.type = type;
		this.text = text;
	}

	public Message(String id, String type, String text) {
		super();
		this.id = id;
		this.type = type;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
