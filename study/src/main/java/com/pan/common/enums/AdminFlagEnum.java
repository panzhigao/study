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
	ADMIN_FALSE(0, "不是管理员"),
	/**
	 * 是管理员
	 */
	ADMIN_TRUE(1, "是管理员");

	private Integer code;

	private String name;

	private AdminFlagEnum(Integer code, String name) {
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
