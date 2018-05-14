package com.pan.util;

import org.apache.shiro.SecurityUtils;

/**
 * @author 作者
 * @version 创建时间：2018年4月2日 下午5:49:56
 * 类说明
 */
public class PermissionUtils {

	/**
	 * 是否有权限
	 * @param url
	 * @return
	 */
	public static boolean hasPermssion(String url){
		return SecurityUtils.getSubject().isPermitted(url);
	}
}
