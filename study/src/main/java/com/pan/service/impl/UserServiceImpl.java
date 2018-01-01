package com.pan.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.common.exception.BusinessException;
import com.pan.dto.UserInfoDTO;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.mapper.UserExtensionMapper;
import com.pan.mapper.UserMapper;
import com.pan.service.UserService;
import com.pan.util.PasswordUtils;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static final String DATEFORMAT="yyyyMMdd";
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserExtensionMapper userExtensionMapper;
	
	public void saveUser(User user){
		String username=user.getUsername();
		User checkUsername = findByUsername(username);
		if(checkUsername!=null){
			logger.info("用户名已注册{}",checkUsername);
			throw new BusinessException("用户名已注册");
		}
		SimpleDateFormat sdf=new SimpleDateFormat(DATEFORMAT);
		String dateStr=sdf.format(new Date());
		String password=user.getPassword();
		//用户密码加密
		try {
			String encryptedPwd=PasswordUtils.getEncryptedPwd(password);
			user.setPassword(encryptedPwd);
			//生成userId
			String uuid=UUID.randomUUID().toString();
			uuid=uuid.substring(uuid.lastIndexOf("-")+1);
			String userId=dateStr+uuid;
			user.setUserId(userId);
			user.setCreateTime(new Date());
			user.setStatus(User.STATUS_NORMAL);
			userMapper.saveUser(user);
		} catch (NoSuchAlgorithmException e) {
			logger.error("加密用户密码错误",e);
			throw new BusinessException("注册用户信息失败");
		} catch (UnsupportedEncodingException e) {
			logger.error("加密用户密码失败，不支持的编码",e);
			throw new BusinessException("注册用户信息失败");
		}catch (Exception e) {
			logger.error("新增用户信息失败",e);
			throw new BusinessException("注册用户信息失败");
		}
	}

	public User findByUsername(String username) {
		return this.userMapper.findByUsername(username);
	}

	public User checkLogin(User user) {
		String username=user.getUsername();
		User userInDb = findByUsername(username);
		if(userInDb==null){
			throw new BusinessException("用户名或密码错误");
		}
		if(User.STATUS_BLOCKED.equals(userInDb.getStatus())){
			throw new BusinessException("用户账号已锁定");
		}
		String passwordInDb=userInDb.getPassword();
		try {
			boolean validPassword = PasswordUtils.validPassword(user.getPassword(), passwordInDb);
			if(!validPassword){
				throw new BusinessException("用户名或密码错误");
			}
			updateUserLastLoginTime(userInDb.getUserId());
			return userInDb;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("验证用户和数据库密码出错",e);
			throw new BusinessException("用户名或密码错误");
		}
	}

	public void updateUserLastLoginTime(String userId) {
		User user=new User();
		user.setUserId(userId);
		user.setLastLoginTime(new Date());
		userMapper.updateUserByUserId(user);
	}

	public User findByUserid(String userId) {
		logger.info("用户id:{}",userId);
		return userMapper.findByUserId(userId);
	}

	public void updateUserInfo(User user, UserExtension userExtension) {
		if(StringUtils.isBlank(user.getNickname())){
			throw new BusinessException("用户昵称不能为空");
		}
		if(StringUtils.isBlank(user.getTelephone())){
			throw new BusinessException("用户手机号不能为空");
		}
		User userInDb = findByUserTelephone(user.getTelephone());
		if(userInDb!=null&&!StringUtils.equals(user.getUserId(),userInDb.getUserId())){
			logger.info("该手机号已被使用：{}",user.getTelephone());
			throw new BusinessException("该手机号已被使用，请更换手机号");
		}
		String userId=user.getUserId();
		user.setUpdateTime(new Date());
		userMapper.updateUserByUserId(user);
		String userBrief=userExtension.getUserBrief();
		String userPortrait=userExtension.getUserPortrait();
		//当没用用户简介时新增，否则更新
		UserExtension userExtensionInDb = userExtensionMapper.findByUserId(userId);
		UserExtension userExtensionTemp=new UserExtension();
		userExtensionTemp.setUserId(userId);
		userExtensionTemp.setUserBrief(userBrief);
		userExtensionTemp.setUserPortrait(userPortrait);
		if(userExtensionInDb!=null){
			userExtensionTemp.setUpdateTime(new Date());
			userExtensionMapper.updateUserExtensionByUserId(userExtensionTemp);	
		}else{
			userExtensionTemp.setCreateTime(new Date());
			userExtensionMapper.saveUserExtension(userExtensionTemp);
		}	
		
	}

	public User findByUserTelephone(String telephone) {
		logger.info("手机号：{}",telephone);
		return userMapper.findByTelephone(telephone);
	}

	public UserInfoDTO getUserInfoByUserId(String userId) {
		User user = findByUserid(userId);
		UserExtension userExtension = userExtensionMapper.findByUserId(userId);
		UserInfoDTO userInfoDTO=new UserInfoDTO(user,userExtension);
		return userInfoDTO;
	}
}
