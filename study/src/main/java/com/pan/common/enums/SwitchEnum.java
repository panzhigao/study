package com.pan.common.enums;

/**
 * 开关枚举
 * @author Administrator
 */
public enum SwitchEnum {
	/**
	 * 关闭
	 */
	DISABLED("0", "关闭"),
	/**
	 * 开启
	 */
	ENABLED("1", "开启");

	private String value;

	private String name;

	private SwitchEnum(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
