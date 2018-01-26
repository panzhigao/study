package com.pan.service;

import javax.servlet.http.HttpServletRequest;
import com.pan.entity.User;
import com.pan.entity.UserExtension;

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
	public User checkLogin(HttpServletRequest httpRequest,User user,String vercode);
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
}
