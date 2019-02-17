package com.pan.query;

/**
 * @author 作者
 * @version 创建时间：2018年3月28日 下午5:17:24 类说明
 */
public class QueryRole extends QueryBase {
	/**
	 * 是否是超级管理员，0-否，1-是
	 */
	private String superAdminFlag;
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

	public String getSuperAdminFlag() {
		return superAdminFlag;
	}

	public void setSuperAdminFlag(String superAdminFlag) {
		this.superAdminFlag = superAdminFlag;
	}

	@Override
	public String toString() {
		return "QueryRoleVO [superAdminFlag=" + superAdminFlag + ", roleId=" + roleId + ", roleName=" + roleName + "]";
	}

}
