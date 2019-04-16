package com.pan.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ExceptionLog extends BaseEntity{

    private static final long serialVersionUID = -233818559731942762L;
    /**
     * 用户名
     */
    private String username;

    private Integer ip;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 异常类型
     */
    private String exceptionType;
    /**
     * 是否查看，0：未查看、1：已查看
     */
    private Integer isView;
    /**
     * 异常信息
     */
    private String exceptionMsg;
}