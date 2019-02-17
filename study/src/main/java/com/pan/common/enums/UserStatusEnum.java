package com.pan.common.enums;

/**
 * 用户状态枚举
 * @author Administrator
 *
 */
public enum UserStatusEnum {
	/**
	 * 禁用状态
	 */
	STATUS_BLOCKED("0", "禁用状态"),
	/**
	 * 正常状态
	 */
	STATUS_NORMAL("1", "正常状态");

	private String code;

	private String desc;

	private UserStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
