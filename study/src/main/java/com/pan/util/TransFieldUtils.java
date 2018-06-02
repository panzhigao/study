package com.pan.util;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pan.common.annotation.UnescapeHtml;

/**
 * 转换类中属性值
 * 
 * @author Administrator
 *
 */
public class TransFieldUtils {

	private static final Logger logger = LoggerFactory.getLogger(TransFieldUtils.class);

	private static Field[] getFieldArray(Class<?> clazz) {
		Field[] result = new Field[] {};
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field[] declaredFields = clazz.getDeclaredFields();
				result=ArrayUtils.addAll(result, declaredFields);
			} catch (Exception e) {
				logger.error("获取属性失败",e);
			}
		}
		return result;
	}

	public static void transEntity(Object obj) {
		Class<? extends Object> clazz = obj.getClass();
		Field[] declaredFields = getFieldArray(clazz);
		for (Field field : declaredFields) {
			UnescapeHtml annotation = field.getAnnotation(UnescapeHtml.class);
			if (annotation != null) {
				Object content;
				try {
					field.setAccessible(true);
					content = field.get(obj);
					String clean = Jsoup.clean(StringEscapeUtils.unescapeHtml((String) content),
							Whitelist.basicWithImages());
					field.set(obj, clean);
				} catch (IllegalArgumentException e) {
					logger.error("转义字段{}失败", field.getName(), e);
				} catch (IllegalAccessException e) {
					logger.error("转义字段{}失败", field.getName(), e);
				}
			}
		}
	}

	public static void transEntityCollection(Collection<?> collection) {
		for (Object object : collection) {
			transEntity(object);
		}
	}
}
