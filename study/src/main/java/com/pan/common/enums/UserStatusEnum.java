package com.pan.common.enums;

/**
 * 用户状态枚举
 * @author Administrator
 *
 */
public enum UserStatusEnum {
	/**
	 * 下线状态
	 */
	STATUS_BLOCKED(0, "下线状态"),
	/**
	 * 上线状态
	 */
	STATUS_NORMAL(1, "上线状态");

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
