package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.common.exception.BusinessException;
import com.pan.entity.Role;
import com.pan.mapper.RoleMapper;
import com.pan.service.RoleService;
import com.pan.util.IdUtils;
import com.pan.util.JsonUtils;
import com.pan.util.ValidationUtils;

@Service
public class RoleServiceImpl implements RoleService{
	
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public void addRole(Role role) {
		logger.info("新增角色：{}",role);
		ValidationUtils.validateEntity(role);
		role.setCreateTime(new Date());
		role.setRoleId(IdUtils.generateRoleId());
		roleMapper.addRole(role);
	}

	@Override
	public Map<String, Object> findByParams(Map<String, Object> params) {
		Map<String,Object> pageData=new HashMap<String, Object>(2);
		List<Role> list = new ArrayList<Role>();
		try {
			logger.info("分页权限参数为:{}", JsonUtils.toJson(params));
			int total=roleMapper.getCountByParams(params);
			//当查询记录大于0时，查询数据库记录，否则直接返回空集合
			if(total>0){				
				list = roleMapper.findByParams(params);
			}
			pageData.put("data", list);
			pageData.put("total", total);
			pageData.put("code", "200");
			pageData.put("msg", "");
		} catch (Exception e) {
			logger.error("分页查询权限异常", e);
		}
		return pageData;
	}

	@Override
	public void deleteRole(String roleId) {
		roleMapper.deleteRole(roleId);
	}

	@Override
	public void allocatePermissionToRole(String roleId, String[] permissions) {
		Role role = findByRoleId(roleId);
		if(role==null){
			throw new BusinessException("该角色不存在");
		}
		
		
	}

	@Override
	public Role findByRoleId(String roleId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("roleId", roleId);
		List<Role> list = roleMapper.findByParams(params);
		if(list.size()==1){
			return list.get(0);
		}
		return null;
	}

}
