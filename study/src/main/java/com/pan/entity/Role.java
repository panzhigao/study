package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 用户角色
 * @author panzhigao-wb
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
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
	@Size(max = 30,message = "角色名称不能超过30的字符")
	private String roleName;
	/**
	 * 是否选中
	 */
	private String marker;
	/**
	 * 是否是超级管理员 0-否，1-是
	 */
	private Integer superAdminFlag;
}
