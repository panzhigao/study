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
	 * {@link User.id}
	 * 用户id
	 */
	private Long userId;
	/**
	 * {@link Role.id}
	 * 角色id
	 */
	private Long roleId;

	public UserRole(Long userId, Long roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}
}
