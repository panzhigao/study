package com.pan.util;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;

import com.pan.entity.User;
import com.pan.shiro.MyRealm;

public class TokenUtils {
	
	public static User getLoingUser(){
		User user= (User)SecurityUtils.getSubject().getPrincipal();
		return user;
	} 
	
	public static String getLoingUserId(){
		User loingUser = getLoingUser();
		return loingUser.getUserId();
	} 
	
	public static Session getSession(){
		return SecurityUtils.getSubject().getSession();
	}
	
	public static void setAttribute(Object key,Object value){
		Session session = getSession();
		session.setAttribute(key, value);
	}
	
	public static Object getAttribute(Object key){
		Session session = getSession();
		return session.getAttribute(key);
	}
	
	//清空授权信息
	public static void clearAuth(){  
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();  
	    realm.clearAuthz();  
	} 
	
	//清空指定用户授权信息
	public static void clearAuth(User user){  
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();  
	    realm.clearAuthz(user);  
	}
	
	//清空所有用户授权信息
	public static void clearAllUserAuth(){  
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();  
	    realm.clearAllCachedAuthorizationInfo();  
	}

}
