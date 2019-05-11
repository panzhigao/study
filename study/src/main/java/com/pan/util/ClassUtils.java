package com.pan.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	/**
	 * 获取类所有属性
	 * @param o
	 * @return
	 */
	public static Field[] getAllFields(Object o){
	    Class<?> c= o.getClass();
	    List<Field> fieldList = new ArrayList<>();
	    while (c!= null && c!=Object.class){
	        fieldList.addAll(new ArrayList<>(Arrays.asList(c.getDeclaredFields())));
	        c= c.getSuperclass();
	    }
	    Field[] fields = new Field[fieldList.size()];
	    fieldList.toArray(fields);
	    return fields;
	}

}
