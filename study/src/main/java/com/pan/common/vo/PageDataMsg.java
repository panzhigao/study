package com.pan.common.vo;

import com.pan.common.enums.ResultCodeEnum;

import lombok.Data;

/**
 * 分页数据
 * @author Administrator
 *
 */
@Data
public class PageDataMsg {
	/**
	 * 总记录数
	 */
	private long total;
	/**
	 * 数据
	 */
	private Object data;
	
	private String code;
	
	private String msg;
	
	public PageDataMsg(){
		
	}

	public PageDataMsg(long total, Object data) {
		super();
		this.total = total;
		this.data = data;
		this.code=ResultCodeEnum.OK.getCode();
		this.msg=ResultCodeEnum.OK.getMsg();
	}
	
}
