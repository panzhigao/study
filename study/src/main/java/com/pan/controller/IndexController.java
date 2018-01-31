package com.pan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 网站首页
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	/**
	 * 跳转网站主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/")
	public ModelAndView toLogin(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav=new ModelAndView("html/index");
		return mav;
	}
}
