package com.pan.entity;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author panzhigao-wb
 *
 */
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
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getUpdateTime()=" + getUpdateTime()
				+ "]";
	}
}
