package com.jos.core.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class JsonResponse extends BaseResponseEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3959162035826781917L;

	public JsonResponse(boolean isSuccess) {
		super(isSuccess);
	}
	
	public JsonResponse() {
		super();
	}

	private String jsonData;
	
	private List<Message> messages = new ArrayList<Message>();
	
	private String singleMessage;

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	 
	public void addMessage(String message) {
		if(messages == null)
			messages = new ArrayList<Message>();
		Message msg = new Message(message);
		this.messages.add(msg);
	}

	public String getSingleMessage() {
		if(StringUtils.isEmpty(this.singleMessage)){
			if(this.messages!=null&&this.messages.size()>0)
			this.singleMessage = this.messages.get(0).getText();
		}
		return singleMessage;
	}

	public void setSingleMessage(String singleMessage) {
		this.singleMessage = singleMessage;
		this.addMessage(singleMessage);
	}

}
