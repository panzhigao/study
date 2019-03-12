package com.pan.common.enums;

/**
 * @author panzhigao
 */
public enum ArticleTypeEnum {
	/**
	 * 文章
	 */
	TYPE_ARTICLE(1, "文章"),
	/**
	 * 审核未通过
	 */
	TYPE_SYSTEM_NOTICE(2, "系统公告");

	private Integer code;

	private String name;

	ArticleTypeEnum(Integer code, String msg) {
		this.code = code;
		this.name = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}


}
