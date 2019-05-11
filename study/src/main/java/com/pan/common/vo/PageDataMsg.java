package com.pan.common.vo;

import lombok.Data;

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
	
	public PageDataMsg(){
		
	}

	public PageDataMsg(long total, Object data) {
		super();
		this.total = total;
		this.data = data;
	}
	
}
