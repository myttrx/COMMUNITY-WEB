package com.jos.core.web;

import java.util.Iterator;


import com.jos.community.module.service.MessageService;
import com.jos.community.utils.Reflections;
import com.jos.community.utils.SpringContextHolder;
import com.jos.community.utils.StrUtils;


public class SelectHelper {

	private static String DEFAULT_BLANK_VALUE = "Please Choose One";

	static{
		MessageService messageService = SpringContextHolder.getBean(MessageService.class);
		DEFAULT_BLANK_VALUE = messageService.getMessage("select.placeholder", DEFAULT_BLANK_VALUE);
	}
	
	public static String getOptions(Boolean haveBlankOption,Iterable  list, String valueName, String textName,  String selectedValue) {
		return getOptions(haveBlankOption, "", list, valueName, textName, selectedValue);
	}
	
	public static String getOptions(Boolean haveBlankOption,String blankValue,  Iterable  list, String valueName, String textName,  String selectedValue) {
		StringBuffer optionStr = new StringBuffer();
		if(haveBlankOption){
			//optionStr.append("<option value=\'\' selected disabled style=\'display: none;\'>Please Select</option>");
			if (StrUtils.isNotBlank(blankValue)) {
				optionStr.append("<option selected value=\'\'>"+blankValue+"</option>");
				optionStr.append("</option>");
			}else {
				optionStr.append("<option selected value=\'\'>"+DEFAULT_BLANK_VALUE+"</option>");
				optionStr.append("</option>");
			}
			
		}
		Iterator it = list.iterator();
		if(list==null || !it.hasNext()){
			return "";
		}
		
		for(Object o : list){
			String value = Reflections.getFieldValue(o, valueName).toString();
			String text = Reflections.getFieldValue(o, textName).toString();
			
			optionStr.append("<option value=\'");
			optionStr.append(value);
			optionStr.append("\'");
			if(value.equals(selectedValue)){
				optionStr.append(" selected ");
			}
			optionStr.append(">");
			optionStr.append(text);
			optionStr.append("</option>");
		}

		return optionStr.toString();
	}
	
	public static String getOptions(Boolean haveBlankOption,Class<?>  enumType, String selectedValue) {
		return getOptions(haveBlankOption, "", enumType, selectedValue);
	}
	
	public static String getOptions(Boolean haveBlankOption,String blankValue,
			Class<?>  enumType, String selectedValue) {
		StringBuffer optionStr = new StringBuffer();
		if(haveBlankOption){
			if (StrUtils.isNotBlank(blankValue)) {
				optionStr.append("<option selected value=\'\'>"+blankValue+"</option>");
				optionStr.append("</option>");
			}else {
				optionStr.append("<option selected value=\'\'>"+DEFAULT_BLANK_VALUE+"</option>");
				optionStr.append("</option>");
			}
		}
		if (!enumType.isEnum()) {
			return "";
		}
		Object[] consts = enumType.getEnumConstants();
		for (Object o : consts) {
			String s = o.toString();
			optionStr.append("<option value=\"");
			optionStr.append(s);
			optionStr.append("\"");
			if(selectedValue.equals(s)){
				optionStr.append(" selected ");
			}
			optionStr.append(">");
			optionStr.append(s);
			optionStr.append("</option>");
		}

		return optionStr.toString();
	}
}
