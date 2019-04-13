package com.pan.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pan.common.constant.MyConstant;
import com.pan.common.enums.PermissionTypeEnum;
import com.pan.dto.Tree;
import com.pan.entity.Permission;
import com.pan.entity.User;
import com.pan.service.PermissionService;
import com.pan.service.RoleService;
import com.pan.service.UserService;
import com.pan.util.TokenUtils;

/**
 * @author panzhigao
 */
public class MyRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private RedisSessionDAO redisSessionDAO;
	
	/**
	 * 加载菜单
	 * 
	 * @param userId
	 */
	private Set<String> loadMenus(Long userId) {
		logger.debug(">>>>>>>>>>>>>>>>>>>从数据库加载权限>>>>>>>>>>>>>>>>>>>");
		List<Permission> permissionList = permissionService.findPermissionsByUserId(userId);
		List<Tree> nodes = new ArrayList<Tree>(50);
		//用户的权限点集合
		Set<String> permissions = new HashSet<>();
		for (Permission permission : permissionList) {
			//非菜单权限点
			if (StringUtils.isNotBlank(permission.getUrl())) {
				permissions.add(permission.getUrl());
			}
			//按钮
			if (PermissionTypeEnum.BUTTON.getCode().equals(permission.getType())) {
				continue;
			}
			Tree roleTree = new Tree();
			roleTree.setTitle(permission.getPermissionName());
			roleTree.setValue(permission.getId()+"");
			roleTree.setId(permission.getId()+"");
			roleTree.setPId(permission.getPid()+"");
			roleTree.setUrl(permission.getUrl());
			roleTree.setIcon(permission.getIcon());
			roleTree.setSort(permission.getSort());
			nodes.add(roleTree);
		}
		List<Tree> nodeList = nodes.stream().distinct().collect(Collectors.toList());
		//构建用户中心左侧菜单栏
		List<Tree> buildTree = Tree.buildTree(nodeList, true);
		TokenUtils.setAttribute("menus", buildTree);
		return permissions;
	}
	
	/**
	 * 授权，加载用户角色和权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Long userId = (Long) principals.getPrimaryPrincipal();
		// 角色信息
		List<Long> roles = roleService.getRoleIdsByUserId(userId);
		Set<String> roleSet = roles.stream().map(r-> String.valueOf(r)).collect(Collectors.toSet());
		// 权限信息
		Set<String> permissions = loadMenus(userId);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRoles(roleSet);
		simpleAuthorizationInfo.addStringPermissions(permissions);
		return simpleAuthorizationInfo;
	}
	
	/**
	 * 验证,校验用户名和密码是否正确，同时加载菜单到session
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token)  {
		String username = (String) token.getPrincipal();
		String inputPassword = new String((char[]) token.getCredentials());
		User inputUser=new User();
		inputUser.setUsername(username);
		inputUser.setPassword(inputPassword);
		User userInDb=userService.checkLogin(inputUser);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInDb.getId(), inputPassword, getName());
		TokenUtils.setAttribute(MyConstant.USER,userInDb);
		TokenUtils.setAttribute(MyConstant.USER_ID, userInDb.getId());
		//加载菜单到session
		loadMenus(userInDb.getId());
		return authenticationInfo;
	}
	
	/**
	 * 清除当前用户验证和授权信息
	 */
	public void cleanAll(){
		clearAuth();
		clearAuthz();
	}
	
	/**
	 * 清除当前用户登陆信息
	 */
	public void clearAuth() {
		this.clearCachedAuthenticationInfo(SecurityUtils.getSubject().getPrincipals());
	}
	
	/**
	 * 删除当前用户权限信息
	 */
	public void clearAuthz() {
		this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}
	
	/**
	 * 删除指定用户认证信息,即删除用户session信息,如果用户处于登录状态，会被踢出
	 * @param userId 被删除人id
	 */
	public void clearAuth(Long userId) {
		Collection<Session> activeSessions = redisSessionDAO.getActiveSessions();
		for (Session session : activeSessions) {
			User user=(User) session.getAttribute("user");
			if(user!=null&&user.getId().equals(userId)){
				redisSessionDAO.delete(session);
			}
		}
	}
	
	
	/**
	 * 删除指定用户授权信息
	 * @param userId 被删除人id
	 */
	public void clearAuthz(Long userId) {
		logger.info("删除指定用户授权信息,userId={}",userId);
		Subject subject = SecurityUtils.getSubject();
		String realmName = subject.getPrincipals().getRealmNames().iterator().next();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(userId, realmName);
		subject.runAs(principals);
		if(getAuthorizationCache()!=null){
			getAuthorizationCache().remove(subject.getPrincipals());
			this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
		}
		subject.releaseRunAs();
	}

	/**
	 * 删除所有用户权限信息
	 */
	public void clearAllCachedAuthorizationInfo() {
		logger.debug(">>>>>>>>>>>>>>>>>>>清空所有用户权限>>>>>>>>>>>>>>>>>>>");
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				if (key instanceof String) {
					String str = (String) key;
					if (((String) key).indexOf(RedisCache.CACHE_PREFIX) > -1) {
						str = str.replace(RedisCache.CACHE_PREFIX, "");
						cache.remove(str);
					}
				} else {
					cache.remove(key);
				}
			}
		}
	}

	/**
	 * 判断当前用户是否在线
	 *  @param userId 用户id
	 */
	public boolean isOnline(Long userId,Collection<Session> activeSessions) {
		for (Session session : activeSessions) {
			User user=(User) session.getAttribute("user");
			if(user!=null&&user.getId().equals(userId)){
				return true;
			}
		}
		return false;
	}
}
