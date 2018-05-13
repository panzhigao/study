package com.pan.test;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;

import com.pan.shiro.MyRealm;

public class ShiroTest {
	
	public static void main(String[] args) {
		MyRealm realm=new MyRealm();
		realm.getAuthenticationCache();
		// 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
		// 2、得到SecurityManager实例并绑定给SecurityUtils
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		securityManager.setRealm(realm);
		SecurityUtils.setSecurityManager(securityManager);
		// 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		try {
			// 4、登录，即身份验证
			subject.login(token);
		} catch (AuthenticationException e) {
			// 5、身份验证失败
			System.out.println("身份验证失败");
		}
	}
}
