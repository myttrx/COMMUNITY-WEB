package com.jos.community.utils;

public class SearchFilter {

	public enum Operator{
		EQ, NOTEQ, LIKE, GT, LT, GTE, LTE, IN, ISNULL, ISNOTNULL, NOTIN
	}
	
	public String fieldName;
	public Object value;//eg the value of Date,String,Long
	public Operator operator;
	
	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}
}
