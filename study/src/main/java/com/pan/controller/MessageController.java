package com.pan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.constant.MyConstant;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Message;
import com.pan.service.MessageService;
import com.pan.service.UserService;
import com.pan.util.CookieUtils;


@Controller
public class MessageController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(method=RequestMethod.GET,value="/user/message")
	public ModelAndView toIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/user/message");
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/message/load")
	@ResponseBody
	public ResultMsg loadMessages(){
		String loginUserId = CookieUtils.getLoginUserId();
		List<Message> list=messageService.findByReceiverUserId(loginUserId);
		return ResultMsg.ok("加载消息成功", list);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/message/count")
	@ResponseBody
	public ResultMsg getUnreadMessageCount(){
		String loginUserId = CookieUtils.getLoginUserId();
		int count=messageService.countMessage(loginUserId, MyConstant.MESSAGE_NOT_READED);
		return ResultMsg.ok("统计成功", count);
	}
}
