package com.pan.common.enums;

/**
 * @author panzhigao
 * 消息类型
 */

public enum MessageTypeEnum {
    /**
     * 评论
     */
    COMMENT(1, "评论"),
    /**
     * 系统消息
     * eg.文章审核通过，未通过
     */
    SYSTEM_MESSAGE(2, "系统消息"),
    /**
     * 公告
     * 用户页面主动弹出
     */
    NOTICE(3, "公告");

    private Integer code;

    private String name;

    MessageTypeEnum(Integer code, String msg) {
        this.code = code;
        this.name = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
