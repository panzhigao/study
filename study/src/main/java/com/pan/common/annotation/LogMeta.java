package com.pan.common.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 */
@Target(ElementType.FIELD) //表明该注解对成员方法起作用
@Retention(RetentionPolicy.RUNTIME) //在编译以后仍然起作用
@Inherited
@Documented
public @interface LogMeta {
    /**
     * 字段描述
     * @return
     */
    String fieldDesc() default "";

}
