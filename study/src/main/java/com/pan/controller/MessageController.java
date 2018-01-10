package com.pan.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.pan.service.UserService;


@Controller
public class MessageController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET,value="/user/message")
	public ModelAndView toIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/user/message");
		return mav;
	}
}
