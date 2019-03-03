package com.pan.common.enums;

/**
 * 用户状态枚举
 * 
 * @author Administrator
 *
 */
public enum PermissionTypeEnum {
	/**
	 * 菜单
	 */
	MENU(0, "菜单"),
	/**
	 * 链接
	 */
	LINK(1, "链接"),
	/**
	 * 按钮
	 */
	BUTTON(2, "按钮");

	private Integer code;

	private String name;

	PermissionTypeEnum(Integer code, String name) {
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
