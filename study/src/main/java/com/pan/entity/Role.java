package com.pan.entity;

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
