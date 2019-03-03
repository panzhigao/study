package com.pan.common.enums;

/**
 * @author panzhigao 消息状态
 */

public enum MessageStatusEnum {
	/**
	 * 未读
	 */
	MESSAGE_NOT_READED(0, "未读"),
	/**
	 * 已读
	 */
	MESSAGE_READED(1, "已读");

	private Integer code;

	private String name;

	MessageStatusEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}
