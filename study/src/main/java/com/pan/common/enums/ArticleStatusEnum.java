package com.pan.common.enums;

/**
 * @author panzhigao
 */

public enum ArticleStatusEnum {
	/**
	 * 审核未通过
	 */
	FAIL_CHECKED(0,"审核未通过"),
	/**
	 * 审核未通过
	 */
	SKETCH(1,"草稿"),
	/**
	 * 审核中
	 */
	IN_CHECK(2,"审核中"),
	/**
	 * 发布成功
	 */
	PUBLIC_SUCCESS(3,"发布成功"),
	/**
	 * 下线
	 */
	OFFLINE(4,"下线");
	
	private Integer code;

    private String name;

    ArticleStatusEnum(Integer code, String name) {
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
