package com.pan.mapper;

import java.util.List;
import com.pan.entity.User;
import com.pan.entity.UserRole;
import com.pan.vo.QueryUserVO;

/**
 * 
 * @author Administrator
 *
 */
public interface UserMapper {
	/**
	 * 根据userId查找用户信息，唯一一条用户数据
	 * @param userId
	 * @return
	 */
	public User findByUserId(String userId);
	/**
	 * 根据手机号查找用户信息，唯一一条用户数据
	 * @param telephone
	 * @return
	 */
	public User findByTelephone(String telephone);
	/**
	 * 根据username查找用户信息，唯一一条用户数据
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	/**
	 * 保存用户信息
	 * @param user
	 */
	public void saveUser(User user);
	/**
	 * 更新用户id更新用户信息
	 * @param user
	 */
	public void updateUserByUserId(User user);
	/**
	 * 查询文章详细,支持分页
	 * @param params
	 * @return
	 */
	public int getCountByParams(QueryUserVO queryUserVO);
	/**
	 * 分页查询用户信息
	 * @return
	 */
	public List<User> findByParams(QueryUserVO queryUserVO);
	/**
	 * 新增用户角色
	 * @param list
	 */
	public void addUserRole(List<UserRole> list);
	/**
	 * 删除用户角色
	 * @param userId
	 */
	public void deleteUserRoleByUserId(String userId);
}
