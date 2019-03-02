package com.pan.controller;


import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
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
import com.pan.common.enums.ResultCodeEnum;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.service.UserService;
import com.pan.util.RSAUtil;
import com.pan.util.RegexUtils;
import com.pan.util.TokenUtils;


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
	@RequestMapping(method=RequestMethod.GET,value="/login")
	public ModelAndView toLogin(){
		if(TokenUtils.getLoginUserId()!=null){
			 String loginUserId = TokenUtils.getLoginUserId();
			 ModelAndView mv = new ModelAndView("redirect:/u/"+loginUserId);
			 return mv;
		}
		ModelAndView mav=new ModelAndView("html/user/login");
		return mav;
	}
	
	/**
	 * 用户登陆
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(method=RequestMethod.POST,value="/doLogin")
	@ResponseBody
	public ResultMsg doLogin(HttpServletRequest request,User user,String vercode) throws Exception{
		//TODO 密码输入多次错误
		logger.info("用户登陆，用户信息为：{}",user);
		String vercodeInSession=(String)TokenUtils.getAttribute(MyConstant.VERCODE);
		if(!StringUtils.equalsIgnoreCase(vercode, vercodeInSession)){
			throw new BusinessException("验证码错误");
		}
		//获取私钥
		String privateKey=(String)TokenUtils.getAttribute(MyConstant.PRIVATE_KEY);
		String password=user.getPassword();
		//解密密码
		String decodeByPrivateKey = RSAUtil.decodeByPrivateKey(password, privateKey);
		UsernamePasswordToken passwordToken=new UsernamePasswordToken(user.getUsername(),decodeByPrivateKey);
		Subject subject = SecurityUtils.getSubject();
		subject.login(passwordToken);
		//手机号登陆
		User userInDb;
		if(RegexUtils.checkTelephone(user.getUsername())){
			userInDb = userService.findByUserTelephone(user.getUsername());
		}else{
			userInDb = userService.findByUsername(user.getUsername());
		}
		//获取跳转前的url
		String redirectUrl="";
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		if(savedRequest!=null){
			redirectUrl=savedRequest.getRequestUrl();
			logger.info("跳转登陆页前的url:{}",redirectUrl);
		}
		if(StringUtils.isNotBlank(redirectUrl)&&!MyConstant.DEFAULT_REQUEST_URL.equals(redirectUrl)){
			return ResultMsg.build(ResultCodeEnum.REDIRECT, "用户登陆成功",redirectUrl);
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
