package com.pan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.constant.MyConstant;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Article;
import com.pan.entity.Message;
import com.pan.service.ArticleService;
import com.pan.service.MessageService;
import com.pan.service.UserService;
import com.pan.util.CookieUtils;


@Controller
public class MessageController {
	
	private static final Logger logger=LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ArticleService articleService;
	
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
	
	@RequestMapping(method=RequestMethod.POST,value="/user/message/clean")
	@ResponseBody
	public ResultMsg cleanMessage(String messageId){
		String loginUserId = CookieUtils.getLoginUserId();
		int count=messageService.cleanMessage(loginUserId, messageId);
		return ResultMsg.ok("消息置为已读成功", count);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/user/systemMessage")
	public ModelAndView toSendMessageIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/user/sendMessage");
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/message/send")
	@ResponseBody
	public ResultMsg sendMessage(Article article,String messageId){
		logger.info("发布消息开始");
		String userId=CookieUtils.getLoginUserId();
		article.setUserId(userId);
		articleService.saveSystemMessage(article);
		return ResultMsg.ok("消息发布成功");
	}
}
