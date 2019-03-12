package com.pan.service;


import com.pan.entity.Message;
import com.pan.query.QueryMessage;

import java.util.Map;

/**
 * 
 * @author Administrator
 *
 */
public interface MessageService extends BaseService<Message>{
	/**
	 * 新增通知
	 * @param message
	 */
	void addMessage(Message message);
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
	//TODO 修改方法名
	int cleanMessage(String userId,String messageId);
	/**
	 * 分页查询数据
	 * @param queryMessage
	 * @return
	 */
	Map<String, Object> findByParams(QueryMessage queryMessage);
}
