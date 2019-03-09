package com.pan.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录历史
 *
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class LoginHistory extends BaseEntity {
    private static final long serialVersionUID = 5675762635184777705L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * ip地址
     */
    private Integer ip;
    /**
     * 用户代理
     */
    private String userAgent;
}
