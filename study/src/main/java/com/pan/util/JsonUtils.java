package com.pan.util;

import java.lang.reflect.Field;
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
	
	public static <T> Map<String,String> listToMap(List<T> list,String keyId){
		Map<String, String> map=new HashMap<String, String>();
		for (T t : list) {
			try {
				Field field = t.getClass().getDeclaredField(keyId);
				field.setAccessible(true);
				String id=(String) field.get(t);
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
	public static <T> List<T> mapToList(Map<String,String> map,Class<T> clazz){
		List<T> list=new ArrayList<T>();
		for(Map.Entry<String,String> entry:map.entrySet()){
			list.add((T) fromJson(entry.getValue(),clazz));
		}
		return list;
	}

}
