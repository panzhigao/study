package com.pan.common.enums;

/**
 * @author panzhigao
 */
public enum CompleteFlagEnum {
	/**
	 * 审核未完成
	 */
	NOT_COMPLETE(0, "审核未完成"),
	/**
	 * 审核已完成
	 */
	COMPLETE(1, "审核已完成");

	CompleteFlagEnum(Integer code, String name) {
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
