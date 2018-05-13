package com.pan.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pan.common.annotation.LoginGroup;
import com.pan.common.annotation.RegisterGroup;
import com.pan.common.annotation.TelephoneBindGroup;
import com.pan.common.annotation.UserEditGroup;
import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.entity.UserRole;
import com.pan.mapper.UserExtensionMapper;
import com.pan.mapper.UserMapper;
import com.pan.service.UserService;
import com.pan.util.CookieUtils;
import com.pan.util.ImageUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;
import com.pan.util.PasswordUtils;
import com.pan.util.RegexUtils;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;
import com.pan.util.VerifyCodeUtils;
import com.pan.vo.QueryUserVO;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static final String DATEFORMAT="yyyyMMdd";
	
	
	/**
	 * 图片访问路径
	 */
	@Value("${picture.url}")
	private String imgUrl;
	
	/**
	 * 图片保存路径
	 */
	@Value("${picture.dir}")
	private String pictureDir;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserExtensionMapper userExtensionMapper;
	
	
	@Override
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
			//为用户添加用户角色信息
			UserRole userRole=new UserRole(userId, "r1001308");
			userRole.setCreateTime(new Date());
			this.addUserRole(userRole);
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
	
	@Override
	public User findByUsername(String username) {
		return this.userMapper.findByUsername(username);
	}
	
	@Override
	public User checkLogin(User user,String vercode) {
		CookieUtils.validateVercode(vercode);
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
	
	@Override
	public void updateUserLastLoginTime(String userId) {
		User user=new User();
		user.setUserId(userId);
		user.setLastLoginTime(new Date());
		userMapper.updateUserByUserId(user);
	}
	
	@Override
	public User findByUserId(String userId) {
		logger.info("用户id:{}",userId);
		return userMapper.findByUserId(userId);
	}
	
	@Override
	public void updateUserInfo(User user, UserExtension userExtension) {
		ValidationUtils.validateEntityWithGroups(user,new Class[]{UserEditGroup.class});
		String userId=user.getUserId();
		user.setUpdateTime(new Date());
		if(StringUtils.isNotBlank(user.getUserPortrait())){
			String temp=user.getUserPortrait();
			temp=temp.replace("data:image/jpeg;base64,", "");
			String generateImage = ImageUtils.generateImage(temp, pictureDir);
			if(generateImage!=null){
				String newPortrait=imgUrl+generateImage;
				user.setUserPortrait(newPortrait);
			}else{
				user.setUserPortrait(null);
			}
		}
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
	
	@Override
	public User findByUserTelephone(String telephone) {
		logger.info("手机号：{}",telephone);
		return userMapper.findByTelephone(telephone);
	}
	
	@Override
	public UserExtension findExtensionByUserId(String userId) {
		return userExtensionMapper.findByUserId(userId);
	}

	@Override
	public String sendValidationCode(User user,String operateType) {
		ValidationUtils.validateEntityWithGroups(user,TelephoneBindGroup.class);
		//校验手机号是否被占用
		User userInDb = findByUserTelephone(user.getTelephone());
		if(MyConstant.OPERATETYPE_SET.equals(operateType)){
			if(userInDb!=null){
				logger.info("该手机号已被使用：{}",user.getTelephone());
				throw new BusinessException("该手机号已被使用，请更换手机号");
			}
		}else if(MyConstant.OPERATETYPE_FINDPASSWORD.equals(operateType)){
			if(userInDb==null){
				logger.info("该手机号未注册：{}",user.getTelephone());
				throw new BusinessException("该手机号未注册");
			}
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

	@Override
	public void updateUserByUserId(User user) {
		user.setUpdateTime(new Date());
		userMapper.updateUserByUserId(user);
	}

	@Override
	public Map<String, Object> findPageData(QueryUserVO queryUserVO) {
		Map<String,Object> pageData=new HashMap<String, Object>(2);
		List<User> list = new ArrayList<User>();
		try {
			logger.info("分页查询文章参数为:{}", JsonUtils.toJson(queryUserVO));
			int total=userMapper.getCountByParams(queryUserVO);
			//当查询记录大于0时，查询数据库记录，否则直接返回空集合
			if(total>0){				
				list = userMapper.findByParams(queryUserVO);
			}
			pageData.put("data", list);
			pageData.put("total", total);
			pageData.put("code", "200");
			pageData.put("msg", "");
		} catch (Exception e) {
			logger.error("分页查询文章异常", e);
		}
		return pageData;
	}

	@Override
	public void allocateRoleToUser(String userId, String[] roles) {
		User user=this.findByUserId(userId);
		if(user==null){
			throw new BusinessException("该用户不存在");
		}
		//TODO 增加日志
		//删除该用户下的所有角色，再重新添加
		userMapper.deleteUserRoleByUserId(userId);
		if(ArrayUtils.isNotEmpty(roles)){
			List<UserRole> list=new ArrayList<UserRole>();
			for (String role : roles) {
				UserRole userRole=new UserRole();
				userRole.setRoleId(role);
				userRole.setUserId(userId);
				userRole.setCreateTime(new Date());
				list.add(userRole);
			}
			userMapper.addUserRole(list);
		}else{
			roles=new String[0];
		}
		JedisUtils.setString("user_roles:"+userId, JsonUtils.toJson(roles));
	}

	@Override
	public String changeUserStatus(String userId, String status) {
		String message=null;
		User userInDb = userMapper.findByUserId(userId);
		if(userInDb==null){
			throw new BusinessException("用户不存在");
		}
		User user=new User(userId,status);
		String loginUserId = TokenUtils.getLoingUserId();
		user.setUpdateUser(loginUserId);
		user.setUpdateTime(new Date());
		if(User.STATUS_BLOCKED.equals(status)){
			message="禁用账号成功";
			userMapper.updateUserByUserId(user);
			User loginUser = CookieUtils.getLoginUser();
			if(loginUser!=null){
				loginUser.setStatus(status);
				CookieUtils.setLoginUser(loginUser);
			}
		}else if(User.STATUS_NORMAL.equals(status)){
			message="启用账号成功";
			userMapper.updateUserByUserId(user);
			User loginUser = CookieUtils.getLoginUser();
			if(loginUser!=null){
				loginUser.setStatus(status);
				CookieUtils.setLoginUser(loginUser);
			}
		}else{
			message="操作错误，请稍后重试";
		}	
		return message;
	}

	@Override
	public int findRoleUserCountByRoleId(String roleId) {
		return userMapper.findRoleUserCountByRoleId(roleId);
	}

	@Override
	public void addUserRole(UserRole userRole) {
		List<UserRole> list=new ArrayList<UserRole>();
		list.add(userRole);
		userMapper.addUserRole(list);
	}
}
