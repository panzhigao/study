package com.pan.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.common.annotation.LoginGroup;
import com.pan.common.annotation.RegisterGroup;
import com.pan.common.annotation.TelephoneBindGroup;
import com.pan.common.annotation.UserEditGroup;
import com.pan.common.exception.BusinessException;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.mapper.UserExtensionMapper;
import com.pan.mapper.UserMapper;
import com.pan.service.UserService;
import com.pan.util.CookieUtils;
import com.pan.util.JedisUtils;
import com.pan.util.PasswordUtils;
import com.pan.util.RegexUtils;
import com.pan.util.ValidationUtils;
import com.pan.util.VerifyCodeUtils;

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
	
	public User saveUser(User user){
		ValidationUtils.validateEntityWithGroups(user,new Class[]{RegisterGroup.class});
		String username=user.getUsername();
		User userInDb = findByUsername(username);
		if(userInDb!=null){
			logger.info("用户名已注册{}",userInDb);
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
			return user;
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

	public User checkLogin(HttpServletRequest httpRequest,User user,String vercode) {
		CookieUtils.validateVercode(httpRequest, vercode);
		ValidationUtils.validateEntityWithGroups(user, new Class[]{LoginGroup.class});
		String username=user.getUsername();
		User userInDb=null;
		//手机号登陆
		if(RegexUtils.checkTelephone(username)){
			userInDb = findByUserTelephone(username);
		}else{
			userInDb = findByUsername(username);
		}
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

	public User findByUserId(String userId) {
		logger.info("用户id:{}",userId);
		return userMapper.findByUserId(userId);
	}

	public void updateUserInfo(User user, UserExtension userExtension) {
		ValidationUtils.validateEntityWithGroups(user,new Class[]{UserEditGroup.class});
		String userId=user.getUserId();
		user.setUpdateTime(new Date());
		userMapper.updateUserByUserId(user);
		String userBrief=userExtension.getUserBrief();
		//当没用用户简介时新增，否则更新
		UserExtension userExtensionInDb = userExtensionMapper.findByUserId(userId);
		UserExtension userExtensionTemp=new UserExtension();
		userExtensionTemp.setUserId(userId);
		userExtensionTemp.setUserBrief(userBrief);
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

	public UserExtension findExtensionByUserId(String userId) {
		return userExtensionMapper.findByUserId(userId);
	}

	@Override
	public String sendValidationCode(User user) {
		ValidationUtils.validateEntityWithGroups(user,TelephoneBindGroup.class);
		//校验手机号是否被占用
		User userInDb = findByUserTelephone(user.getTelephone());
		if(userInDb!=null){
			logger.info("该手机号已被使用：{}",user.getTelephone());
			throw new BusinessException("该手机号已被使用，请更换手机号");
		}
		//生成验证码
		String generateVerifyCode = VerifyCodeUtils.generateVerifyCode(4);
		//验证码5分钟后过期
		JedisUtils.setStringExpire(user.getTelephone(),generateVerifyCode,300);
		return generateVerifyCode;
	}

	@Override
	public void bindTelephone(User user,String code) {
		ValidationUtils.validateEntityWithGroups(user,TelephoneBindGroup.class);
		//redis中存的验证码
		String redisCode = JedisUtils.getString(user.getTelephone());
		if(redisCode==null){
			throw new BusinessException("验证码有误，请重新发送");
		}
		if(!StringUtils.equals(redisCode,code)){
			throw new BusinessException("验证码有误");
		}
		user.setUpdateTime(new Date());
		userMapper.updateUserByUserId(user);
		JedisUtils.existsKey(redisCode);
	}
}
