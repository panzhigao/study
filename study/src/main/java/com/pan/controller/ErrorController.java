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
		return "error/404";
	}
	
	@RequestMapping(value="/500")
	public String to500(){
		return "error/500";
	}
}
