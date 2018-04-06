package com.pan.entity;

/**
 * 
 * @author panzhigao-wb
 *
 */
public class UserRole extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2179680256579852350L;
	/**
	 * {@link User.userId}
	 * 用户id
	 */
	private String userId;
	/**
	 * {@link Role.roleId}
	 * 角色id
	 */
	private String roleId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public UserRole() {
		super();
	}
	public UserRole(String userId, String roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "UserRole [userId=" + userId + ", roleId=" + roleId
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getUpdateTime()=" + getUpdateTime()
				+ "]";
	}
	
}
