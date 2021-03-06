package com.pan.common.enums;

public enum LinkStatusEnum {
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

	LinkStatusEnum(Integer code, String name) {
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
