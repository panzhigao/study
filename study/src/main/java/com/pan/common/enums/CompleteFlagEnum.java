package com.pan.common.enums;

public enum CompleteFlagEnum {

	NOT_COMPLETE(0, "审核未完成"),

	COMPLETE(1, "审核已完成");

	private CompleteFlagEnum(Integer code, String name) {
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
