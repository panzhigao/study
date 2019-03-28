package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryMessage extends QueryBase{
	/**
	 * 消息状态 0-未读 1-已读
	 */
	private Integer status;
	/**
	 * 消息接收者id
	 */
	private String receiverUserId;
}
