package com.pan.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    /**
     * 限流唯一标示
     *
     * @return
     */
    String key() default "";
    /**
     * 限流时间,单位秒
     *
     * @return
     */
    int time();
    /**
     * 限流次数
     *
     * @return
     */
    int count();
}
