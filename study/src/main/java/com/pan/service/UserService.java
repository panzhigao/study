package com.pan.service;

import java.util.List;
import java.util.Map;

import com.pan.entity.ScoreHistory;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.entity.UserRole;
import com.pan.query.QueryUser;

/**
 * 
 * @author Administrator
 *
 */
public interface UserService {
	/**
	 * 注册用户信息
	 * @param user
	 * @return
	 */
	User registerUser(User user);
	/**
	 * 根据用户名查找唯一用户信息
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
	/**
	 * 根据用户Id查找唯一用户信息
	 * @param userId
	 * @return
	 */
	User findByUserId(String userId);
	/**
	 * 根据手机号查找唯一用户信息
	 * @param telephone
	 * @return
	 */
	User findByUserTelephone(String telephone);
	/**
	 * 校验用户登陆
	 * @param user
	 * @return
	 */
	User checkLogin(User user);
	/**
	 * 更新用户登陆时间
	 * @param userId
	 */
	void updateUserLastLoginTime(String userId);
	/**
	 * 修改用户信息
	 * @param userExtension
	 */
	void updateUserInfo(User user,UserExtension userExtension);
	/**
	 * 根据用户id查找用户其他信息
	 * @param userId
	 * @return 
	 */
	UserExtension findExtensionByUserId(String userId);
	/**
	 * 发送验证码
	 * @param user 用户信息
	 * @param operateType 操作类型
	 * @return
	 */
	String sendValidationCode(User user,String operateType);
	/**
	 * 校验手机和验证码
	 * @param user
	 * @param code 验证码
	 */
	void bindTelephone(User user,String code);
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	void updateUserByUserId(User user);
	/**
	 * 分页查询客户信息
	 * @param queryUserVO
	 * @return
	 */
	Map<String,Object> findPageData(QueryUser queryUserVO);
	/**
	 * 为用户分配角色
	 * @param userId
	 * @param roles 角色id数组
	 */
	void allocateRoleToUser(String userId,String[] roles);
	/**
	 * 修改用户状态
	 * @param userId
	 * @param status
	 */
	String changeUserStatus(String userId,String status);
	/**
	 * 查找使用角色的用户数
	 * @param roleId
	 * @return
	 */
	int findRoleUserCountByRoleId(String roleId);
	/**
	 * 为用户添加用户角色信息
	 * @param userRole
	 */
	void addUserRole(UserRole userRole);
	/**
	 * 根据角色id获取拥有该角色的用户
	 * @param roleId
	 * @return
	 */
	List<User> findUserByRoleId(String roleId);
	/**
	 * 用户签到
	 */
	ScoreHistory checkIn(String userId);
}
