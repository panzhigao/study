package com.pan.service;

import java.util.List;
import java.util.Map;
import com.pan.entity.ScoreHistory;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.query.QueryUser;

/**
 * 
 * @author Administrator
 *
 */
public interface UserService extends BaseService<User>{
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
	void updateUserLastLoginTime(Long userId);
	/**
	 * 修改用户信息
	 * @param userExtension
	 */
	void updateUserInfo(User user,UserExtension userExtension);
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
	void allocateRoleToUser(Long userId,Long[] roles);
	/**
	 * 修改用户状态
	 * @param userId
	 * @param status
	 * @return 返回状态内容
	 */
	String changeUserStatus(Long userId,Integer status);
	/**
	 * 根据角色id获取拥有该角色的用户
	 * @param roleId
	 * @return
	 */
	List<User> findUserByRoleId(Long roleId);
	/**
	 * 用户签到
	 * @param userId 用户id
	 * @return
	 */
	ScoreHistory checkIn(Long userId);
	/**
	 * 同步用户es数据
	 * @return
	 */
	int syncUserEsData();
	/**
	 * @param queryArticleVO
	 * @return
	 */
	List<User> queryFromEsByCondition(QueryUser queryUser);
}
