package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者
 * @version 创建时间：2018年3月28日 下午5:17:24 类说明
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryRole extends QueryBase {
	/**
	 * 是否是超级管理员，0-否，1-是
	 */
	private Integer superAdminFlag;
	/**
	 * 角色id
	 */
	private Long roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
}
