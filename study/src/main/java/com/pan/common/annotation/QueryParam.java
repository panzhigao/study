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
 * es查询参数注解
 */
@Target({ElementType.FIELD,ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface QueryParam {
	/**
	 * 标注字段采用什么匹配类型
	 * @return
	 */
	QueryTypeEnum queryType() default QueryTypeEnum.MATCH;
	/**
	 * 标注字段是否需要高亮,默认为不高亮
	 * @return
	 */
	boolean highLightFlag() default false;
}
