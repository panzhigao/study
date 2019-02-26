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
}
