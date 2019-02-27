package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.entity.*;
import com.pan.mapper.BaseMapper;
import com.pan.query.QueryPermission;
import com.pan.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.constant.MyConstant;
import com.pan.common.enums.AdminFlagEnum;
import com.pan.common.exception.BusinessException;
import com.pan.dto.Tree;
import com.pan.dto.TreeNode;
import com.pan.mapper.PermissionMapper;
import com.pan.query.QueryRole;
import com.pan.util.IdUtils;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;


/**
 * 
 * @author Administrator
 * 
 */
@Service
public class PermissionServiceImpl extends AbstractBaseService<Permission,PermissionMapper> implements  PermissionService {

	private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private OperateLogService operateLogService;

	/**
	 * 新增权限，同时为自动为超级管理员添加该权限
	 * 记录日志
	 */
	@Override
	public void addPermission(Permission permission) {
		logger.info("新增权限：{}",permission);
		ValidationUtils.validateEntity(permission);
		if(StringUtils.isBlank(permission.getPid())){
			permission.setPid("0");
		}
		if(permission.getSort()==null){
			permission.setSort(1);
		}
		Date now=new Date();
		permission.setCreateTime(now);
		User loginUser = TokenUtils.getLoginUser();
		permission.setCreateUser(loginUser.getUserId());
		permission.setPermissionId(IdUtils.generatePermissionId());
		permissionMapper.insertSelective(permission);
		//记录操作日志
		operateLogService.addOperateLog(permission.toString(),OperateLogTypeEnum.ADD_PERMISSION);
		QueryRole queryRoleVO=new QueryRole();
		queryRoleVO.setSuperAdminFlag(AdminFlagEnum.ADMIN_TRUE.getCode());
		//自动为超级管理员添加权限
		List<Role> list = roleService.findByParams(queryRoleVO);
		if(list.size()>0){
			String roleId=list.get(0).getRoleId();
			RolePermission rolePermission=new RolePermission(roleId,permission.getPermissionId());
			rolePermissionService.addRolePermission(rolePermission);
			List<User> users = userService.findUserByRoleId(roleId);
			for (User user : users) {
				TokenUtils.clearAuthz(user.getUserId());
			}
		}
	}
	
	/**
	 * 通过权限id获取权限信息并校验
	 * @param permissionId
	 */
	private Permission getAndCheck(String permissionId){
		Permission permission = permissionMapper.selectByPermissionId(permissionId);
		if(permission==null){
			logger.error("根据权限id未查询到权限信息，permissionId={}",permissionId);
			throw new BusinessException("该权限点不存在");
		}
		return permission;
	}
	
	/**
	 * 删除权限点，同时删除角色权限标里关联该权限点的数据
	 * 同时删除缓存中角色的权限信息，记录操作日志
	 */
	@Override
	public int deleteByPermissionId(String permissionId) {
		Permission permission = getAndCheck(permissionId);
		//删除权限信息
		int p = permissionMapper.deleteByPrimaryKey(permission.getId());
		//删除角色权限管联信息
		int rp = rolePermissionService.deleteRolePermissionByPermissionId(permission.getPermissionId());
		logger.info("删除权限点，删除权限点条数：{}，删除角色权限关联信息条数：{}",p,rp);
		//记录日志
		operateLogService.addOperateLog(permission.toString(),OperateLogTypeEnum.DELETE_PERMISSION);
		//删除缓存数据
		TokenUtils.clearAllUserAuth();
		return p;
	}

	@Override
	public List<TreeNode> getTreeData() {
		List<Permission> list = this.permissionMapper.findAll();
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		for (Permission permission : list) {
			TreeNode treeNode=new TreeNode();
			treeNode.setId(permission.getPermissionId());
			treeNode.setpId(permission.getPid());
			treeNode.setName(permission.getPermissionName());
			treeNode.setUrl(permission.getUrl());
			treeNode.setIcon(permission.getIcon());
			treeNode.setSort(permission.getSort());
			treeNode.setType(permission.getType());
			nodes.add(treeNode);
		}
		return nodes;
	}

	@Override
	public List<Tree> getPermissionTreeData(String roleId) {
		if(StringUtils.isBlank(roleId)){
			return new ArrayList<Tree>();
		}
		List<Permission> list = this.permissionMapper.getPermissionSelectedByRoleId(roleId);
		List<Tree> nodes=new ArrayList<Tree>(20);
		for (Permission permission : list) {
			Tree permissionTree=new Tree();
			permissionTree.setTitle(permission.getPermissionName());
			permissionTree.setValue(permission.getPermissionId());
			permissionTree.setId(permission.getPermissionId());
			permissionTree.setPId(permission.getPid());
			permissionTree.setIcon(permission.getIcon());
			permissionTree.setSort(permission.getSort());
			if(!StringUtils.equals(permission.getMarker(),"0")){
				permissionTree.setChecked(true);
			}
			nodes.add(permissionTree);
		}
		return Tree.buildTree(nodes,true);
	}

	@Override
	public List<Permission> getPermissionByRoleId(String roleId) {
		return permissionMapper.getPermissionByRoleId(roleId);
	}

	@Override
	public Permission getByPermissionId(String permissionId) {
		QueryPermission queryPermission=new QueryPermission();
		queryPermission.setPermissionId(permissionId);
		List<Permission> list = this.permissionMapper.findPagable(queryPermission);
		return list.size()==1?list.get(0):null;
	}

	@Override
	public void updatePermission(Permission permission) {
		if(StringUtils.isBlank(permission.getPermissionId())){
			throw new BusinessException("参数有误，权限id为空");
		}
		Permission permissionInDb = getAndCheck(permission.getPermissionId());
		permission.setId(permissionInDb.getId());
		if(MyConstant.PERMISSION_TYPE_MENU.equals(permission.getType())){
			permission.setUrl("  ");
		}
		String loginUserId = TokenUtils.getLoginUserId();
		permission.setUpdateUser(loginUserId);
		permission.setUpdateTime(new Date());
		permissionMapper.updateByPrimaryKeySelective(permission);
		TokenUtils.clearAllUserAuth();
	}

	@Override
	public List<Permission> getAll() {
		return permissionMapper.findAll();
	}

	@Override
	public List<Permission> findPermissionsByUserId(String userId) {
		return permissionMapper.findPermissionsByUserId(userId);
	}

	@Override
	protected <M> BaseMapper<Permission> getBaseMapper() {
		return permissionMapper;
	}
}
