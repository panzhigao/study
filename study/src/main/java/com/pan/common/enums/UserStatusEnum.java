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
	STATUS_BLOCKED(0, "禁用状态"),
	/**
	 * 正常状态
	 */
	STATUS_NORMAL(1, "正常状态");

	private Integer code;

	private String name;

	UserStatusEnum(Integer code, String name) {
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
