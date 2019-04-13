package com.pan.mapper;


import org.apache.ibatis.annotations.Param;

import com.pan.entity.Message;


/**
 * 
 * @author Administrator
 */
public interface MessageMapper extends BaseMapper<Message>{
	/**
	 * 根据消息接收者id更新消息状态
	 * @param status
	 * @param receiverUserId
	 * @return
	 */
	int updateStatusByReceiverUserId(@Param("status")Integer status,@Param("receiverUserId")Long receiverUserId);
}
