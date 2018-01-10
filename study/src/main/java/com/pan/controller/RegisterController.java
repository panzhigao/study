package com.pan.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.constant.MyConstant;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.service.UserService;
import com.pan.util.CookieUtils;
import com.pan.util.JedisUtils;
import com.pan.util.VerifyCodeUtils;

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
	public ModelAndView toRegisterPage(HttpServletRequest request,HttpServletResponse response){
		String cookieValue = CookieUtils.getCookieValue(request, MyConstant.SESSION_ID);
		if(cookieValue==null){
			cookieValue=UUID.randomUUID().toString();
			CookieUtils.setCookie(request, response, MyConstant.SESSION_ID, cookieValue);
		}
		String vercode=VerifyCodeUtils.generateVerifyCode(4);
		JedisUtils.setString(MyConstant.USER_SESSION+cookieValue, vercode);
		ModelAndView mav=new ModelAndView("html/user/reg");
		mav.addObject("vercode", vercode);
		return mav;
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
		userService.saveUser(user);
		resultMsg=ResultMsg.ok("用户注册成功");
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
