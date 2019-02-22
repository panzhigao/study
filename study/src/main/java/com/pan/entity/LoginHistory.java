package com.pan.entity;


/**
 * 登录历史
 *
 * @author panzhigao
 */
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
     * ip地址
     */
    private String ipStr;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIp() {
        return ip;
    }

    public void setIp(Integer ip) {
        this.ip = ip;
    }

    public String getIpStr() {
        return ipStr;
    }

    public void setIpStr(String ipStr) {
        this.ipStr = ipStr;
    }

    @Override
    public String toString() {
        return "LoginHistory{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", ip=" + ip +
                ", ipStr='" + ipStr + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}
