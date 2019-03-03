package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者
 * @version 创建时间：2018年3月28日 下午5:17:24
 * 类说明
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryUser extends QueryBase{
	/**
	 * 用户性别
	 */
	private String sex;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 手机号
	 */
	private String telephone;
}
