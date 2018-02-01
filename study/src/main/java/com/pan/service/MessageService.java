package com.pan.service;

import java.util.List;

import com.pan.entity.Message;

/**
 * 
 * @author Administrator
 *
 */
public interface MessageService {
	/**
	 * 新增通知
	 */
	public void addMessage(Message message);
	/**
	 * 查询用户的所有消息
	 * @param userId
	 * @return
	 */
	public List<Message> findByReceiverUserId(String userId);
	/**
	 * 统计用户未读消息条数
	 * @param userId
	 * @param status
	 * @return
	 */
	public int countMessage(String userId,String status);
}
