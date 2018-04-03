package com.pan.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.pan.common.enums.QueryTypeEnum;

/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午1:45:21
 * 类说明
 */
@Target(ElementType.FIELD) 
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface QueryType {
	QueryTypeEnum queryType() default QueryTypeEnum.MATCH;
}
