package com.pan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pan.entity.Message;


/**
 * 
 * @author Administrator
 *
 */
public interface MessageMapper {
	/**
	 * 新增消息
	 */
	public void addMessage(Message message);
	/**
	 * 查询用户的所有消息
	 * @param userId
	 * @return
	 */
	public List<Message> findByReceiverUserId(String userId);
	/**
	 * 查询
	 * @param userId
	 * @return
	 */
	public int countMessage(@Param("receiverUserId")String receiverUserId,@Param("status")String status);
}
