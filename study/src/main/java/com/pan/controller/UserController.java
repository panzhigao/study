package com.pan.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.service.UserService;
import com.pan.util.CookieUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class UserController {
		
	@Autowired
	private UserService userService;
	
	@Value("${cookie.maxAge}")
	private int cookieMaxage;
	
	/**
	 * 跳转用户编辑个人信息页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/edit")
	public ModelAndView toUserEditPage(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("content/userEdit");
		User user = CookieUtils.getLoginUser(request);
		UserExtension userExtension=userService.findExtensionByUserId(user.getUserId());
		mav.addObject("user",user);
		mav.addObject("userExtension",userExtension);
		return mav;
	}
	
	/**
	 * 跳转用户主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/u/{userId}")
	public ModelAndView toUserIndex(HttpServletRequest request,@PathVariable("userId")String userId){
		ModelAndView mav=new ModelAndView("html/user/home");
		//用户信息
		User u = userService.findByUserId(userId);
		if(u==null){
			throw new BusinessException("用户不存在");
		}
		UserExtension userExtension=userService.findExtensionByUserId(u.getUserId());
		mav.addObject("u",u);
		mav.addObject("uExtension",userExtension);
		return mav;
	}
	
	/**
	 * 修改个人信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/doEdit")
	@ResponseBody
	public ResultMsg userEdit(HttpServletRequest request,User user,UserExtension userExtension){
		ResultMsg resultMsg=null;
		String userId = CookieUtils.getLoginUserId(request);
		String token = CookieUtils.getCookieValue(request, MyConstant.TOKEN);
		user.setUserId(userId);
		userService.updateUserInfo(user, userExtension);
		User userT = userService.findByUserId(userId);
		String json=JsonUtils.toJson(userT);
		JedisUtils.setStringExpire(MyConstant.USER_LOGINED+token, json, cookieMaxage);
		resultMsg=ResultMsg.ok("修改用户信息成功");
		return resultMsg;
	}
	
}
