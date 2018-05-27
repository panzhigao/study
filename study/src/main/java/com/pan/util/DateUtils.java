package com.pan.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static final String FORMAT_DATE_FULL="yyyy-MM-dd HH:mm:ss";
	
	public static final String FORMAT_DATE="yyyy-MM-dd";
	
	public static final String FORMAT_DATE2="yyyyMMdd";
	
	public static final String FORMAT_TIME_MILLS="yyyyMMddHHmmssSSS";
	
	public static String getDateStr(String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
}
