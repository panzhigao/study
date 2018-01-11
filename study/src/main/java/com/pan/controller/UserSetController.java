package com.pan.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.pan.entity.UserExtension;
import com.pan.service.UserService;
import com.pan.util.CookieUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;


/**
 * 用户基本设置
 * @author Administrator
 *
 */
@Controller
public class UserSetController {
	
	@Autowired
	private UserService userService;
	
	@Value("${cookie.maxAge}")
	private int cookieMaxage;
	
	/**
	 * 登陆成功，跳转用户设置页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/set")
	public ModelAndView toIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/user/set");
		User user = CookieUtils.getLoginUser(request);
		UserExtension userExtension=userService.findByUserId(user.getUserId());
		mav.addObject("user",user);
		mav.addObject("userExtension",userExtension);
		return mav;
	}
	
	/**
	 * 修改个人信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/doSet")
	@ResponseBody
	public ResultMsg userEdit(HttpServletRequest request,User user,UserExtension userExtension){
		ResultMsg resultMsg=null;
		String userId = CookieUtils.getLoingUserId(request);
		String token = CookieUtils.getCookieValue(request, MyConstant.TOKEN);
		user.setUserId(userId);
		userService.updateUserInfo(user, userExtension);
		User userT = userService.findByUserid(userId);
		String json=JsonUtils.toJson(userT);
		JedisUtils.setStringExpire(MyConstant.USER_LOGINED+token, json, cookieMaxage);
		resultMsg=ResultMsg.ok("修改用户信息成功");
		return resultMsg;
	}
}
