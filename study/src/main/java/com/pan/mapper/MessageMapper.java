package com.pan.mapper;


import org.apache.ibatis.annotations.Param;
import com.pan.entity.Message;


/**
 * 
 * @author Administrator
 */
//TODO 消息查询接口优化
public interface MessageMapper extends BaseMapper<Message>{
	/**
	 * 消息标记为已读
	 * @param receiverUserId
	 * @param messageId
	 * @return
	 */
	int cleanMessage(@Param("receiverUserId")String receiverUserId,@Param("messageId")String messageId);
}
