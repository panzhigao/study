package com.pan.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 转义字段
 * @author Administrator
 *
 */
@Target(ElementType.FIELD) //表明该注解对成员方法起作用  
@Retention(RetentionPolicy.RUNTIME) //在编译以后仍然起作用 
@Inherited
@Documented
public @interface UnescapeHtml {

}
