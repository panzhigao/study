package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.common.enums.PermissionTypeEnum;
import com.pan.service.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.enums.AdminFlagEnum;
import com.pan.common.exception.BusinessException;
import com.pan.dto.Tree;
import com.pan.dto.TreeNode;
import com.pan.entity.Permission;
import com.pan.entity.Role;
import com.pan.entity.RolePermission;
import com.pan.entity.User;
import com.pan.mapper.PermissionMapper;
import com.pan.query.QueryRole;
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
		if(!PermissionTypeEnum.MENU.getCode().equals(permission.getType())){
			if(StringUtils.isBlank(permission.getUrl())){
				throw new BusinessException("url不能为空");
			}
		}
		if(permission.getPid()==null){
			permission.setPid(0L);
		}
		if(permission.getSort()==null){
			permission.setSort(1);
		}
		Date now=new Date();
		permission.setCreateTime(now);
		User loginUser = TokenUtils.getLoginUser();
		permission.setCreateUserId(loginUser.getId());
		permissionMapper.insertSelective(permission);
		//记录操作日志
		operateLogService.addOperateLog(permission.toString(),OperateLogTypeEnum.PERMISSION_ADD);
		QueryRole queryRoleVO=new QueryRole();
		queryRoleVO.setSuperAdminFlag(AdminFlagEnum.ADMIN_TRUE.getCode());
		//自动为超级管理员添加权限
		List<Role> list = roleService.findPageable(queryRoleVO);
		if(list.size()>0){
			Long roleId=list.get(0).getId();
			RolePermission rolePermission=new RolePermission(roleId,permission.getId());
			rolePermission.setCreateTime(now);
			rolePermission.setCreateUserId(loginUser.getId());
			rolePermissionService.addRolePermission(rolePermission);
			List<User> users = userService.findUserByRoleId(roleId);
			for (User user : users) {
				TokenUtils.clearAuthz(user.getId());
			}
		}
	}
	
	/**
	 * 通过权限id获取权限信息并校验
	 * @param permissionId
	 */
	private Permission getAndCheck(Long permissionId){
		Permission permission = permissionMapper.selectByPrimaryKey(permissionId);
		if(permission==null){
			logger.error("根据权限id未查询到权限信息，permissionId={}",permissionId);
			throw new BusinessException("该权限点不存在");
		}
		return permission;
	}
	
	private int deleteAll(Long permissionId){
		Permission permission = getAndCheck(permissionId);
		//删除权限信息
	    int count = permissionMapper.deleteByPrimaryKey(permission.getId());
		//删除角色权限关联信息
		int rp = rolePermissionService.deleteRolePermissionByPermissionId(permission.getId());
		logger.info("删除权限点{}，删除角色权限关联信息条数：{}",permission.getPermissionName(),rp);
		//记录日志
		operateLogService.addOperateLog(permission.toString(),OperateLogTypeEnum.PERMISSION_DELETE);
		List<Permission> children = permissionMapper.findByParentId(permissionId);
		if(CollectionUtils.isEmpty(children)){
			return count;
		}
		for(Permission item:children){
			count=count+deleteAll(item.getId());
		}
		return count;
	}
	
	/**
	 * 删除权限点，同时删除角色权限标里关联该权限点的数据
	 * 同时删除缓存中角色的权限信息，记录操作日志
	 *
	 * //权限点 修改 r:add
	 */
	@Override
	public int deleteByPermissionId(Long permissionId) {
		//查询当前节点的子节点个数
		int countByParentId = permissionMapper.countByParentId(permissionId);
		if(countByParentId>0){
			logger.info("当前权限点含有子权限");	
		}
		int count = deleteAll(permissionId);
		logger.info("共删除权限{}条",count);
		//删除缓存数据
		TokenUtils.clearAllUserAuth();
		return count;
	}

	@Override
	public List<TreeNode> getTreeData() {
		List<Permission> list = this.permissionMapper.findAll();
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		for (Permission permission : list) {
			TreeNode treeNode=new TreeNode();
			treeNode.setId(permission.getId()+"");
			treeNode.setpId(permission.getPid()+"");
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
	public List<Tree> getPermissionTreeData(Long roleId) {
		if(roleId==null){
			return new ArrayList<Tree>();
		}
		List<Permission> list = this.permissionMapper.getPermissionSelectedByRoleId(roleId);
		List<Tree> nodes=new ArrayList<Tree>(20);
		for (Permission permission : list) {
			Tree permissionTree=new Tree();
			permissionTree.setTitle(permission.getPermissionName());
			permissionTree.setValue(permission.getId()+"");
			permissionTree.setId(permission.getId()+"");
			permissionTree.setPId(permission.getPid()+"");
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
	public List<Permission> getPermissionByRoleId(Long roleId) {
		return permissionMapper.getPermissionByRoleId(roleId);
	}


	/**
	 * 更新权限，记录日志
	 * @param permission
	 */
	@Override
	public void updatePermission(Permission permission) {
		if(permission.getId()==null){
			throw new BusinessException("参数有误，权限id为空");
		}
		Permission permissionInDb = getAndCheck(permission.getId());
		permission.setId(permissionInDb.getId());
		if(PermissionTypeEnum.MENU.getCode().equals(permission.getType())){
			permission.setUrl("  ");
		}
		Long loginUserId = TokenUtils.getLoginUserId();
		permission.setUpdateUserId(loginUserId);
		permission.setUpdateTime(new Date());
		Long permissionId=permission.getId();
		String changedFields = ValidationUtils.getChangedFields(permission, permissionInDb);
		permissionMapper.updateByPrimaryKeySelective(permission);
		//记录操作日志
		operateLogService.addOperateLog(String.format("权限id：%s，编辑内容：%s",permissionId,changedFields),OperateLogTypeEnum.PERMISSION_EDIT);
		TokenUtils.clearAllUserAuth();
	}

	@Override
	public List<Permission> getAll() {
		return permissionMapper.findAll();
	}

	@Override
	public List<Permission> findPermissionsByUserId(Long userId) {
		return permissionMapper.findPermissionsByUserId(userId);
	}


	@Override
	protected PermissionMapper getBaseMapper() {
		return permissionMapper;
	}
}
