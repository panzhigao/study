package com.pan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 
 * @author Administrator
 * 用户登录
 */
@Controller
public class LoginController {
	
	//private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * 跳转登录页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/","index"})
	public String toLogin(){
		return "index";
	}
	
}
