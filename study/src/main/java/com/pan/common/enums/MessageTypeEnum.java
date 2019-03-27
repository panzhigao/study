package com.pan.common.enums;

/**
 * @author panzhigao
 * 消息类型
 */

public enum MessageTypeEnum {
    /**
     * 公告
     * 用户页面主动弹出
     */
    NOTICE(0, "公告"),
    /**
     * 评论
     */
    COMMENT(1, "评论"),
    /**
     * 文章审核未通过
     */
    ARTICLE_CHECK_NOT_PASS(2, "文章审核未通过"),
    /**
     * 文章审核通过
     */
    ARTICLE_CHECK_PASS(3, "文章审核通过"),
    /**
     * 点赞评论
     */
    COMMENT_PRAISE(4, "点赞评论");

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
