package com.pan.entity;


import com.pan.common.annotation.LogMeta;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 权限实体类
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Permission extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -338202844637487403L;
    /**
     * 权限名
     */
    @NotEmpty(message = "权限名不能为空")
    @Size(max=64,message="权限名称不能超过64个字符")
    @LogMeta(fieldDesc = "权限名称")
    private String permissionName;
    /**
     * 权限点
     */
    @NotEmpty(message = "权限点不能为空")
    @Size(max=64,message="权限点不能超过64个字符")
    @LogMeta(fieldDesc = "权限点")
    private String permissionPoint;
    /**
     * 权限路径
     */
    @LogMeta(fieldDesc = "权限路径")
    private String url;
    /**
     * 父级pid
     */
    private Long pid;
    /**
     * 标识，0-未选中
     */
    private String marker;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 排序
     */
    @LogMeta(fieldDesc = "权限排序")
    private Integer sort;
    /**
     * 图标
     */
    @LogMeta(fieldDesc = "权限图标")
    private String icon;
    /**
     * 类型 0-菜单 1-链接 2-按钮
     */
    @LogMeta(fieldDesc = "权限类型")
    private Integer type;
}
