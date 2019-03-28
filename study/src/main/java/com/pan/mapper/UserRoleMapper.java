package com.pan.mapper;


import com.pan.entity.UserRole;

import java.util.List;

/**
 * @author panzhigao
 */
public interface UserRoleMapper extends BaseMapper<UserRole>{
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
}