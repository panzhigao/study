package com.pan.common.enums;

/**
 * 操作日志枚举
 * @author panzhigao
 */
public enum OperateLogTypeEnum {
    /**
     * 新增权限
     */
    ADD_PERMISSION(1, "新增权限"),
    /**
     * 编辑权限
     */
    EDIT_PERMISSION(2, "编辑权限"),
    /**
     * 删除权限
     */
    DELETE_PERMISSION(3, "删除权限");

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
