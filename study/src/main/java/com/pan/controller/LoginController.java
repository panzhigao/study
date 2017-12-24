package com.pan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.service.UserService;


/**
 * 
 * @author Administrator
 * 用户登录
 */
@Controller
public class LoginController {
	
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 跳转登录页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/","login"})
	public String toLogin(){
		return "login";
	}
	
	/**
	 * 用户登陆
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/doLogin")
	@ResponseBody
	public ResultMsg doLogin(User user){
		logger.info("用户登陆，用户信息为：{}",user);
		ResultMsg resultMsg=null;
		try {
			userService.checkLogin(user);
			resultMsg=ResultMsg.ok("登陆成功");
		}catch(BusinessException e){
			resultMsg=ResultMsg.fail(e.getMessage());
		}catch (Exception e) {
			logger.error("登陆失败",e);
			resultMsg=ResultMsg.ok("登陆失败");
		}
		return resultMsg;
	}
	
	/**
	 * 登陆成功，跳转主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public String toIndex(){
		return "content/index";
	}
}
