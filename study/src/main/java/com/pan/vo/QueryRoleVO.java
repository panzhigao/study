package com.pan.vo;


/**
 * @author 作者
 * @version 创建时间：2018年3月28日 下午5:17:24
 * 类说明
 */
public class QueryRoleVO extends BaseVO{
	private String superAdminFlag;
	private String roleId;
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
		return "QueryRoleVO [superAdminFlag=" + superAdminFlag + ", roleId="
				+ roleId + ", roleName=" + roleName + "]";
	}
	
}