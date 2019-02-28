package com.pan.mapper;

import java.util.List;

import com.pan.dto.UserDTO;
import com.pan.entity.User;
import com.pan.entity.UserRole;
import com.pan.query.QueryUser;

/**
 * 
 * @author Administrator
 *
 */
public interface UserMapper extends BaseMapper<User>{
	/**
	 * 根据userId查找用户信息，唯一一条用户数据
	 * @param userId
	 * @return
	 */
	User findByUserId(String userId);
	/**
	 * 根据手机号查找用户信息，唯一一条用户数据
	 * @param telephone
	 * @return
	 */
	User findByTelephone(String telephone);
	/**
	 * 根据username查找用户信息，唯一一条用户数据
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
	/**
	 * 更新用户id更新用户信息
	 * @param user
	 */
	void updateUserByUserId(User user);
	/**
	 * 分页查询用户信息
	 * @param queryUserVO
	 * @return
	 */
	List<UserDTO> findByParams(QueryUser queryUserVO);
	/**
	 * 删除用户角色
	 * @param userId
	 */
	void deleteUserRoleByUserId(String userId);
	/**
	 * 查找使用角色的用户数据
	 * @param roleId
	 * @return
	 */
	List<User> findUserByRoleId(String roleId);
}
