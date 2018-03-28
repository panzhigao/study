package com.pan.dto;

import com.pan.entity.User;

/**
 * @author 作者
 * @version 创建时间：2018年3月28日 下午7:01:46
 * 类说明
 */
public class UserDTO extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9038298327313388493L;
	private String roleNames;
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	
}
