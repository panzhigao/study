package com.pan.service;

import java.util.Map;

import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.vo.QueryUserVO;

/**
 * 
 * @author Administrator
 *
 */
public interface UserService {
	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 */
	public User saveUser(User user);
	/**
	 * 根据用户名查找唯一用户信息
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	/**
	 * 根据用户Id查找唯一用户信息
	 * @param userId
	 * @return
	 */
	public User findByUserId(String userId);
	/**
	 * 根据手机号查找唯一用户信息
	 * @param telephone
	 * @return
	 */
	public User findByUserTelephone(String telephone);
	/**
	 * 校验用户登陆
	 * @param httpRequest
	 * @param user
	 * @param vercode
	 * @return
	 */
	public User checkLogin(User user,String vercode);
	/**
	 * 更新用户登陆时间
	 * @param userId
	 */
	public void updateUserLastLoginTime(String userId);
	/**
	 * 修改用户信息
	 * @param userExtension
	 */
	public void updateUserInfo(User user,UserExtension userExtension);
	/**
	 * 根据用户id查找用户其他信息
	 * @param userId
	 * @return 
	 */
	public UserExtension findExtensionByUserId(String userId);
	/**
	 * 发送验证码
	 * @param user 用户信息
	 * @param operateType 操作类型
	 * @return
	 */
	public String sendValidationCode(User user,String operateType);
	/**
	 * 校验手机和验证码
	 * @param user
	 * @param code 验证码
	 */
	public void bindTelephone(User user,String code);
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public void updateUserByUserId(User user);
	/**
	 * 分页查询客户信息
	 * @param params
	 * @return
	 */
	public Map<String,Object> findPageData(QueryUserVO queryUserVO);
	/**
	 * 为用户分配角色
	 * @param userId
	 * @param roles 角色id数组
	 */
	public void allocateRoleToUser(String userId,String[] roles);
	/**
	 * 修改用户状态
	 * @param userId
	 * @param status
	 */
	public String changeUserStatus(String userId,String status);
}
