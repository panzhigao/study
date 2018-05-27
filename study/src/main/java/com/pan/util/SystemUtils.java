package com.pan.util;

public class SystemUtils {
	
	public static boolean isWindows(){
		String os = System.getProperty("os.name"); 
		if(os.toLowerCase().startsWith("win")){ 
			return true;
		} 
		return false;
	}

}
