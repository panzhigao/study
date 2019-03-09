package com.pan.util;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.pan.common.constant.MyConstant;
import com.pan.entity.User;
import com.pan.shiro.MyRealm;
import java.util.Collection;

/**
 * @author panzhigao
 */
public class TokenUtils {
	
	public static boolean isAuthenticated(){
		return SecurityUtils.getSubject().isAuthenticated();
	} 
	
	public static User getLoginUser(){
		User user = (User)getSession().getAttribute("user");
		return user;
	} 
	
	public static String getLoginUserId(){
		User loginUser = getLoginUser();
		if(loginUser==null){
			return null;
		}
		return loginUser.getUserId();
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
	 * @param userId 用户id
	 */
	public static void clearAuth(String userId){  
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();  
	    realm.clearAuth(userId);
	}
	
	/**
	 * 清空指定用户授权信息
	 * @param userId 用户id
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
	 * 将用户信息放入subject,使用户自动登录
	 * @param user 新的用户信息
	 */
	public static void userAutoLogin(User user){
		Subject subject = SecurityUtils.getSubject(); 
	    UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());  
		subject.login(token);
	}

	/**
	 * 获取登录人的ip地址
	 * @return
	 */
	public static String getIp(){
		return getAttribute(MyConstant.USER_IP)==null?"0.0.0.0":(String)getAttribute(MyConstant.USER_IP);
	}

	/**
	 * 判断当前用户是否在线
	 * @param userId 用户id
	 * @return
	 */
	public static boolean isOnline(String userId,Collection<Session> activeSessions){
		RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
		MyRealm realm = (MyRealm)rsm.getRealms().iterator().next();
		return realm.isOnline(userId,activeSessions);
	}
}
