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
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.constant.MyConstant;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.service.UserService;
import com.pan.util.CookieUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;
import com.pan.util.VerifyCodeUtils;


/**
 * 
 * @author Administrator
 * 用户登录
 */
@Controller
public class LoginController{
	
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	@Value("${cookie.maxAge}")
	private int cookieMaxage;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 跳转登录页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="login")
	public ModelAndView toLogin(HttpServletRequest request,HttpServletResponse response){
		String cookieValue = CookieUtils.getCookieValue(request, MyConstant.SESSION_ID);
		if(cookieValue==null){
			cookieValue=UUID.randomUUID().toString();
			CookieUtils.setCookie(request, response, MyConstant.SESSION_ID, cookieValue);
		}
		String vercode=VerifyCodeUtils.generateVerifyCode(4);
		JedisUtils.setString(MyConstant.USER_SESSION+cookieValue, vercode);
		ModelAndView mav=new ModelAndView("html/user/login");
		mav.addObject("vercode", vercode);
		return mav;
	}
	
	/**
	 * 用户登陆
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/doLogin")
	public String doLogin(HttpServletRequest request,HttpServletResponse response,User user){
		//TODO 密码输入多次错误
		logger.info("用户登陆，用户信息为：{}",user);
		User userInDb = userService.checkLogin(user);
		String token=UUID.randomUUID().toString();
		//设置cookie过期时间
		CookieUtils.setCookie(request, response, MyConstant.TOKEN,token,cookieMaxage);
		userInDb.setPassword(null);
		String json=JsonUtils.toJson(userInDb);
		JedisUtils.setStringExpire(MyConstant.USER_LOGINED+token, json, cookieMaxage);
		return "redirect:/user/home";
	}
	
	/**
	 * 登陆成功，跳转用户home页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/home")
	public ModelAndView toIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/user/home");
		User user = CookieUtils.getLoginUser(request);
		mav.addObject("user",user);
		UserExtension userExtension=userService.findByUserId(user.getUserId());
		mav.addObject("userExtension",userExtension);
		return mav;
	}
	
	/**
	 * 用户退出
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/quit")
	public String quit(HttpServletRequest request,HttpServletResponse response){
		String cookieValue = CookieUtils.getCookieValue(request,MyConstant.TOKEN);
		if(cookieValue!=null){
			//立即过期redis中的session
			JedisUtils.expire(MyConstant.USER_SESSION+cookieValue, 0);
		}
		CookieUtils.cleanCookies(request, response);
		return "redirect:/login";
	}
}
