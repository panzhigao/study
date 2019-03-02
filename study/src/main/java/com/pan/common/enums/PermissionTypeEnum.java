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

	private String desc;

	PermissionTypeEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
