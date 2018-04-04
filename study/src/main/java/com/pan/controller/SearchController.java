package com.pan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 作者
 * @version 创建时间：2018年4月4日 下午6:21:30
 * 类说明
 */
@Controller
public class SearchController {
	
	@RequestMapping(method=RequestMethod.GET,value="/search")
	public ModelAndView getSearchContent(String q){
		return null;
	}
	
}
