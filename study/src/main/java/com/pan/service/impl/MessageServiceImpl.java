package com.pan.service.impl;


import java.util.Date;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import com.pan.util.TransFieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.enums.MessageStatusEnum;
import com.pan.common.enums.MessageTypeEnum;
import com.pan.entity.Message;
import com.pan.entity.User;
import com.pan.mapper.MessageMapper;
import com.pan.query.QueryMessage;
import com.pan.service.AbstractBaseService;
import com.pan.service.MessageService;
import com.pan.util.IdUtils;
import com.pan.util.JsonUtils;
import com.pan.util.MessageUtils;
import com.pan.util.TokenUtils;


/**
 * 
 * @author Administrator
 * 
 */
@Service
public class MessageServiceImpl extends AbstractBaseService<Message, MessageMapper> implements MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	protected MessageMapper getBaseMapper() {
		return messageMapper;
	}
	

	@Override
	public int countMessage(Long userId, Integer status) {
		QueryMessage queryMessage=new QueryMessage();
		queryMessage.setReceiverUserId(userId);
		queryMessage.setStatus(status);
		return messageMapper.countByParams(queryMessage);
	}

	@Override
	public int cleanMessage(Long userId, String messageId) {
		return messageMapper.cleanMessage(userId, messageId);
	}

	@Override
	public void sendMessageToUser(Message message,MessageTypeEnum messageTypeEnum) {
		if(message==null){
			return;
		}
		logger.info("发送消息，接收消息userId={}",message.getReceiverUserId());
		User loginUser = TokenUtils.getLoginUser();
		message.setSenderUserId(loginUser.getId());
		message.setSenderName(loginUser.getNickname());
		message.setMessageId(IdUtils.generateMessageId());
		message.setStatus(MessageStatusEnum.MESSAGE_NOT_READED.getCode());
		message.setMessageType(messageTypeEnum.getCode());
		message.setCreateTime(new Date());
		messageMapper.insertSelective(message);
		MessageUtils.sendToUser(message.getReceiverUserId(), JsonUtils.toJson(message));
	}
}
