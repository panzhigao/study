package com.pan.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import com.pan.common.annotation.LogMeta;
import com.pan.entity.Permission;
import org.apache.commons.collections.CollectionUtils;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 校验工具
 * @author Administrator
 *
 */
public class ValidationUtils {

	private static final Logger logger = LoggerFactory.getLogger(ValidationUtils.class);

	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public static <T> void validateEntity(T obj) {
		validateEntityWithGroups(obj,Default.class);
	}

	public static <T> ValidationResult validateProperty(T obj,String propertyName) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validateProperty(obj,
				propertyName, Default.class);
		if (CollectionUtils.isNotEmpty(set)) {
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>(10);
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(propertyName, cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
		}
		return result;
	}

	/**
	 *
	 * @param object
	 * @param groups 当传入的groups为空数组，会采用Default.class来校验
	 * @param <T>
	 */
	public static <T> void validateEntityWithGroups(T object,Class<?>... groups) {
		Set<ConstraintViolation<T>> set = validator.validate(object,groups);
		if (CollectionUtils.isNotEmpty(set)) {
			StringBuilder stringBuilder=new StringBuilder("参数有误：");
			for (ConstraintViolation<T> cv : set) {
				stringBuilder.append(cv.getMessage()+"; ");
			}
			logger.error(stringBuilder.toString());
			throw new BusinessException(stringBuilder.toString());
		}
	}
	
	/**
	 * 比较对象前后修改的值,默认记录未修改的值
	 * @param before
	 * @param after
	 * @return
	 */
	public static String getChangedFields(Object before,Object after){
		return getChangedFields(before, after, false);
	}
	
	/**
	 * 比较对象前后修改的值
	 * @param before
	 * @param after
	 * @param showUnchanged 是否记录未修改内容
	 * @return
	 */
	public static String getChangedFields(Object before,Object after,boolean showUnchanged){
		if(before==null||after==null){
			throw new BusinessException("比较对象不能为空");
		}
		if(!before.getClass().equals(after.getClass())){
			throw new BusinessException("比较对象类型不一致");
		}
		Field[] fields = before.getClass().getDeclaredFields();
		StringBuilder builder=new StringBuilder();
		for(Field f:fields){
			LogMeta annotation = f.getAnnotation(LogMeta.class);
			if(annotation!=null){
				f.setAccessible(true);
				try {
					String desc=annotation.fieldDesc();
					String beforeValue = String.valueOf(f.get(before));
					String afterValue = String.valueOf(f.get(after));
					if(!beforeValue.equals(afterValue)){
						builder.append(desc);
						builder.append("：").append(beforeValue).append("-->").append(afterValue).append("；");
					}else if(showUnchanged){
						builder.append(desc);
						builder.append("未修改；");
					}
				} catch (IllegalAccessException e) {
					logger.error("获取属性失败",e);
				}

			}
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		Permission p1=new Permission();
		p1.setPermissionName("123");
		Permission p2=new Permission();
		p2.setPermissionName("333");
		String changedFields = getChangedFields(p1, p2);
		System.out.println(changedFields);
	}
}
