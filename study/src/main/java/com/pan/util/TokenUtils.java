package com.pan.util;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.pan.entity.User;
import com.pan.shiro.MyRealm;

public class TokenUtils {
	
	public static boolean isAuthenticated(){
		return SecurityUtils.getSubject().isAuthenticated();
	} 
	
	public static User getLoginUser(){
		User user = (User)getSession().getAttribute("user");
		return user;
	} 
	
	public static String getLoingUserId(){
		User loingUser = getLoginUser();
		if(loingUser==null){
			return null;
		}
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
	
	/**
	 * 清空当前用户授权信息
	 */
	public static void clearAuth(){  
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();  
	    realm.clearAuthz();  
	} 
	
	/**
	 * 清空指定用户认证信息
	 * @param user 用户信息
	 */
	public static void clearAuth(String userId){  
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();  
	    realm.clearAuth(userId);
	}
	
	/**
	 * 清空指定用户授权信息
	 * @param user 用户信息
	 */
	public static void clearAuthz(String userId){  
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();  
	    realm.clearAuthz(userId);  
	}
	
	/**
	 * 清空当前用户授权和验证信息
	 */
	public static void clearPrincipal(){  
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();  
	    realm.cleanAll();
	}
	
	/**
	 * 清空所有用户授权信息
	 */
	public static void clearAllUserAuth(){  
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();  
	    realm.clearAllCachedAuthorizationInfo();  
	}
		
	/**
	 * 将用户信息放入subject
	 * @param user 新的用户信息
	 */
	public static void setPrincipal(User user){
		Subject subject = SecurityUtils.getSubject(); 
	    UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());  
		subject.login(token);
	}
}
