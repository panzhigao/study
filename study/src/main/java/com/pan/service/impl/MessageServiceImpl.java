package com.pan.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.entity.Message;
import com.pan.mapper.MessageMapper;
import com.pan.service.MessageService;


/**
 * 
 * @author Administrator
 * 
 */
@Service
public class MessageServiceImpl implements MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	public void addMessage(Message message) {
		// TODO 校验
		logger.info("消息内容：{}",message);
		messageMapper.addMessage(message);
	}

	@Override
	public List<Message> findByReceiverUserId(String userId) {
		logger.info("用户id：{}",userId);
		if(userId==null){
			return new ArrayList<Message>();
		}
		return messageMapper.findByReceiverUserId(userId);
	}

	@Override
	public int countMessage(String userId, String status) {
		return messageMapper.countMessage(userId, status);
	}

	@Override
	public int cleanMessage(String userId, String messageId) {
		return messageMapper.cleanMessage(userId, messageId);
	}
}
