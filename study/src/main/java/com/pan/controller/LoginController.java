package com.pan.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.service.UserService;
import com.pan.util.CookieUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;


/**
 * 
 * @author Administrator
 * 用户登录
 */
@Controller
public class LoginController {
	
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	@Value("${cookie.maxAge}")
	private int cookieMaxage;
	
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
	public ResultMsg doLogin(HttpServletRequest request, HttpServletResponse response,User user){
		logger.info("用户登陆，用户信息为：{}",user);
		ResultMsg resultMsg=null;
		try {
			User userInDb = userService.checkLogin(user);
			resultMsg=ResultMsg.ok("登陆成功");
			String token=UUID.randomUUID().toString();
			//设置cookie过期时间
			CookieUtils.setCookie(request, response, MyConstant.TOKEN,token,cookieMaxage);
			String json=JsonUtils.toJson(userInDb);
			JedisUtils.setStringExpire(token, json, cookieMaxage);
		}catch(BusinessException e){
			resultMsg=ResultMsg.fail(e.getMessage());
		}catch (Exception e) {
			logger.error("登陆失败",e);
			resultMsg=ResultMsg.fail("登陆失败");
		}
		return resultMsg;
	}
	
	/**
	 * 登陆成功，跳转主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public ModelAndView toIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("content/index");
		User user = CookieUtils.getLoginUser(request);
		mav.addObject("user", user);
		return mav;
	}
}
