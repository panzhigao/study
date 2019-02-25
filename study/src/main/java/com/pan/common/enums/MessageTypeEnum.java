package com.pan.common.enums;

/**
 * @author panzhigao
 * 消息类型
 */

public enum MessageTypeEnum {
    /**
     * 评论
     */
    COMMENT("1", "评论"),
    /**
     * 系统消息
     */
    SYSTEM_MESSAGE("2", "系统消息"),
    /**
     * 公告
     */
    NOTICE("3", "公告");

    private String code;

    private String msg;

    MessageTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
