package com.pan.entity;

import java.util.Date;

/**
 * 用户信息实体类
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
	 * 昵称
	 */
	private String nickname;
	/**
	 * 用户密码
	 */
	private String password;

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
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username
				+ ", nickname=" + nickname + ", password=" + password
				+ ", lastLoginTime=" + lastLoginTime + ", status=" + status
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + "]";
	}

}
