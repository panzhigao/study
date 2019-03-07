package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 操作日志
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OperateLog extends BaseEntity{
    private static final long serialVersionUID = 7151401128307567372L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 日志内容
     */
    private String content;
    /**
     * 操作类型
     */
    private Integer operateType;
    /**
     * ip地址
     */
    private Integer ip;
    /**
     * 创建时间
     */
    private Date createTime;
}