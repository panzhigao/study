package com.pan.util;

import java.beans.PropertyDescriptor;
import java.util.*;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


/**
 * @author panzhigao
 */
public class BeanUtils extends org.springframework.beans.BeanUtils{
	
	public static String[] getNullPropertyNames (Object source,String... ignoreProperties) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null){
            	 emptyNames.add(pd.getName());
            }
        }
        emptyNames.addAll(Arrays.asList(ignoreProperties));
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 复制属性，只复制指定的字段,如果指定字段值为null，将覆盖目标对象
     * @param source
     * @param target
     * @param includesArr 包含的字段
     */
    public static void copyPropertiesInclude(final Object source, final Object target, final String[] includesArr){
        final Collection<String> excludes = new ArrayList<String>();
        final Collection<String> includes = Arrays.asList(includesArr);
        final PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(source.getClass());
        for(final PropertyDescriptor propertyDescriptor : propertyDescriptors){
            String propName = propertyDescriptor.getName();
            if(!includes.contains(propName)){
                excludes.add(propName);
            }
        }
        BeanUtils.copyProperties(source, target, excludes.toArray(new String[excludes.size()]));
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target){
       BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
    
    public static void copyPropertiesIgnoreNull(Object src, Object target,String... ignoreProperties){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src,ignoreProperties));
     }
    
    
}
