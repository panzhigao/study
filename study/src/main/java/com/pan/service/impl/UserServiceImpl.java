package com.pan.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.pan.entity.*;
import com.pan.service.LoginHistoryService;
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
import com.pan.common.enums.AdminFlagEnum;
import com.pan.common.enums.UserStatusEnum;
import com.pan.common.exception.BusinessException;
import com.pan.entity.ScoreHistory.ScoreType;
import com.pan.mapper.UserExtensionMapper;
import com.pan.mapper.UserMapper;
import com.pan.query.QueryUser;
import com.pan.service.ScoreHistoryService;
import com.pan.service.UserExtensionService;
import com.pan.service.UserService;
import com.pan.util.DateUtils;
import com.pan.util.ImageUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;
import com.pan.util.PasswordUtils;
import com.pan.util.RegexUtils;
import com.pan.util.TokenUtils;
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
	
	/**
	 * 默认角色id,新注册用户默认角色
	 */
	@Value("${system.defaultRoleId}")
	private String defaultRoleId;
	
	/**
	 * 管理员角色
	 */
	@Value("${system.adminRoleId}")
	private String adminRoleId;
	
	/**
	 * 图片访问路径
	 */
	@Value("${picture.url}")
	private String imgUrl;
	
	/**
	 * 图片保存路径
	 */
	@Value("${picture.saveDir}")
	private String pictureSaveDir;
	
	/**
	 * 新注册用户默认头像
	 */
	@Value("${system.defaultPortrait}")
	private String defaultPortrait;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserExtensionMapper userExtensionMapper;
	
	@Autowired
	private UserExtensionService userExtensionService;
	
	@Autowired
	private ScoreHistoryService scoreHistoryService;

	@Autowired
	private LoginHistoryService loginHistoryService;

	/**
	 * 用户注册,默认新增用户拥有普通用户权限
	 * 1.校验用户注册字段，校验用户名是否已经注册
	 * 2.密码加密，保存用户信息，用户拓展信息，新增注册积分历史
	 * 3.为用户添加用户角色信息
	 * 4.讲用户登录信息写入subject，用户自动登录
	 */
	@Override
	public User registerUser(User user){
		ValidationUtils.validateEntityWithGroups(user,new Class[]{RegisterGroup.class});
		String username=user.getUsername();
		User userInDb = findByUsername(username);
		if(userInDb!=null){
			logger.info("用户名已注册{}",userInDb);
			throw new BusinessException("用户名已注册");
		}
		String dateStr = DateUtils.getNowDateStr(DateUtils.FORMAT_DATE2);
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
			//用户状态正常
			user.setStatus(UserStatusEnum.STATUS_NORMAL.getCode());
			//默认头像
			user.setUserPortrait(defaultPortrait);
			userMapper.saveUser(user);
			//新增用户拓展信息
			UserExtension userExtensionTemp=new UserExtension();
			userExtensionTemp.setUserId(userId);
			Date now=new Date();
			userExtensionTemp.setCreateTime(now);
			userExtensionTemp.setUpdateTime(now);
			userExtensionTemp.setNickname(user.getNickname());
			userExtensionTemp.setUserPortrait(defaultPortrait);
			userExtensionMapper.saveUserExtension(userExtensionTemp);
			//新增积分信息
			ScoreHistory addScoreHistory = scoreHistoryService.addScoreHistory(userId, ScoreHistory.ScoreType.REGISTER);
			//用户拓展表增加积分
			UserExtension userExtension=new UserExtension();
			userExtension.setUserId(addScoreHistory.getUserId());
			userExtension.setUpdateTime(new Date());
			userExtension.setScore(addScoreHistory.getScore());
			userExtensionService.increaseCounts(userExtension);
			//为用户添加用户角色信息
			UserRole userRole=new UserRole(userId, defaultRoleId);
			userRole.setCreateTime(new Date());
			this.addUserRole(userRole);
			//用户验证,用明文密码验证
			user.setPassword(password);
			TokenUtils.userAutoLogin(user);
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

	/**
	 * 校验用户登陆
	 * @param user
	 * @return
	 */
	@Override
	public User checkLogin(User user) {
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
		if(UserStatusEnum.STATUS_BLOCKED.getCode().equals(userInDb.getStatus())){
			throw new BusinessException("用户账号已锁定");
		}
		String passwordInDb=userInDb.getPassword();
		try {
			boolean validPassword = PasswordUtils.validPassword(user.getPassword(), passwordInDb);
			if(!validPassword){
				throw new BusinessException("用户名或密码错误");
			}
			Date now=new Date();
			//修改用户最近登录时间
			updateUserLastLoginTime(userInDb.getUserId());
			//记录用户登录历史
			LoginHistory loginHistory=new LoginHistory();
			loginHistory.setUserId(userInDb.getUserId());
			loginHistory.setIpStr(TokenUtils.getIp());
			loginHistory.setCreateTime(now);
			loginHistoryService.addLoginHistory(loginHistory);
			//积分历史表新增登录积分
			ScoreHistory addScoreHistory = scoreHistoryService.addScoreHistory(userInDb.getUserId(), ScoreHistory.ScoreType.LOGIN);
			//如果不是今日首次登录，连续登录天数加1
			if(addScoreHistory!=null){
				UserExtension userExtension=new UserExtension();
				userExtension.setUserId(addScoreHistory.getUserId());
				userExtension.setUpdateTime(now);
				userExtension.setScore(addScoreHistory.getScore());
				userExtension.setContinuousLoginDays(1);
				userExtensionService.increaseCounts(userExtension);
			}
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
	
	/**
	 * 更新用户信息，同时更新用户缓存信息
	 */
	@Override
	public void updateUserInfo(User user, UserExtension userExtension) {
		ValidationUtils.validateEntityWithGroups(user,new Class[]{UserEditGroup.class});
		String userId=user.getUserId();
		user.setUpdateTime(new Date());
		String newPortrait=null;
		if(StringUtils.isNotBlank(user.getUserPortrait())){
			String temp=user.getUserPortrait();
			temp=temp.replace("data:image/jpeg;base64,", "");
			String generateImage = ImageUtils.generateImage(temp, pictureSaveDir);
			if(generateImage!=null){
				newPortrait=imgUrl+generateImage;
				user.setUserPortrait(newPortrait);
			}else{
				user.setUserPortrait(null);
			}
		}
		userMapper.updateUserByUserId(user);
		User userInDb = userMapper.findByUserId(userId);
		//重置用户登陆信息
		TokenUtils.setAttribute(MyConstant.USER,userInDb);
		String userBrief=userExtension.getUserBrief();
		userExtension.setUserId(userId);
		//更新用户拓展信息
		if(StringUtils.isBlank(userBrief)){
			userExtension.setUserBrief(null);
		}
		Date now=new Date();
		userExtension.setUserPortrait(newPortrait);
		userExtension.setUpdateTime(now);
		userExtensionMapper.updateUserExtensionByUserId(userExtension);	
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
		User loginUser = TokenUtils.getLoginUser();
		userMapper.updateUserByUserId(user);
		loginUser.setTelephone(user.getTelephone());
		TokenUtils.setAttribute(MyConstant.USER, loginUser);
		JedisUtils.existsKey(redisCode);
	}

	@Override
	public void updateUserByUserId(User user) {
		user.setUpdateTime(new Date());
		userMapper.updateUserByUserId(user);
	}

	@Override
	public Map<String, Object> findPageData(QueryUser queryUser) {
		Map<String,Object> pageData=new HashMap<String, Object>(2);
		List<User> list = new ArrayList<User>();
		try {
			logger.info("分页查询文章参数为:{}", JsonUtils.toJson(queryUser));
			int total=userMapper.getCountByParams(queryUser);
			//当查询记录大于0时，查询数据库记录，否则直接返回空集合
			if(total>0){				
				list = userMapper.findByParams(queryUser);
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
	
	/**
	 * 为用户分配角色
	 * @param userId
	 * @param roles 角色id数组
	 */
	@Override
	public void allocateRoleToUser(String userId, String[] roles) {
		User user=this.findByUserId(userId);
		if(user==null){
			logger.error("为用户分配角色,根据userId({})未查询到用户信息",userId);
			throw new BusinessException("该用户不存在");
		}
		//TODO 增加日志
		//删除该用户下的所有角色，再重新添加
		userMapper.deleteUserRoleByUserId(userId);
		//是否给角色分配管理员角色
		boolean hasAdmin=false;
		if(ArrayUtils.isNotEmpty(roles)){
			List<UserRole> list=new ArrayList<UserRole>();
			for (String roleId : roles) {
				if(StringUtils.equals(adminRoleId, roleId)){
					hasAdmin=true;
				}
				UserRole userRole=new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(userId);
				userRole.setCreateTime(new Date());
				list.add(userRole);
			}
			if(hasAdmin){				
				user.setAdminFlag(AdminFlagEnum.ADMIN_TRUE.getCode());
				user.setUpdateTime(new Date());
				user.setUpdateUser(TokenUtils.getLoingUserId());
				userMapper.updateUserByUserId(user);
			}
			userMapper.addUserRole(list);
			//清空该用户权限缓存
			TokenUtils.clearAuthz(userId);
		}
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
		if(StringUtils.equals(userId, loginUserId)){
			throw new BusinessException("不能修改自己的状态");
		}
		user.setUpdateUser(loginUserId);
		user.setUpdateTime(new Date());
		if(UserStatusEnum.STATUS_BLOCKED.getCode().equals(status)){
			message="禁用账号成功";
			userMapper.updateUserByUserId(user);
			//清空用户授权信息
			TokenUtils.clearAuth(userId);
		}else if(UserStatusEnum.STATUS_NORMAL.getCode().equals(status)){
			message="启用账号成功";
			userMapper.updateUserByUserId(user);
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

	@Override
	public List<User> findUserByRoleId(String roleId) {
		return userMapper.findUserByRoleId(roleId);
	}

	@Override
	public ScoreHistory checkIn(String userId) {
		ScoreHistory addScoreHistory = scoreHistoryService.addScoreHistory(userId, ScoreType.CHECK_IN);
		//用户拓展表增加积分,增加用户签到天数
		UserExtension userExtension=new UserExtension();
		userExtension.setUserId(addScoreHistory.getUserId());
		userExtension.setUpdateTime(new Date());
		userExtension.setScore(addScoreHistory.getScore());
		userExtension.setContinuousCheckInDays(1);
		userExtensionService.increaseCounts(userExtension);
		return addScoreHistory;
	}
}
