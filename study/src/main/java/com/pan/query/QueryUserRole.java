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
public class QueryUserRole extends QueryBase{
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户id
	 */
	private String roleId;
	/**
	 * 创建人id
	 */
	private String createUser;
}
