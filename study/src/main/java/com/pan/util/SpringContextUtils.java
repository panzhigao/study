package com.pan.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author panzhigao
 */
public class SpringContextUtils implements ApplicationContextAware {

    /**
     * 应用上下文环境
     */
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.context = applicationContext;
    }

    /**
     * 根据bean name 获取对象
     *
     * @param name
     * @return
     */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    /**
     * 根据Class获取对象
     * @param clazz
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return context.getBean(clazz);
    }
}
