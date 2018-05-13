package com.pan.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import com.pan.entity.Permission;

/**
 * @author 作者
 * @version 创建时间：2018年4月2日 下午5:49:56
 * 类说明
 */
public class PermissionUtils {
	//TODO S删除
	/**
	 * 是否有权限
	 * @param url
	 * @return
	 */
	public static boolean hasPermssion(String url){
		return SecurityUtils.getSubject().isPermitted(url);
//        String loginUserId = CookieUtils.getLoginUserId();
//		String roles = JedisUtils.getString("user_roles:"+loginUserId);
//		if(StringUtils.isBlank(roles)){
//			return false;
//		}
//		String[] arr=(String[]) JsonUtils.fromJson(roles, String[].class);
//		for (int i = 0; i < arr.length; i++) {
//			Map<String, String> hgetAll = JedisUtils.hgetAll("role_permissions:"+arr[i]);
//			List<Permission> list = JsonUtils.mapToList(hgetAll,Permission.class);
//			for (Permission permission : list) {
//				if(StringUtils.equals(permission.getUrl(),url)){ 
//					return true;
//				}
//			}	
//		}
//		return false;
	}
}
