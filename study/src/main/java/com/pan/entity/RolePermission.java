package com.pan.entity;

/**
 * 
 * @author panzhigao-wb
 *
 */
public class RolePermission extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8947795048710408903L;
	/**
	 * 角色id
	 * {@link Role.roleId}
	 */
	private String roleId;
	/**
	 * 权限id
	 * {@link Permission.permissionId}
	 */
	private String permissionId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	@Override
	public String toString() {
		return "RolePermission [roleId=" + roleId + ", permissionId="
				+ permissionId + ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getUpdateTime()=" + getUpdateTime()
				+ "]";
	}
}
