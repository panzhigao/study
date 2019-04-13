package com.pan.mapper;

import java.util.List;
import com.pan.dto.UserDTO;
import com.pan.entity.User;
import com.pan.query.QueryUser;

/**
 * 
 * @author Administrator
 *
 */
public interface UserMapper extends BaseMapper<User>{
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
	 * 分页查询用户信息
	 * @param queryUserVO
	 * @return
	 */
	List<UserDTO> findByParams(QueryUser queryUserVO);
	/**
	 * 查找使用角色的用户数据
	 * @param roleId
	 * @return
	 */
	List<User> findUserByRoleId(Long roleId);
}
