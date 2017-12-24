package com.pan.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Administrator
 * 用户登录及注册的操作都在这个类
 */
@Controller
public class LoginController {
	
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * 跳转登录页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/")
	public String toLogin(){
		return "index";
	}
	
	/**
	 * 跳转注册页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/register")
	public String toRegisterPage(){
		return "register";
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping
	public Map<String,Object> register(){
		logger.info("注册开始");
		
		
		
		return null;
	}	
}
