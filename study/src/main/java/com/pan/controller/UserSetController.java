package com.pan.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * 用户基本设置
 * @author Administrator
 *
 */
@Controller
public class UserSetController {
	
	
	/**
	 * 登陆成功，跳转用户设置页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/set")
	public ModelAndView toIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/user/set");
		return mav;
	}
}
