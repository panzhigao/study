package com.pan.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author Administrator
 *
 */
public class RegexUtils {
	
	public static final String telephoneRegex="^1[3|5|8]{1}[0-9]{9}$";
	
	public static boolean check(String obj,String regex){
		if(obj==null){
			return false;
		}
		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(obj);
		if(m.find()){
			return true;
		}
		return false;
	}
	
	public static boolean checkTelephone(String str){
		return check(str, telephoneRegex);
	}
	
	 public static void main( String args[] ){
		 System.out.println(checkTelephone(null));
	   }
}
