package com.pan.util;

/**
 * @author panzhigao
 */
public class SystemUtils {

	public static final String WINDOWS="win";

	/**
	 * 判断当前系统是否是windows
	 * @return
	 */
	public static boolean isWindows(){
		String os = System.getProperty("os.name"); 
		if(os.toLowerCase().startsWith(WINDOWS)){
			return true;
		} 
		return false;
	}

}
