package com.pan.common.enums;

/**
 * 操作日志枚举
 * @author panzhigao
 */
public enum OperateLogTypeEnum {
    /**
     * 新增权限
     */
    PERMISSION_ADD(11, "新增权限"),
    /**
     * 编辑权限
     */
    PERMISSION_EDIT(12, "编辑权限"),
    /**
     * 删除权限
     */
    PERMISSION_DELETE(13, "删除权限"),
    /**
     * 新增角色
     */
    ROLE_ADD(21, "新增角色"),
    /**
     * 编辑角色
     */
    ROLE_EDIT(22, "编辑角色"),
    /**
     * 删除角色
     */
    ROLE_DELETE(23, "删除角色");

    private Integer code;

    private String desc;

    OperateLogTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
