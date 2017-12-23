package com.pan.mapper;

import com.pan.entity.User;

/**
 * 
 * @author Administrator
 *
 */
public interface UserMapper {
	public User findByUserId(String userId);
}
