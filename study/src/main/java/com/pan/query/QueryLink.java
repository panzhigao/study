package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class QueryLink extends QueryBase{
	/**
	 * 链接名
	 */
	private String linkName;
	/**
	 * 链接状态
	 */
	private Integer status;
}
