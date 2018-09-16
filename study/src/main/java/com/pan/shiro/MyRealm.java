package com.pan.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.pan.dto.Tree;
import com.pan.entity.Permission;
import com.pan.entity.User;
import com.pan.service.PermissionService;
import com.pan.service.RoleService;
import com.pan.service.UserService;
import com.pan.util.TokenUtils;

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
	private Set<String> loadMenus(String userId) {
		logger.debug("---------------从数据库加载权限--------------------");
		List<Permission> permissionList = permissionService
				.findPermissionsByUserId(userId);
		List<Tree> nodes = new ArrayList<Tree>(20);
		Set<String> permissions = new HashSet<>();
		for (Permission permission : permissionList) {
			// 非菜单路径
			if (StringUtils.isNotBlank(permission.getUrl())) {
				permissions.add(permission.getUrl());
			}
			if ("2".equals(permission.getType())) {
				continue;
			}
			Tree roleTree = new Tree();
			roleTree.setTitle(permission.getPermissionName());
			roleTree.setValue(permission.getPermissionId());
			roleTree.setId(permission.getPermissionId());
			roleTree.setpId(permission.getpId());
			roleTree.setUrl(permission.getUrl());
			roleTree.setIcon(permission.getIcon());
			roleTree.setSort(permission.getSort());
			nodes.add(roleTree);
		}
		List<Tree> buildTree = Tree.buildTree(nodes, true);
		TokenUtils.setAttribute("menus", buildTree);
		return permissions;
	}
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String userId = (String) principals.getPrimaryPrincipal();
		// 角色信息
		List<String> roles = roleService.getRoleByUserId(userId);
		// 权限信息
		Set<String> permissions = loadMenus(userId);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRoles(roles);
		simpleAuthorizationInfo.addStringPermissions(permissions);
		return simpleAuthorizationInfo;
	}
	
	/**
	 * 验证
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
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInDb.getUserId(), inputPassword, getName());
		TokenUtils.setAttribute(MyConstant.USER,userInDb);
		TokenUtils.setAttribute(MyConstant.USER_ID, userInDb.getUserId());
		//加载菜单到session
		loadMenus(userInDb.getUserId());
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
	 * 删除指定用户认证信息,即删除用户session信息
	 * @param user 被删除人
	 */
	public void clearAuth(String userId) {
		Collection<Session> activeSessions = redisSessionDAO.getActiveSessions();
		for (Session session : activeSessions) {
			User user=(User) session.getAttribute("user");
			if(user!=null&&StringUtils.equals(user.getUserId(), userId)){
				redisSessionDAO.delete(session);
			}
		}
	}
	
	
	/**
	 * 删除指定用户授权信息
	 * @param user 被删除人
	 */
	public void clearAuthz(String userId) {
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
		logger.debug("----------------清空所有用户权限-----------------");
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
	
}
