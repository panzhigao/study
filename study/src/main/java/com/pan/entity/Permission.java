package com.pan.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
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
     * 权限id
     */
    private String permissionId;
    /**
     * 权限名
     */
    @NotEmpty(message = "权限名不能为空")
    private String permissionName;
    /**
     * 权限路径
     */
    @NotEmpty(message = "权限路径url不能为空")
    private String url;
    /**
     * 父级pid
     */
    private String pId;
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
    private Integer sort;
    /**
     * 图标
     */
    private String icon;
    /**
     * 类型 0-菜单 1-链接 2-按钮
     */
    private String type;
}
