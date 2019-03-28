package com.pan.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.pan.service.UserExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.exception.BusinessException;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.service.UserService;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class UserController {
		
	@Autowired
	private UserService userService;

	@Autowired
	private UserExtensionService userExtensionService;

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
		UserExtension userExtension=userExtensionService.selectByPrimaryKey(u.getId());
		mav.addObject("u",u);
		mav.addObject("uExtension",userExtension);
		return mav;
	}
	
}
