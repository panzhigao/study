package com.pan.common.enums;

/**
 * 缓存同步枚举
 * @author panzhigao
 */
public enum CacheSyncEnum {
    /**
     * 文章分类
     */
    ARTICLE_CATEGORY(0,"category"),
    /**
     * 系统配置
     */
    SYSTEM_CONFIG(1,"config"),
    /**
     * 链接
     */
    LINK(2,"link");

    private Integer code;

    private String name;

    CacheSyncEnum(Integer code, String name) {
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
