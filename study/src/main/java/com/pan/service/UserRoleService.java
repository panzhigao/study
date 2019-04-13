package com.pan.service;

import com.pan.entity.UserRole;

import java.util.List;

/**
 * @author panzhigao
 */
public interface UserRoleService extends BaseService<UserRole>{
    /**
     * 批量添加加用户角色
     * @param list
     * @return
     */
    int batchAddUserRole(List<UserRole> list);
    /**
     * 根据用户id删除用户角色
     * @param userId
     * @return
     */
    int deleteUserRoleByUserId(Long userId);
    /**
     * 通过角色id查找使用该角色的用户数
     * @param roleId 角色id
     * @return
     */
    int findUserCountByRoleId(Long roleId);
    /**
     * 新增用户角色
     * @param userRole
     */
    void addUserRole(UserRole userRole);
}
