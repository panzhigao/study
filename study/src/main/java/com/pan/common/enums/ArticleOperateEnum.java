package com.pan.common.enums;

public enum ArticleOperateEnum {
	/**
	 * 取消置顶
	 */
	CANCEL_STICK(0,"取消置顶"),
	/**
	 * 置顶
	 */
	STICK(1,"置顶"),
	/**
	 * 取消加精
	 */
	NOT_HIGH_QUALITY(0,"取消加精"),
	/**
	 * 加精
	 */
	HIGH_QUALITY(1,"加精");
	
	private Integer code;

    private String name;

    ArticleOperateEnum(Integer code, String name) {
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
