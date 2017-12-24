package com.pan.common.enums;

/**
 * 错误码
 * @author Administrator
 *
 */
public enum ResultCodeEmun {
	/**
	 * 操作成功码
	 */
	OK("200","操作成功"),
	/**
	 * 操作失败码
	 */
	FAIL("400","操作失败");
	
	private String code;
	
	private String msg;
	
	private ResultCodeEmun(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
}
