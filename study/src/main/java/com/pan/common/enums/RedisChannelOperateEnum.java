package com.pan.common.enums;

/**
 * redis订阅操作枚举
 * @author panzhigao
 */
public enum RedisChannelOperateEnum {
    /**
     * 文章分类缓存重载
     */
    RECACHE_ARTICLE_CATEGORY(0,"recache_category"),
    /**
     * 系统配置缓存重载
     */
    RECACHE_SYSTEM_CONFIG(1,"recache_config"),
    /**
     * 链接缓存重载
     */
    RECACHE_LINK(2,"recache_link"),
    /**
     * 文章es更新或创建
     */
    ARTICLE_ES_CREATE_OR_UPDATE(3,"article_es_create_or_update");

    private Integer code;

    private String name;

    RedisChannelOperateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
