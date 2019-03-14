package com.pan.common.enums;

/**
 * 审核结果类型
 * 
 * @author Administrator
 *
 */
public enum ApproveFlagEnum {
	/**
	 * 文章审核未通过
	 */
	NOT_APPROVED(0, "审核未通过"),
	/**
	 * 文章审核通过
	 */
	APPROVED(1, "审核通过");

	ApproveFlagEnum(Integer code, String name) {
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
