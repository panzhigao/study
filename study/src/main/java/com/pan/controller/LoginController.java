package com.pan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.service.UserService;
import com.pan.util.CookieUtils;
import com.pan.util.TokenUtils;
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
		if(CookieUtils.getLoginUser(request)!=null){
			 String loginUserId = CookieUtils.getLoginUserId(request);
			 ModelAndView mv = new ModelAndView("redirect:/u/"+loginUserId);
			 return mv;
		}
		ModelAndView mav=new ModelAndView("html/user/login");
		//生成验证码
		String vercode=VerifyCodeUtils.generateVerifyCode(4);
		SecurityUtils.getSubject().getSession().setAttribute("vercode", vercode);
		//String vercode=VerifyCodeUtils.generateVerifyCode(4);
		//String cookieValue = CookieUtils.getCookieValue(request, MyConstant.SESSION_ID);
		//JedisUtils.setStringExpire(MyConstant.USER_SESSION+cookieValue, vercode,3600);
		//mav.addObject("vercode", vercode);
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
		String vercodeInSession=(String)TokenUtils.getAttribute("vercode");
		if(!StringUtils.equals(vercode, vercodeInSession)){
			throw new BusinessException("验证码错误");
		}
		UsernamePasswordToken passwordToken=new UsernamePasswordToken(user.getUsername(),user.getPassword());
		Subject subject = SecurityUtils.getSubject();
		subject.login(passwordToken);
		User userInDb = userService.findByUsername(user.getUsername());
		
		
//		User userInDb = userService.checkLogin(user,vercode);
//		String token=UUID.randomUUID().toString();
//		
//		//设置cookie过期时间
//		CookieUtils.setCookie(request, response, MyConstant.TOKEN,token,cookieMaxage);
//		userInDb.setPassword(null);
//		String json=JsonUtils.toJson(userInDb);
//		JedisUtils.setStringExpire(MyConstant.USER_LOGINED+token, json, cookieMaxage);
		
		//用户角色信息放入redis
//		List<String> list = roleService.getRoleByUserId(userInDb.getUserId());
//		JedisUtils.setString("user_roles:"+userInDb.getUserId(), JsonUtils.toJson(list));
		
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
		//CookieUtils.cleanUserLoginTrace();
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/login";
	}
}
