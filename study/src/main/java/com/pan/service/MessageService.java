package com.pan.service;


import com.pan.common.enums.MessageTypeEnum;
import com.pan.entity.Message;

/**
 * 
 * @author Administrator
 *
 */
public interface MessageService extends BaseService<Message>{
	/**
	 * 发送消息到指定用户
	 * @param message 消息内容
	 */
	void sendMessageToUser(Message message,MessageTypeEnum messageTypeEnum);
	/**
	 * 统计用户未读消息条数
	 * @param userId
	 * @param status 消息状态
	 * @return
	 */
	int countMessage(String userId,Integer status);
	/**
	 * 消息标记为已读
	 * @param userId
	 * @param messageId
	 * @return
	 */
	int cleanMessage(String userId,String messageId);
}
