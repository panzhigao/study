package com.pan.controller;

import javax.servlet.http.HttpServletRequest;
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
import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.service.UserService;
import com.pan.util.RegexUtils;
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
	@RequestMapping(method=RequestMethod.GET,value="/loginPage")
	public ModelAndView toLogin(){
		if(TokenUtils.getLoingUserId()!=null){
			 String loginUserId = TokenUtils.getLoingUserId();
			 ModelAndView mv = new ModelAndView("redirect:/u/"+loginUserId);
			 return mv;
		}
		ModelAndView mav=new ModelAndView("html/user/login");
		//生成验证码
		String vercode=VerifyCodeUtils.generateVerifyCode(4);
		TokenUtils.setAttribute(MyConstant.VERCODE, vercode);
		return mav;
	}
	
	/**
	 * 用户登陆
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/doLogin")
	@ResponseBody
	public ResultMsg doLogin(HttpServletRequest request,User user,String vercode){
		//TODO 密码输入多次错误
		logger.info("用户登陆，用户信息为：{}",user);
		String vercodeInSession=(String)TokenUtils.getAttribute(MyConstant.VERCODE);
		if(!StringUtils.equalsIgnoreCase(vercode, vercodeInSession)){
			throw new BusinessException("验证码错误");
		}
		UsernamePasswordToken passwordToken=new UsernamePasswordToken(user.getUsername(),user.getPassword());
		Subject subject = SecurityUtils.getSubject();
		subject.login(passwordToken);
		request.getSession().setAttribute(MyConstant.USER_ID, TokenUtils.getAttribute(MyConstant.USER_ID));
		//手机号登陆
		User userInDb=null;
		if(RegexUtils.checkTelephone(user.getUsername())){
			userInDb = userService.findByUserTelephone(user.getUsername());
		}else{
			userInDb = userService.findByUsername(user.getUsername());
		}
		return ResultMsg.ok("用户登陆成功",userInDb.getUserId());
	}
		
	/**
	 * 用户退出
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/quit")
	public String quit(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/login";
	}
}
