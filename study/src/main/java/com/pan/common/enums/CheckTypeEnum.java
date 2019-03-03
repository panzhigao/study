package com.pan.common.enums;

/**
 * 审核类型
 * 
 * @author Administrator
 *
 */
public enum CheckTypeEnum {
	CREATE(0, "创建"), UPDATE(1, "修改");

	private CheckTypeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	private Integer code;

	private String name;

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}
