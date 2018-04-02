package com.pan.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.pan.entity.Permission;

/**
 * @author 作者
 * @version 创建时间：2018年4月2日 下午5:49:56
 * 类说明
 */
public class PermissionUtils {
	
	public static boolean hasPermssion(String url){
        String loginUserId = CookieUtils.getLoginUserId();
		String roles = JedisUtils.getString("user_roles:"+loginUserId);
		String[] arr=(String[]) JsonUtils.fromJson(roles, String[].class);
		for (int i = 0; i < arr.length; i++) {
			Map<String, String> hgetAll = JedisUtils.hgetAll("role_permissions:"+arr[i]);
			List<Permission> list = JsonUtils.mapToList(hgetAll,Permission.class);
			for (Permission permission : list) {
				if(StringUtils.equals(permission.getUrl(),url)){ 
					return true;
				}
			}	
		}
		return false;
	}
}