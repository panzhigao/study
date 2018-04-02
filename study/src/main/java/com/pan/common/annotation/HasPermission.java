package com.pan.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 作者
 * @version 创建时间：2018年4月2日 下午5:44:14
 * 类说明
 */
@Target(ElementType.METHOD) //表明该注解对成员方法起作用  
@Retention(RetentionPolicy.RUNTIME) //在编译以后仍然起作用 
@Inherited
@Documented
public @interface HasPermission {
	String value() default "";
}
