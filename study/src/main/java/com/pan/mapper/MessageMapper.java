package com.pan.mapper;


import org.apache.ibatis.annotations.Param;
import com.pan.entity.Message;


/**
 * 
 * @author Administrator
 */
public interface MessageMapper extends BaseMapper<Message>{
	/**
	 * 消息标记为已读
	 * @param receiverUserId
	 * @param id 消息id
	 * @return
	 */
	int cleanMessage(@Param("receiverUserId")Long receiverUserId,@Param("id")String id);
}
