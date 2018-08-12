package com.pan.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static final String FORMAT_DATE_FULL="yyyy-MM-dd HH:mm:ss";
	
	public static final String FORMAT_DATE="yyyy-MM-dd";
	
	public static final String FORMAT_DATE2="yyyyMMdd";
	
	public static final String FORMAT_DATE3="yyyy-MM-dd HH:mm";
	
	public static final String FORMAT_TIME_MILLS="yyyyMMddHHmmssSSS";
	
	/**
	 * 获取当前时间格式化成字符串
	 * @param format 日期字符串格式
	 * @return
	 */
	public static String getNowDateStr(String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	public static String getDateStr(Date date,String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 获取昨日日期
	 * @return
	 */
	public static Date getYesterdayDate(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		Date time=cal.getTime();
		return time;
	}
	
	public static void main(String[] args) {
		Date lastDate = getYesterdayDate();
		System.out.println(lastDate);
	}
}
