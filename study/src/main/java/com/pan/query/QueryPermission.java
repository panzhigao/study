package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryPermission extends QueryBase{
    /**
     * 权限id
     */
    private Long permissionId;
    /**
     * 权限名
     */
    private String permissionName;
    /**
     * 权限点
     */
    private String permissionPoint;
}
