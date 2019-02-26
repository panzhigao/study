package com.pan.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户角色
 * @author panzhigao-wb
 *
 */
@Data
public class Role extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -504813221851725957L;
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 角色名称
	 */
	@NotEmpty(message="角色名称不能为空")
	private String roleName;
	/**
	 * 是否选中
	 */
	private String marker;
	/**
	 * 是否是超级管理员
	 */
	private String superAdminFlag;
}
