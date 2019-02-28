package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author panzhigao-wb
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class RolePermission extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 8947795048710408903L;
    /**
     * 角色id
     * {@link Role.roleId}
     */
    private String roleId;
    /**
     * 权限id
     * {@link Permission.permissionId}
     */
    private String permissionId;

    public RolePermission() {
        super();
    }

    public RolePermission(String roleId, String permissionId) {
        super();
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

}
