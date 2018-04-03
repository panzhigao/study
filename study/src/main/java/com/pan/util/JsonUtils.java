package com.pan.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonNull;

/**
 * @author Administrator
 */
public class JsonUtils {

	private static Gson gson = new Gson();

	private JsonUtils() {
	}

	/**
	 * @MethodName : toJson
	 * @Description : 将对象转为JSON串，此方法能够满足大部分需求
	 * @param src
	 *            :将要被转化的对象
	 * @return :转化后的JSON串
	 */
	public static String toJson(Object src) {
		if (src == null) {
			return gson.toJson(JsonNull.INSTANCE);
		}
		return gson.toJson(src);
	}

	/**
	 * @MethodName : fromJson
	 * @Description : 用来将JSON串转为对象，但此方法不可用来转带泛型的集合
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> Object fromJson(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}

	@SuppressWarnings("unchecked")
	public static <T> Object fromJson(String json, Class<T>... classOfT) {
		return gson.fromJson(json, classOfT[0]);
	}

	/**
	 * @MethodName : fromJson
	 * @Description : 用来将JSON串转为对象，此方法可用来转带泛型的集合，如：Type为 new
	 *              TypeToken<List<T>>(){}.getType()
	 *              ，其它类也可以用此方法调用，就是将List<T>替换为你想要转成的类
	 * @param json
	 * @param typeOfT
	 * @return
	 */
	public static Object fromJson(String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

	public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
		List<T> ts = (List<T>) JSON.parseArray(jsonString, clazz);
		return ts;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(List<String> list, Class<T> clazz) {
		List<T> resultList=new ArrayList<T>();
		for (String string : list) {
			if(string==null){
				continue;
			}
			Object obj = fromJson(string, clazz);
			resultList.add((T) obj);
		}
		return resultList;
	}
	
	public static <T> Map<String, String> listToMap(List<T> list, String keyId) {
		Map<String, String> map = new HashMap<String, String>(10);
		for (T t : list) {
			try {
				Field field = t.getClass().getDeclaredField(keyId);
				field.setAccessible(true);
				String id = (String) field.get(t);
				map.put(id, toJson(t));
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> mapToList(Map<String, String> map, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			list.add((T) fromJson(entry.getValue(), clazz));
		}
		return list;
	}
	
	/**
	 * object to map
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Map<String, String> objectToMap(Object obj){
		Map<String, String> map = new HashMap<>(10);
		Class<?> clazz = obj.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value;
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				continue;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				continue;
			}
			map.put(fieldName, String.valueOf(value));
		}
		return map;
	}
	
    public static Object mapToObject(Map<String,String> map, Class<?> beanClass) throws Exception {      
        if (map == null){        	
        	return null;      
        }     
        Object obj = beanClass.newInstance();    
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());      
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();      
        for (PropertyDescriptor property : propertyDescriptors) {    
            Method setter = property.getWriteMethod();      
            if (setter != null) {    
                setter.invoke(obj, map.get(property.getName()));     
            }    
        }    
        return obj;    
    } 
}
