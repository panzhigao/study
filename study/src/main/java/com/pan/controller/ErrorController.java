package com.pan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 异常页面
 * @author Administrator
 *
 */
@Controller
public class ErrorController {
	
	@RequestMapping(value="/404")
	public String to404(){
		return "html/error/404";
	}
	
	@RequestMapping(value="/500")
	public String to500(){
		return "html/error/500";
	}
	
	/**
	 * 没有权限
	 * @return
	 */
	@RequestMapping(value="/unauth")
	public String toUnauthPage(){
		return "html/error/unauth";
	}
}
