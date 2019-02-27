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
    private String permissionId;
    
    private String permissionName;
}
