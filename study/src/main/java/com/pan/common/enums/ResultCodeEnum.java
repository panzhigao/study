package com.pan.common.enums;

/**
 * 错误码
 * @author Administrator
 *
 */
public enum ResultCodeEnum {
	/**
	 * 操作成功码
	 */
	OK("200","操作成功"),
	/**
	 * 操作失败码
	 */
	FAIL("400","操作失败"),
	/**
	 * 图片上传成功
	 */
	UPLOAD_SUCCESS("0","图片上传成功"),
	/**
	 * 
	 */
	SUCCESS("0","操作成功！"),
	/**
	 * 
	 */
	REDIRECT("300","重定向");
	private String code;
	
	private String msg;
	
	ResultCodeEnum(String code, String msg) {
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
