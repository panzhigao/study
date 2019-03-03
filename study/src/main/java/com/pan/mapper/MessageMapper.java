package com.pan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pan.entity.Message;


/**
 * 
 * @author Administrator
 */
//TODO 消息查询接口优化
public interface MessageMapper extends BaseMapper<Message>{
	/**
	 * 查询用户的所有消息
	 * @param userId
	 * @return
	 */
	public List<Message> findByReceiverUserId(String userId);
	/**
	 * 消息标记为已读
	 * @param receiverUserId
	 * @param messageId
	 * @return
	 */
	public int cleanMessage(@Param("receiverUserId")String receiverUserId,@Param("messageId")String messageId);
}
