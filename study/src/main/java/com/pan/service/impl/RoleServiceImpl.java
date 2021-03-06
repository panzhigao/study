package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.pan.common.enums.AdminFlagEnum;
import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.service.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.exception.BusinessException;
import com.pan.dto.Tree;
import com.pan.entity.Role;
import com.pan.entity.RolePermission;
import com.pan.entity.User;
import com.pan.mapper.RoleMapper;
import com.pan.query.QueryRole;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;

/**
 * 角色关联
 * @author panzhigao
 */
@Service
public class RoleServiceImpl extends AbstractBaseService<Role,RoleMapper> implements IRoleService{
	
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private IRolePermissionService rolePermissionService;
	
	@Autowired
	private IUserService userService;

	@Autowired
	private IUserRoleService userRoleService;

	@Autowired
	private IOperateLogService operateLogService;

	/**
	 * 新增角色信息，角色名称不能重复，并记录日志
	 * @param role
	 */
	@Override
	public void addRole(Role role) {
		logger.info("新增角色：{}",role);
		ValidationUtils.validateEntity(role);
		QueryRole queryRole=new QueryRole();
		queryRole.setRoleName(role.getRoleName());
		int countByParams = roleMapper.countByParams(queryRole);
		if(countByParams>0){
			throw new BusinessException("改角色名已存在，请重新输入");
		}
		Long loginUserId = TokenUtils.getLoginUserId();
		role.setCreateTime(new Date());
		role.setCreateUserId(loginUserId);
		role.setSuperAdminFlag(AdminFlagEnum.ADMIN_FALSE.getCode());
		roleMapper.insertSelective(role);
		//记录日志
		operateLogService.addOperateLog(role.toString(), OperateLogTypeEnum.ROLE_ADD);
	}

	/**
	 * 删除角色，超级管理员角色不能删除，已被使用的角色不能删除
	 * 记录日志
	 * @param roleId 角色id
	 */
	@Override
	public void deleteRole(Long roleId) {
		Role roleInDb = this.selectByPrimaryKey(roleId);
		if(roleInDb==null){
			logger.error("根据角色id未查询到角色信息,roleId={}",roleId);
			throw new BusinessException("该角色不存在");
		}
		if(AdminFlagEnum.ADMIN_TRUE.getCode().equals(roleInDb.getSuperAdminFlag())){
			throw new BusinessException("超级管理员角色不能删除");
		}
		int count = userRoleService.findUserCountByRoleId(roleId);
		if(count>0){
			throw new BusinessException("该角色已使用，不能被删除，请先取消该角色的分配");
		}
		//删除角色信息
		roleMapper.deleteByPrimaryKey(roleInDb.getId());
		//删除角色下关联的权限
		rolePermissionService.deleteRolePermissionByRoleId(roleId);
		operateLogService.addOperateLog(roleInDb.toString(),OperateLogTypeEnum.ROLE_ADD);
	}

	/**
	 * 根据角色id获取角色信息，并校验
	 * @param roleId
	 * @return
	 */
	private Role getAndCheck(Long roleId){
		if(roleId==null){
			throw new BusinessException("角色ID未传入");
		}
		Role role = selectByPrimaryKey(roleId);
		if(role==null){
			throw new BusinessException("该角色不存在");
		}
		return role;
	}

	/**
	 * 为角色添加权限
	 * @param roleId 添加权限的角色id
	 * @param permissions 权限id数组
	 */
	@Override
	public void allocatePermissionToRole(Long roleId, Long[] permissions) {
		Role role = getAndCheck(roleId);
		if(AdminFlagEnum.ADMIN_TRUE.getCode().equals(role.getSuperAdminFlag())){
			throw new BusinessException("超级管理员不能编辑");
		}
		//删除该角色下的所有权限，再重新添加
		rolePermissionService.deleteRolePermissionByRoleId(roleId);
		if(ArrayUtils.isNotEmpty(permissions)){
			List<RolePermission> list=new ArrayList<RolePermission>();
			for (Long permissionId : permissions) {
				RolePermission rolePermission=new RolePermission();
				rolePermission.setPermissionId(permissionId);
				rolePermission.setRoleId(roleId);
				rolePermission.setCreateTime(new Date());
				rolePermission.setCreateUserId(TokenUtils.getLoginUserId());
				list.add(rolePermission);
			}
			rolePermissionService.addRolePermission(list);
		}
		reCachePermissionByRoleId(roleId);
	}


	/**
	 * 获取角色层级树
	 */
	@Override
	public List<Tree> getRoleTreeData(Long userId) {
		if(userId==null){
			return new ArrayList<Tree>();
		}
		List<Role> list = this.roleMapper.getRoleSelectedByUserId(userId);
		List<Tree> nodes=new ArrayList<Tree>(20);
		for (Role role : list) {
			Tree roleTree=new Tree();
			roleTree.setTitle(role.getRoleName());
			roleTree.setValue(role.getId()+"");
			roleTree.setId(role.getId()+"");
			roleTree.setPId("0");
			if(!StringUtils.equals(role.getMarker(),"0")){
				roleTree.setChecked(true);
			}
			nodes.add(roleTree);
		}
		return Tree.buildTree(nodes,false);
	}

	@Override
	public List<Long> getRoleIdsByUserId(Long userId) {
		return this.roleMapper.getRoleIdsByUserId(userId);
	}
	
	/**
	 * 重新缓存指定角色用户权限
	 * 通过roleId获取所有拥有该角色的用户，清空用户权限缓存
	 * @param roleId
	 */
	@Override
	public void reCachePermissionByRoleId(Long roleId){
		try {
			List<User> list = userService.findUserByRoleId(roleId);
			for (User user : list) {				
				TokenUtils.clearAuthz(user.getId());
			}
		} catch (Exception e) {
			logger.error("缓存角色权限失败,roleId:{}",roleId);
		}
	}

	/**
	 * 更新角色信息,并记录日志
	 * @param role
	 */
	@Override
	public void updateRole(Role role) {
		ValidationUtils.validateEntity(role);
		Role roleInDb = getAndCheck(role.getId());
		Role updateRole=new Role();
		updateRole.setId(roleInDb.getId());
		updateRole.setRoleName(role.getRoleName());
		updateRole.setRemark(role.getRemark());
		updateRole.setUpdateTime(new Date());
		updateRole.setUpdateUserId(TokenUtils.getLoginUserId());
		roleMapper.updateByPrimaryKeySelective(updateRole);
		String changedFields = ValidationUtils.getChangedFields(roleInDb, updateRole);
		String different=String.format("角色id：%s，编辑内容：%s",role.getId(),changedFields);
		operateLogService.addOperateLog(different,OperateLogTypeEnum.ROLE_EDIT);
	}
}
