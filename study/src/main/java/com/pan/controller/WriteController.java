package com.pan.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pan.entity.User;
import com.pan.util.CookieUtils;

/**
 * 用户创作
 * @author Administrator
 *
 */
@Controller
public class WriteController {
	
	/**
	 * 跳转发文页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/to_write")
	public ModelAndView toWrite(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("content/write");
		User user = CookieUtils.getLoginUser(request);
		mav.addObject("user", user);
		return mav;
	}
}
