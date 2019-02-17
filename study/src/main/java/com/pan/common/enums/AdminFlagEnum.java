package com.pan.common.enums;

/**
 * 管理员标志枚举
 * 
 * @author Administrator
 *
 */
public enum AdminFlagEnum {
	/**
	 * 不是管理员
	 */
	ADMIN_FALSE("0", "不是管理员"),
	/**
	 * 是管理员
	 */
	ADMIN_TRUE("1", "是管理员");

	private String code;

	private String desc;

	private AdminFlagEnum(String code, String desc) {
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
