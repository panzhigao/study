package com.pan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.annotation.CheckUsernameGroup;
import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.service.UserService;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;
import com.pan.util.VerifyCodeUtils;

/**
 * 注册
 * @author Administrator
 *
 */
@Controller
public class RegisterController {
	
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	@Value("${cookie.maxAge}")
	private int cookieMaxage;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 跳转注册页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/register")
	public ModelAndView toRegisterPage(HttpServletRequest request,HttpServletResponse response){
		if(TokenUtils.getLoginUser()!=null){
			 ModelAndView mv = new ModelAndView("redirect:/user/home");
			 return mv;
		}
		String vercode=VerifyCodeUtils.generateVerifyCode(4);
		TokenUtils.setAttribute(MyConstant.VERCODE, vercode);
		ModelAndView mav=new ModelAndView("html/user/reg");
		mav.addObject("vercode", vercode);
		return mav;
	}
	
	/**
	 * 用户注册操作
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/doRegister")
	@ResponseBody
	public ResultMsg register(User user,String vercode){
		logger.info("注册开始,用户信息为：{}",user);
		String vercodeInSession=(String)TokenUtils.getAttribute(MyConstant.VERCODE);
		if(!StringUtils.equals(vercode, vercodeInSession)){
			throw new BusinessException("验证码错误");
		}
		userService.saveUser(user);
		return ResultMsg.ok("用户注册成功");
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/checkUnique")
	@ResponseBody
	public Map<String,Object> checkUnique(String username){
		logger.info("校验用户名是否已注册：{}",username);
		User checkUser=new User();
		checkUser.setUsername(username);
		Map<String,Object> resultMap=new HashMap<String, Object>(2);
		try {
			ValidationUtils.validateEntityWithGroups(checkUser, CheckUsernameGroup.class);
		} catch (BusinessException e) {
			resultMap.put("valid", false);
			resultMap.put("msg", e.getMessage());
			return resultMap;
		}
		User user = userService.findByUsername(username);
		if(user==null){
			resultMap.put("valid", true);
		}else{
			resultMap.put("valid", false);
			resultMap.put("msg", "该用户名已被占用，请换一个");
		}
		return resultMap;
	}
}
