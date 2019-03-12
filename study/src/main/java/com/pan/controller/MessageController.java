package com.pan.controller;

import java.util.Map;
import com.pan.query.QueryMessage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.enums.MessageStatusEnum;
import com.pan.common.vo.ResultMsg;
import com.pan.service.MessageService;
import com.pan.util.TokenUtils;


/**
 *
 * @author panzhigao
 */
@Controller
public class MessageController {

	
	@Autowired
	private MessageService messageService;

	/**
	 * 跳转系统消息页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/message")
	@RequiresPermissions("/user/message")
	public ModelAndView toIndex(){
		ModelAndView mav=new ModelAndView("html//message/messagePage");
		String loginUserId = TokenUtils.getLoginUserId();
		int count=messageService.countMessage(loginUserId, MessageStatusEnum.MESSAGE_NOT_READED.getCode());
		mav.addObject("unread",count>0);
		return mav;
	}

	/**
	 * 加载系统消息
	 * @param queryMessage
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/message/load")
	@ResponseBody
	@RequiresPermissions(value="/user/message")
	public ResultMsg loadMessages(QueryMessage queryMessage){
		String loginUserId = TokenUtils.getLoginUserId();
		queryMessage.setReceiverUserId(loginUserId);
		Map<String, Object> result = messageService.findByParams(queryMessage);
		return ResultMsg.ok("加载消息成功", result);
	}

	/**
	 * 统计未读消息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/message/count")
	@ResponseBody
	@RequiresPermissions(value="/user/message")
	public ResultMsg getUnreadMessageCount(){
		String loginUserId = TokenUtils.getLoginUserId();
		int count=messageService.countMessage(loginUserId, MessageStatusEnum.MESSAGE_NOT_READED.getCode());
		return ResultMsg.ok("统计成功", count);
	}

	/**
	 * 将系统消息置为已读
	 * @param messageId 消息id
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/message/clean")
	@ResponseBody
	@RequiresPermissions(value="/user/message")
	public ResultMsg cleanMessage(String messageId){
		String loginUserId = TokenUtils.getLoginUserId();
		int count=messageService.cleanMessage(loginUserId, messageId);
		return ResultMsg.ok("消息置为已读成功", count);
	}
}
