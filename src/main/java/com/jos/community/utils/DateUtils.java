package com.jos.community.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils{

    public static final String MONTH_FORMAT = "yyyy-MM";  
    public static final String DATE_FORMAT = "yyyy-MM-dd";  
    public static final String HOUR_FORMAT = "HH:mm:ss";  
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * @funName: convertDateToStr 
     * @description: default use DATE_FORMAT
     * @param date
     * @return String 
     */
    public static String convertDateToStr(Date date) {
		return convertDateToStr(date,DATE_FORMAT);
	}
	
	public static String convertDateToStr(Date date,String pattern) {
		String str = null;
		try {
			str = DateFormatUtils.format(date, pattern,Locale.ENGLISH);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return str;
	}
	
	public static Date convertStrToDate(String str) {
		return convertStrToDate(str,DATE_FORMAT);
	}
	
	public static Date convertStrToDate(String str, String pattern) {
		SimpleDateFormat sdf = null;
		Date date = null;
		try {
			if (StringUtils.isNotBlank(pattern)) {
				sdf = new SimpleDateFormat(pattern,Locale.ENGLISH);
				date = sdf.parse(str);
			} else {
				sdf = new SimpleDateFormat(DATE_FORMAT);
				date = sdf.parse(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date formatDate(Date date){
		return formatDate(date,DATE_FORMAT);
	}
	
	public static Date formatDate(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.ENGLISH);
		try {
			date =sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getLastMonthFirstDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	public static Date getLastMonthLastDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
}
