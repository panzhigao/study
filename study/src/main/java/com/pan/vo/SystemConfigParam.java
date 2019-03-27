package com.pan.vo;

import lombok.Data;

/**
 * 系统配置参数
 * @author Administrator
 *
 */
@Data
public class SystemConfigParam {
	/**
	 * 参数名
	 */
	private String paramName;
	/**
	 * 参数值
	 */
	private String paramValue;
	/**
	 * 参数说明
	 */
	private String paramDesc;
}
