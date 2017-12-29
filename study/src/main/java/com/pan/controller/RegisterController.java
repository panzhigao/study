package com.pan.controller;

import java.util.HashMap;
import java.util.Map;

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
 * 注册
 * @author Administrator
 *
 */
@Controller
public class RegisterController {
	
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
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
	@RequestMapping(method=RequestMethod.POST,value="/doRegister")
	@ResponseBody
	public ResultMsg register(User user){
		logger.info("注册开始,用户信息为：{}",user);
		ResultMsg resultMsg=null;
		try {
			userService.saveUser(user);
			resultMsg=ResultMsg.ok("用户注册成功");
		}catch(BusinessException e){
			resultMsg=ResultMsg.fail(e.getMessage());
		}catch (Exception e) {
			logger.error("注册用户失败",e);
			resultMsg=ResultMsg.fail("注册用户失败");
		}
		return resultMsg;
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/checkUnique")
	@ResponseBody
	public Map<String,Object> checkUnique(String username){
		logger.info("校验用户名是否已注册：{}",username);
		Map<String,Object> resultMap=new HashMap<String, Object>(1);
		User user = userService.findByUsername(username);
		if(user==null){
			resultMap.put("valid", true);
		}else{
			resultMap.put("valid", false);
		}
		return resultMap;
	}
}
