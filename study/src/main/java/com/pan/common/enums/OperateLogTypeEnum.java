package com.pan.common.enums;

/**
 * 操作日志枚举
 * @author panzhigao
 */
public enum OperateLogTypeEnum {
    /**
     * 新增权限
     */
    PERMISSION_ADD(101, "新增权限"),
    /**
     * 编辑权限
     */
    PERMISSION_EDIT(102, "编辑权限"),
    /**
     * 删除权限
     */
    PERMISSION_DELETE(103, "删除权限"),
    /**
     * 新增角色
     */
    ROLE_ADD(201, "新增角色"),
    /**
     * 编辑角色
     */
    ROLE_EDIT(202, "编辑角色"),
    /**
     * 删除角色
     */
    ROLE_DELETE(203, "删除角色"),
    /**
     * 分配角色
     */
    ROLE_ALLOCATE(204, "分配角色"),
	/**
	 * 用户禁用
	 */
	USER_DISABLE(301,"用户禁用"),
	/**
	 * 用户开启
	 */
	USER_ENABLE(302,"用户开启"),
	/**
	 * 编辑系统配置
	 */
	SYSTEM_CONFIG_EDIT(401,"编辑系统配置");
	
	
    private Integer code;

    private String name;

    OperateLogTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static String getNameByCode(Integer code){
		OperateLogTypeEnum[] values = OperateLogTypeEnum.values();
		for(OperateLogTypeEnum operateLogTypeEnum:values){
			if(operateLogTypeEnum.getCode().equals(code)){
				return operateLogTypeEnum.getName();
			}
		}
		return "未知";
	}
	
	public static OperateLogTypeEnum[] getEnums(){
		return OperateLogTypeEnum.values();
	}
	
}
