package com.pan.service;


import com.pan.common.enums.MessageTypeEnum;
import com.pan.entity.Message;

/**
 * 
 * @author Administrator
 *
 */
public interface IMessageService extends BaseService<Message>{
	/**
	 * 发送消息到指定用户
	 * @param message 消息内容
	 * @param messageTypeEnum
	 */
	void sendMessageToUser(Message message,MessageTypeEnum messageTypeEnum);
	/**
	 * 统计用户未读消息条数
	 * @param userId
	 * @param status 消息状态
	 * @return
	 */
	int countMessage(Long userId,Integer status);
	/**
	 * 消息标记为已读
	 * @param userId
	 * @param messageId
	 * @return
	 */
	int updateMessageReaded(Long userId, Long messageId);
}
