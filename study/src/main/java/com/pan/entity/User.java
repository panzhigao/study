package com.pan.entity;

import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
public class User extends BaseEntity{
	/**
	 * 禁用状态
	 */
	public static final String STATUS_BLOCKED="0";
	/**
	 * 正常状态
	 */
	public static final String STATUS_NORMAL="1";

	/**
	 * 
	 */
	private static final long serialVersionUID = -2820248584713373399L;
	
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最近登录时间
	 */
	private Date lastLoginTime;
	/**
	 * 用户状态
	 * 0禁用，1正常
	 */
	private String status;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username
				+ ", password=" + password + ", createTime=" + createTime
				+ ", lastLoginTime=" + lastLoginTime + ", status=" + status
				+ "]";
	}
}
