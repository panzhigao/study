package com.pan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pan.service.ArticleService;
import com.pan.util.CookieUtils;

/**
 * 用户中心
 * @author Administrator
 *
 */
@Controller
public class UserCenterController {
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 登陆成功，跳转用户设置页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/center")
	public ModelAndView toIndex(HttpServletRequest request){
		String loingUserId = CookieUtils.getLoingUserId(request);
		Map<String,Object> params=new HashMap<String, Object>(2);
		params.put("userId", loingUserId);
		int count=articleService.getCount(params);
		ModelAndView mav=new ModelAndView("html/user/center");
		mav.addObject("myCounts", count);
		return mav;
	}
}
