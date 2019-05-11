package com.pan.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ClassUtils {
	
	public static Object getFieldValue(Object obj,String fieldName){
		if(obj==null){
			return null;
		}
		Field[] allFields = getAllFields(obj);
		for(Field f:allFields){
			if(StringUtils.equals(f.getName(),fieldName)){
				f.setAccessible(true);
				try {
					return f.get(obj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
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
	
	public static void setValue(Object obj, String filedStr, Object value) {
		Class<?> clazz = obj.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		Field[] parentFileds = clazz.getSuperclass().getDeclaredFields();
		Field[] all = (Field[]) ArrayUtils.addAll(declaredFields, parentFileds);
		for (Field field : all) {
			if (StringUtils.equals(filedStr, field.getName())) {
				try {
					field.setAccessible(true);
					field.set(obj, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
