package com.pan.util;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.pan.entity.User;
import com.pan.shiro.MyRealm;

public class TokenUtils {
	
	public static boolean isAuthenticated(){
		return SecurityUtils.getSubject().isAuthenticated();
	} 
	
	public static User getLoginUser(){
		User user= (User)SecurityUtils.getSubject().getPrincipal();
		return user;
	} 
	
	public static String getLoingUserId(){
		User loingUser = getLoginUser();
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
	 * 清空指定用户授权信息
	 * @param user 用户信息
	 */
	public static void clearAuth(User user){  
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();  
	    realm.clearAuthz(user);  
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
	 * 重置用户信息
	 * @param user 新的用户信息
	 */
	public static void resetPrincipal(User user){
		Subject subject = SecurityUtils.getSubject();  
		PrincipalCollection principalCollection = subject.getPrincipals();  
		String realmName = principalCollection.getRealmNames().iterator().next();  
		PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);  
		//重新加载Principal  
		subject.runAs(newPrincipalCollection); 
	}
	
	/**
	 * 将用户信息放入subject
	 * @param user 新的用户信息
	 */
	public static void setPrincipal(User user){
		Subject subject = SecurityUtils.getSubject(); 
	    UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());  
		subject.login(token);
//		
//		RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
//	    MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();  
//	    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),realm.getName()); 
//		PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(authenticationInfo, realm.getName());  
//		//重新加载Principal  
//		subject.runAs(newPrincipalCollection); 
	}
}
