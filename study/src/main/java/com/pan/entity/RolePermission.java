package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


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
     * {@link Role.id}
     */
    private Long roleId;
    /**
     * 权限id
     * {@link Permission.id}
     */
    private Long permissionId;

    public RolePermission() {
        super();
    }

    public RolePermission(Long roleId, Long permissionId) {
        super();
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

}
