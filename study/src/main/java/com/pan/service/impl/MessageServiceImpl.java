package com.pan.service.impl;


import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.enums.MessageStatusEnum;
import com.pan.common.enums.MessageTypeEnum;
import com.pan.common.exception.BusinessException;
import com.pan.entity.Message;
import com.pan.entity.User;
import com.pan.mapper.MessageMapper;
import com.pan.query.QueryMessage;
import com.pan.service.AbstractBaseService;
import com.pan.service.IMessageService;
import com.pan.util.JsonUtils;
import com.pan.util.MessageUtils;
import com.pan.util.TokenUtils;


/**
 * 
 * @author Administrator
 * 
 */
@Service
public class MessageServiceImpl extends AbstractBaseService<Message, MessageMapper> implements IMessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Autowired
	private MessageMapper messageMapper;
	
//	@Override
//	protected MessageMapper getBaseMapper() {
//		return messageMapper;
//	}
	

	@Override
	public int countMessage(Long userId, Integer status) {
		QueryMessage queryMessage=new QueryMessage();
		queryMessage.setReceiverUserId(userId);
		queryMessage.setStatus(status);
		return messageMapper.countByParams(queryMessage);
	}
	
	/**
	 * 消息置为已读
	 * @param userId 用户id
	 * @param messageId 消息id，当消息id没有传入时，所有消息标记为已读
	 * @return
	 */
	@Override
	public int updateMessageReaded(Long userId, Long messageId) {
		if(messageId==null){
			return messageMapper.updateStatusByReceiverUserId(MessageStatusEnum.MESSAGE_READED.getCode(), userId);
		}
		Message selectByPrimaryKey = messageMapper.selectByPrimaryKey(messageId);
		if(selectByPrimaryKey==null){
			logger.error("根据消息id={}未查询到消息信息",messageId);
			throw new BusinessException("该消息不存在");
		}
		if(!selectByPrimaryKey.getReceiverUserId().equals(userId)){
			throw new BusinessException("不能操作非本人的消息");
		}
		Message message=new Message();
		message.setId(messageId);
		message.setStatus(MessageStatusEnum.MESSAGE_READED.getCode());
		return messageMapper.updateByPrimaryKeySelective(message);
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
		message.setStatus(MessageStatusEnum.MESSAGE_NOT_READED.getCode());
		message.setMessageType(messageTypeEnum.getCode());
		message.setCreateTime(new Date());
		messageMapper.insertSelective(message);
		MessageUtils.sendToUser(message.getReceiverUserId(), JsonUtils.toJson(message));
	}
}
