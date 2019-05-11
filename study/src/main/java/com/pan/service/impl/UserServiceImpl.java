package com.pan.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import com.pan.common.enums.ScoreTypeEnum;
import com.pan.dto.UserDTO;
import com.pan.entity.*;
import com.pan.service.*;
import com.pan.shiro.RedisSessionDAO;
import com.pan.util.*;
import com.pan.vo.UserVO;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.pan.common.annotation.LoginGroup;
import com.pan.common.annotation.RegisterGroup;
import com.pan.common.annotation.TelephoneBindGroup;
import com.pan.common.annotation.UserEditGroup;
import com.pan.common.constant.EsConstant;
import com.pan.common.constant.MyConstant;
import com.pan.common.constant.PageConstant;
import com.pan.common.enums.AdminFlagEnum;
import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.common.enums.RedisChannelEnum;
import com.pan.common.enums.UserStatusEnum;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.PageDataMsg;
import com.pan.mapper.RoleMapper;
import com.pan.mapper.UserExtensionMapper;
import com.pan.mapper.UserMapper;
import com.pan.query.QueryUser;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl extends AbstractBaseService<User,UserMapper> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
        
    /**
     * 默认角色id,新注册用户默认角色
     */
    @Value("${system.defaultRoleId}")
    private Long defaultRoleId;

    /**
     * 管理员角色
     */
    @Value("${system.adminRoleId}")
    private Long adminRoleId;

    /**
     * 图片访问路径
     */
    @Value("${picture.url}")
    private String imgUrl;

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
    private RoleMapper roleMapper;
    
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserExtensionService userExtensionService;

    @Autowired
    private ScoreHistoryService scoreHistoryService;

    @Autowired
    private LoginHistoryService loginHistoryService;
    
    @Autowired
    private OperateLogService operateLogService;
    
    @Autowired
    private PictureService pictureService;
    
	@Autowired
	private EsClientService esClientService;
    
    /**
     * 用户注册,默认新增用户拥有普通用户权限
     * 1.校验用户注册字段，校验用户名是否已经注册
     * 2.密码加密，保存用户信息，用户拓展信息，新增注册积分历史
     * 3.为用户添加用户角色信息
     * 4.讲用户登录信息写入subject，用户自动登录
     */
    @Override
    public User registerUser(User user) {
        ValidationUtils.validateEntityWithGroups(user, new Class[]{RegisterGroup.class});
        String username = user.getUsername();
        User userInDb = findByUsername(username);
        if (userInDb != null) {
            logger.info("用户名已注册{}", userInDb);
            throw new BusinessException("用户名已注册");
        }
        String password = user.getPassword();
        //用户密码加密
        try {
        	Date now = new Date();
            String encryptedPwd = PasswordUtils.getEncryptedPwd(password);
            user.setPassword(encryptedPwd);
            Long userId=IdUtils.generateId();
            user.setId(userId);
            user.setCreateTime(new Date());
            //用户状态正常
            user.setStatus(UserStatusEnum.STATUS_NORMAL.getCode());
            //默认头像
            user.setUserPortrait(defaultPortrait);
            //最近登录时间
            user.setLastLoginTime(now);
            user.setUpdateTime(now);
            userMapper.insertSelective(user);
            //新增用户拓展信息
            UserExtension userExtensionTemp = new UserExtension();
            userExtensionTemp.setId(userId);
            userExtensionTemp.setCreateTime(now);
            userExtensionTemp.setUpdateTime(now);
            userExtensionTemp.setNickname(user.getNickname());
            userExtensionTemp.setUserPortrait(defaultPortrait);
            userExtensionMapper.insertSelective(userExtensionTemp);
            //新增积分信息
            ScoreHistory addScoreHistory = scoreHistoryService.addScoreHistory(userId, ScoreTypeEnum.REGISTER);
            //用户拓展表增加积分
            UserExtension userExtension = new UserExtension();
            userExtension.setId(addScoreHistory.getUserId());
            userExtension.setUpdateTime(new Date());
            userExtension.setScore(addScoreHistory.getScore());
            userExtensionService.increaseCounts(userExtension);
            //为用户添加用户角色信息
            UserRole userRole = new UserRole(userId, defaultRoleId);
            userRole.setCreateTime(new Date());
            userRole.setCreateUserId(userId);
            userRoleService.addUserRole(userRole);
            //用户验证,用明文密码验证
            user.setPassword(password);
            TokenUtils.userAutoLogin(user);
            //通知redis消费
            JedisUtils.rpush(MyConstant.USER_ES_REDIS_LIST, user.getId().toString());
    		Publisher.sendMessage(RedisChannelEnum.USER_ES_CREATE_OR_UPDATE.getName(), "");
            return user;
        } catch (NoSuchAlgorithmException e) {
            logger.error("加密用户密码错误", e);
            throw new BusinessException("注册用户信息失败");
        } catch (UnsupportedEncodingException e) {
            logger.error("加密用户密码失败，不支持的编码", e);
            throw new BusinessException("注册用户信息失败");
        } catch (Exception e) {
            logger.error("新增用户信息失败", e);
            throw new BusinessException("注册用户信息失败");
        }
    }

    @Override
    public User findByUsername(String username) {
        return this.userMapper.findByUsername(username);
    }

    /**
     * 校验用户登陆
     *
     * @param user
     * @return
     */
    @Override
    public User checkLogin(User user) {
        ValidationUtils.validateEntityWithGroups(user, new Class[]{LoginGroup.class});
        String username = user.getUsername();
        User userInDb = null;
        //手机号登陆
        if (RegexUtils.checkTelephone(username)) {
            userInDb = findByUserTelephone(username);
        } else {
            userInDb = findByUsername(username);
        }
        if (userInDb == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (UserStatusEnum.STATUS_BLOCKED.getCode().equals(userInDb.getStatus())) {
            throw new BusinessException("用户账号已锁定");
        }
        String passwordInDb = userInDb.getPassword();
        try {
            boolean validPassword = PasswordUtils.validPassword(user.getPassword(), passwordInDb);
            if (!validPassword) {
                throw new BusinessException("用户名或密码错误");
            }
            Date now = new Date();
            //修改用户最近登录时间
            updateUserLastLoginTime(userInDb.getId());
            //记录用户登录历史
            LoginHistory loginHistory = new LoginHistory();
            loginHistory.setUserId(userInDb.getId());
            loginHistory.setUsername(userInDb.getUsername());
            loginHistory.setIp(IPUtils.ip2Integer(TokenUtils.getIp()));
            loginHistory.setCreateTime(now);
            loginHistory.setUserAgent(String.valueOf(TokenUtils.getAttribute(MyConstant.USER_AGENT)));
            loginHistoryService.insertSelective(loginHistory);
            //积分历史表新增登录积分
            ScoreHistory addScoreHistory = scoreHistoryService.addScoreHistory(userInDb.getId(), ScoreTypeEnum.LOGIN);
            //如果不是今日首次登录，连续登录天数加1
            if (addScoreHistory != null) {
                UserExtension userExtension = new UserExtension();
                userExtension.setId(addScoreHistory.getUserId());
                userExtension.setUpdateTime(now);
                userExtension.setScore(addScoreHistory.getScore());
                userExtension.setContinuousLoginDays(1);
                userExtension.setTotalLoginDays(1);
                userExtensionService.increaseCounts(userExtension);
            }
            userInDb.setPassword(null);
            return userInDb;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("验证用户和数据库密码出错", e);
            throw new BusinessException("用户名或密码错误");
        }
    }

    @Override
    public void updateUserLastLoginTime(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setLastLoginTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }


    /**
     * 更新当前登录人用户信息,只更新性别，昵称，用户头像，地址，同时更新用户缓存信息
     * 更新用户表，用户拓展表,发送消息更新es数据
     */
    @Override
    public void updateUserInfo(User user, UserExtension userExtension) {
    	String pictureSaveDir=SystemConfigUtils.getParamValue("image_upload_dir");
        ValidationUtils.validateEntityWithGroups(user, new Class[]{UserEditGroup.class});
        User updateUser=new User();
        BeanUtils.copyPropertiesInclude(user,updateUser,new String[]{"sex","nickname","userPortrait","address","userBrief"});
        Long userId = TokenUtils.getLoginUserId();
        updateUser.setId(userId);
        updateUser.setUpdateTime(new Date());
        //新图片访问url路径
        String newPortraitUrl = null;
        if (StringUtils.isNotBlank(updateUser.getUserPortrait())) {
            String temp = updateUser.getUserPortrait();
            temp = temp.replace("data:image/jpeg;base64,", "");
            String generateImage = ImageUtils.generateImage(temp, pictureSaveDir);
            if (generateImage != null) {
                newPortraitUrl = imgUrl + generateImage;
                updateUser.setUserPortrait(newPortraitUrl);
                //保存图片路径
                pictureService.insertPicture(newPortraitUrl, pictureSaveDir+generateImage);
            } else {
                updateUser.setUserPortrait(null);
            }
        }
        userMapper.updateByPrimaryKeySelective(updateUser);
        User userInDb = userMapper.selectByPrimaryKey(userId);
        //重置用户登陆信息
        TokenUtils.setAttribute(MyConstant.USER, userInDb);
        String userBrief = userExtension.getUserBrief();
        userExtension.setId(userId);
        //更新用户拓展信息
        if (StringUtils.isBlank(userBrief)) {
            userExtension.setUserBrief(null);
        }
        Date now = new Date();
        userExtension.setUserPortrait(newPortraitUrl);
        userExtension.setUpdateTime(now);
        userExtensionMapper.updateByPrimaryKeySelective(userExtension);
		//通知redis消费
        JedisUtils.rpush(MyConstant.USER_ES_REDIS_LIST, userId+"");
		Publisher.sendMessage(RedisChannelEnum.USER_ES_CREATE_OR_UPDATE.getName(), "");
    }

    @Override
    public User findByUserTelephone(String telephone) {
        logger.info("手机号：{}", telephone);
        return userMapper.findByTelephone(telephone);
    }


    @Override
    public String sendValidationCode(User user, String operateType) {
        ValidationUtils.validateEntityWithGroups(user, TelephoneBindGroup.class);
        //校验手机号是否被占用
        User userInDb = findByUserTelephone(user.getTelephone());
        if (MyConstant.OPERATE_TYPE_SET.equals(operateType)) {
            if (userInDb != null) {
                logger.info("该手机号已被使用：{}", user.getTelephone());
                throw new BusinessException("该手机号已被使用，请更换手机号");
            }
        } else if (MyConstant.OPERATE_TYPE_FIND_PASSWORD.equals(operateType)) {
            if (userInDb == null) {
                logger.info("该手机号未注册：{}", user.getTelephone());
                throw new BusinessException("该手机号未注册");
            }
        }
        //生成验证码
        String generateVerifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //验证码5分钟后过期
        JedisUtils.setStringExpire(user.getTelephone(), generateVerifyCode, 300);
        return generateVerifyCode;
    }

    @Override
    public void bindTelephone(User user, String code) {
        ValidationUtils.validateEntityWithGroups(user, TelephoneBindGroup.class);
        //redis中存的验证码
        String redisCode = JedisUtils.getString(user.getTelephone());
        if (redisCode == null) {
            throw new BusinessException("验证码有误，请重新发送");
        }
        if (!StringUtils.equals(redisCode, code)) {
            throw new BusinessException("验证码有误");
        }
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        User loginUser = TokenUtils.getLoginUser();
        loginUser.setTelephone(user.getTelephone());
        TokenUtils.setAttribute(MyConstant.USER, loginUser);
        JedisUtils.existsKey(redisCode);
    }

    @Override
    public void updateUserByUserId(User user) {
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Map<String, Object> findPageData(QueryUser queryUser) {
        Map<String, Object> pageData = new HashMap<>(4);
        List<UserDTO> list = new ArrayList<>();
        try {
            logger.info("分页查询用户参数为:{}", JsonUtils.toJson(queryUser));
            int total = userMapper.countByParams(queryUser);
            //当查询记录大于0时，查询数据库记录，否则直接返回空集合
            if (total > 0) {
                RedisSessionDAO redisSessionDAO = SpringContextUtils.getBean(RedisSessionDAO.class);
                Collection<Session> activeSessions = redisSessionDAO.getActiveSessions();
                list = userMapper.findDTOPageable(queryUser);
                list.forEach(s->{
                    boolean online = TokenUtils.isOnline(s.getId(),activeSessions);
                    s.setIsOnline(online);
                });
            }
            pageData.put("data", list);
            pageData.put("total", total);
            pageData.put("code", "200");
            pageData.put("msg", "");
        } catch (Exception e) {
            logger.error("分页查询用户异常", e);
        }
        return pageData;
    }

    /**
     * 为用户分配角色,每个用户至少有一个角色
     * 如果为用户分配管理员角色，将该用户设为管理员
     * @param userId 被分配角色的用户id
     * @param roleIds  角色id数组
     */
    @Override
    public void allocateRoleToUser(Long userId, Long[] roleIds) {
        User user = selectByPrimaryKey(userId);
        if (user == null) {
            logger.error("为用户分配角色,根据userId({})未查询到用户信息", userId);
            throw new BusinessException("该用户不存在");
        }
        if(ArrayUtils.isEmpty(roleIds)){
        	throw new BusinessException("至少选择一个角色");
        }
        List<Role> roles = roleMapper.findByRoleIds(roleIds);
        Set<String> roleSet = roles.stream().map(r->r.getRoleName()+"("+r.getId()+")").collect(Collectors.toSet());
        //查询用户数据库里的角色信息
        List<Long> roleIdsByUserId = roleMapper.getRoleIdsByUserId(userId);
        Long[] userRoleIds=new Long[roleIdsByUserId.size()];
        Long[] array = roleIdsByUserId.toArray(userRoleIds);
        List<Role> rolesInDb = roleMapper.findByRoleIds(array);
        Set<String> roleSetFromDb = rolesInDb.stream().map(r->r.getRoleName()+"("+r.getId()+")").collect(Collectors.toSet());
        if(Objects.deepEquals(roleSet, roleSetFromDb)){
        	throw new BusinessException("分配的角色未修改，请重新选择");
        }
        //删除该用户下的所有角色，再重新添加
        int deleteUserRoleByUserId = userRoleService.deleteUserRoleByUserId(userId);
        logger.info("删除用户角色共：{} 条",deleteUserRoleByUserId);
        //是否给角色分配管理员角色
        boolean hasAdmin = false;
        if (ArrayUtils.isNotEmpty(roleIds)) {
            List<UserRole> list = new ArrayList<UserRole>();
            for (Long roleId : roleIds) {
                if (adminRoleId.equals(roleId)) {
                    hasAdmin = true;
                }
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRole.setCreateTime(new Date());
                userRole.setCreateUserId(TokenUtils.getLoginUserId());
                list.add(userRole);
            }
            //如果给用户分配管理员角色，更新改用户的管理员字段
            if (hasAdmin && AdminFlagEnum.ADMIN_FALSE.getCode().equals(user.getAdminFlag())) {
                user.setAdminFlag(AdminFlagEnum.ADMIN_TRUE.getCode());
                user.setUpdateTime(new Date());
                user.setUpdateUserId(TokenUtils.getLoginUserId());
                userMapper.updateByPrimaryKeySelective(user);
            //取消用户admin权限    
            }else if(!hasAdmin && AdminFlagEnum.ADMIN_TRUE.getCode().equals(user.getAdminFlag())){
            	user.setAdminFlag(AdminFlagEnum.ADMIN_FALSE.getCode());
                user.setUpdateTime(new Date());
                user.setUpdateUserId(TokenUtils.getLoginUserId());
                userMapper.updateByPrimaryKeySelective(user);
            }
            userRoleService.batchAddUserRole(list);
            //清空该用户权限缓存
            TokenUtils.clearAuthz(userId);
        }
        //记录日志
        StringBuilder builder=new StringBuilder();
        builder.append("为用户(username=").append(user.getUsername()).append(")分配角色，角色从:");
        roleSetFromDb.forEach(r->builder.append(r));
        builder.append("===>>");
        roleSet.forEach(r->builder.append(r));
        operateLogService.addOperateLog(builder.toString(), OperateLogTypeEnum.ROLE_ALLOCATE);
    }
    
	/**
	 * 修改用户状态
	 * 0-禁用，1-开启
	 * @param userId
	 * @param status
	 */
    @Override
    public String changeUserStatus(Long userId, Integer status) {
        String message;
        User userInDb = userMapper.selectByPrimaryKey(userId);
        if (userInDb == null) {
            throw new BusinessException("用户不存在");
        }
        User user = new User();
        user.setStatus(status);
        Long loginUserId = TokenUtils.getLoginUserId();
        if (loginUserId.equals(userId)) {
            throw new BusinessException("不能修改自己的状态");
        }
        user.setUpdateUserId(loginUserId);
        user.setUpdateTime(new Date());
        if (UserStatusEnum.STATUS_BLOCKED.getCode().equals(status)) {
            message = "禁用账号成功";
            userMapper.updateByPrimaryKeySelective(user);
            String content="禁用"+userInDb.getNickname()+"("+userInDb.getUsername()+")";
            operateLogService.addOperateLog(content, OperateLogTypeEnum.USER_DISABLE);
            //清空用户授权信息
            TokenUtils.clearAuth(userId);
        } else if (UserStatusEnum.STATUS_NORMAL.getCode().equals(status)) {
            message = "启用账号成功";
            userMapper.updateByPrimaryKeySelective(user);
            String content="开启"+userInDb.getNickname()+"("+userInDb.getUsername()+")";
            operateLogService.addOperateLog(content, OperateLogTypeEnum.USER_ENABLE);
        } else {
            message = "操作错误，请稍后重试";
        }
        return message;
    }


    @Override
    public List<User> findUserByRoleId(Long roleId) {
        return userMapper.findUserByRoleId(roleId);
    }
    
	/**
	 * 用户签到
	 * 新增一条用户签到积分记录，用户连续签到天数加1
	 */
    @Override
    public ScoreHistory checkIn(Long userId) {
        ScoreHistory addScoreHistory = scoreHistoryService.addScoreHistory(userId, ScoreTypeEnum.CHECK_IN);
        //用户拓展表增加积分,增加用户签到天数
        UserExtension userExtension = new UserExtension();
        userExtension.setId(addScoreHistory.getUserId());
        userExtension.setUpdateTime(new Date());
        userExtension.setScore(addScoreHistory.getScore());
        userExtension.setContinuousCheckInDays(1);
        userExtension.setTotalCheckInDays(1);
        userExtensionService.increaseCounts(userExtension);
        return addScoreHistory;
    }

    @Override
    protected UserMapper getBaseMapper() {
        return userMapper;
    }
    
	private DocWriteRequest<?> buildRequest(User user){
		User userEs = esClientService.getById(EsConstant.ES_INDEX_NAME, EsConstant.ES_TYPE_NAME, user.getId()+"", User.class);
		if(userEs!=null){
			Map<String, Object> objectToMap = JsonUtils.objectToMap(user);
			Map<String,Object> joinMap=new HashMap<>();
			joinMap.put("name", "user");
			objectToMap.put("join_field", joinMap);
			return esClientService.buildUpdateRequest(EsConstant.ES_INDEX_NAME, EsConstant.ES_TYPE_NAME, user.getId()+"", objectToMap);
		}else{
			Map<String, Object> objectToMap = JsonUtils.objectToMap(user);
			Map<String,Object> joinMap=new HashMap<>();
			joinMap.put("name", "user");
			objectToMap.put("join_field", joinMap);
			return esClientService.buildIndexRequest(EsConstant.ES_INDEX_NAME, EsConstant.ES_TYPE_NAME, objectToMap);
		}
	}
    
	/**
	 * 同步用户es数据
	 */
	@Override
	public int syncUserEsData() {
		logger.info("同步用户es数据开始....");
		long start=System.currentTimeMillis();
		QueryUser queryUser=new QueryUser();
		int userTotal = countByParams(queryUser);
		int circleNum=userTotal/PageConstant.MAX_PAGE_SIZE;
		queryUser.setPageSize(PageConstant.MAX_PAGE_SIZE);
		BulkRequest bulkRequest=new BulkRequest();
		for(int i=0;i<=circleNum;i++){
			queryUser.setPageNo(i+1);
			List<User> findPageable = findPageable(queryUser);
			for(User u:findPageable){
				DocWriteRequest<?> request = buildRequest(u);
				bulkRequest.add(request);
			}	
			esClientService.bulk(bulkRequest);
		}
		long end=System.currentTimeMillis();
		String message=String.format("同步用户es数据结束，耗时%s",(end-start));
		operateLogService.addOperateLog(message, OperateLogTypeEnum.USER_ES_SYNC);
		logger.info(message);
		return userTotal;
	}

	@Override
	public List<UserVO> queryFromEsByCondition(QueryUser queryUser) {
		List<UserVO> list=new ArrayList<>();
		try {
			list=esClientService.queryByParamsWithHightLight(EsConstant.ES_INDEX_NAME, EsConstant.ES_TYPE_NAME, queryUser, true, UserVO.class);
		} catch (Exception e) {
			logger.error("查询用户es信息出错,查询条件{}",queryUser);
		}
		return list;
	}

	@Override
	public boolean updateUserEs(Long userId) {
		User user = selectByPrimaryKey(userId);
		if(user==null){
			logger.info("用户数据不存在，id={}",userId);
			return false;
		}
		user.setPassword(null);
		Object es = esClientService.getById(EsConstant.ES_INDEX_NAME, EsConstant.ES_TYPE_NAME, userId+"", Article.class);
		if(es!=null){
			return esClientService.updateRecord(EsConstant.ES_INDEX_NAME, EsConstant.ES_TYPE_NAME, userId+"", JsonUtils.objectToMap(user));
		}else{
			return createUserEs(userId);
		}
	}

	@Override
	public boolean createUserEs(Long userId) {
		User user = selectByPrimaryKey(userId);
		if(user==null){
			logger.info("用户数据不存在，id={}",userId);
			return false;
		}
		try {
			esClientService.createIndex(EsConstant.ES_INDEX_NAME, EsConstant.ES_TYPE_NAME, user);
			return true;
		} catch (Exception e) {
			logger.error("创建用户索引失败，id={}",userId,e);
		}
		return false;
	}

	@Override
	public PageDataMsg queryUserFromEs(QueryUser queryUser) {
		if(queryUser==null||StringUtils.isBlank(queryUser.getNickname())){
			return new PageDataMsg();
		}
		long total = esClientService.queryCountByParams(EsConstant.ES_INDEX_NAME,EsConstant.ES_TYPE_NAME,queryUser);
		if(total==0){
			return new PageDataMsg();
		}
		List<UserVO> list = queryFromEsByCondition(queryUser);
		return new PageDataMsg(total,list);
	}
}
