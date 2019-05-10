package com.pan.util;

import java.lang.reflect.Field;

public class ClassUtils {
	
	public static Object getField(Object obj,String fieldName){
		if(obj==null){
			return null;
		}
		try {
			Field declaredField = obj.getClass().getDeclaredField(fieldName);
			if(declaredField==null){
				return null;
			}
			declaredField.setAccessible(true);
			Object object = declaredField.get(obj);
			return object;
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
