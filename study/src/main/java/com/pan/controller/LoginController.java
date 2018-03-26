package com.pan.controller;

import java.util.List;
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
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.service.RoleService;
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
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 跳转登录页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="login")
	public ModelAndView toLogin(HttpServletRequest request,HttpServletResponse response){
		if(CookieUtils.getLoginUser(request)!=null){
			 String loginUserId = CookieUtils.getLoginUserId(request);
			 ModelAndView mv = new ModelAndView("redirect:/u/"+loginUserId);
			 return mv;
		}
		ModelAndView mav=new ModelAndView("html/user/login");
		String vercode=VerifyCodeUtils.generateVerifyCode(4);
		String cookieValue = CookieUtils.getCookieValue(request, MyConstant.SESSION_ID);
		JedisUtils.setString(MyConstant.USER_SESSION+cookieValue, vercode);
		mav.addObject("vercode", vercode);
		return mav;
	}
	
	/**
	 * 用户登陆
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/doLogin")
	@ResponseBody
	public ResultMsg doLogin(HttpServletRequest request,HttpServletResponse response,User user,String vercode){
		//TODO 密码输入多次错误
		logger.info("用户登陆，用户信息为：{}",user);
		User userInDb = userService.checkLogin(user,vercode);
		String token=UUID.randomUUID().toString();
		
		//设置cookie过期时间
		CookieUtils.setCookie(request, response, MyConstant.TOKEN,token,cookieMaxage);
		userInDb.setPassword(null);
		String json=JsonUtils.toJson(userInDb);
		JedisUtils.setStringExpire(MyConstant.USER_LOGINED+token, json, cookieMaxage);
		
		//用户角色信息放入redis
		List<String> list = roleService.getRoleByUserId(userInDb.getUserId());
		JedisUtils.setString("user_roles:"+userInDb.getUserId(), JsonUtils.toJson(list));
		
		//用户登录成功，将用户session添加到redis集合中
		request.getSession().setAttribute("userId", userInDb.getUserId());
		return ResultMsg.ok("用户登陆成功",userInDb.getUserId());
	}
		
	/**
	 * 用户退出
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/quit")
	public String quit(HttpServletRequest request,HttpServletResponse response){
		String token = CookieUtils.getCookieValue(request,MyConstant.TOKEN);
		String sessionId = CookieUtils.getCookieValue(request,MyConstant.SESSION_ID);
		if(token!=null){
			//立即过期redis中的登录状态
			JedisUtils.expire(MyConstant.USER_LOGINED+token, 0);
		}
		if(sessionId!=null){
			//立即过期redis中的session
			JedisUtils.expire(MyConstant.USER_SESSION+sessionId, 0);
		}
		CookieUtils.deleteCookie(request, response, MyConstant.TOKEN);
		return "redirect:/login";
	}
}
