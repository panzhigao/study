package com.pan.controller;

import javax.servlet.http.HttpServletRequest;

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
	
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);
	
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
		UserExtension userExtension=userService.findByUserId(user.getUserId());
		mav.addObject("user",user);
		mav.addObject("userExtension",userExtension);
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
		String userId = CookieUtils.getLoingUserId(request);
		String token = CookieUtils.getCookieValue(request, MyConstant.TOKEN);
		try {
			user.setUserId(userId);
			userService.updateUserInfo(user, userExtension);
			User userT = userService.findByUserid(userId);
			String json=JsonUtils.toJson(userT);
			JedisUtils.setStringExpire(MyConstant.USER_SESSION+token, json, cookieMaxage);
			resultMsg=ResultMsg.ok("修改用户信息成功");
		}catch(BusinessException e){
			logger.error("修改用户信息失败",e);
			resultMsg=ResultMsg.fail(e.getMessage());
		}catch (Exception e) {
			logger.error("修改用户信息失败",e);
			resultMsg=ResultMsg.fail("修改用户信息失败");
		}
		return resultMsg;
	}
	
}
