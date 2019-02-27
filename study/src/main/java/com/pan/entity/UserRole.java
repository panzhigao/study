package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author panzhigao-wb
 *
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
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

	public UserRole(String userId, String roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}
}
