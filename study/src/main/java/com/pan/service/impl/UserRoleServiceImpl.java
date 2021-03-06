package com.pan.service.impl;

import com.pan.entity.UserRole;
import com.pan.mapper.UserRoleMapper;
import com.pan.query.QueryUserRole;
import com.pan.service.AbstractBaseService;
import com.pan.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author panzhigao
 */
@Service
public class UserRoleServiceImpl extends AbstractBaseService<UserRole,UserRoleMapper> implements IUserRoleService{

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public int batchAddUserRole(List<UserRole> list) {
        return userRoleMapper.batchAddUserRole(list);
    }

    @Override
    public int deleteUserRoleByUserId(Long userId) {
        return userRoleMapper.deleteUserRoleByUserId(userId);
    }

    @Override
    public int findUserCountByRoleId(Long roleId) {
        QueryUserRole queryUserRole=new QueryUserRole();
        queryUserRole.setRoleId(roleId);
        return userRoleMapper.countByParams(queryUserRole);
    }

    @Override
    public void addUserRole(UserRole userRole) {
        List<UserRole> list = new ArrayList<>(1);
        list.add(userRole);
        userRoleMapper.batchAddUserRole(list);
    }
}
