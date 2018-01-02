package com.pan.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;

import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ValidationResult;

public class ValidationUtils {
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public static <T> ValidationResult validateEntity(T obj) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
		if (CollectionUtils.isNotEmpty(set)) {
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
		}
		return result;
	}

	public static <T> ValidationResult validateProperty(T obj,String propertyName) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validateProperty(obj,
				propertyName, Default.class);
		if (CollectionUtils.isNotEmpty(set)) {
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(propertyName, cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
		}
		return result;
	}
	
	public static <T> void validateEntityWithGroups(T object,Class<?>... groups) {
		Set<ConstraintViolation<T>> set = validator.validate(object,groups);
		if (CollectionUtils.isNotEmpty(set)) {
			StringBuilder stringBuilder=new StringBuilder("参数有误：");
			for (ConstraintViolation<T> cv : set) {
				stringBuilder.append(cv.getMessage()+"; ");
			}
			throw new BusinessException(stringBuilder.toString());
		}
		System.out.println("校验成功");
	}
}
