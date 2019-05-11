package com.pan.common.enums;

/**
 * redis订阅频道枚举
 * @author panzhigao
 */
public enum RedisChannelEnum {
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
    ARTICLE_ES_CREATE_OR_UPDATE(3,"article_es_create_or_update"),
    /**
     * 用户es更新或创建
     */
    USER_ES_CREATE_OR_UPDATE(4,"user_es_create_or_update");

    private Integer code;

    private String name;

    RedisChannelEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    /**
     * 获取所有频道
     * @return
     */
    public static String[] getAllChannel(){
    	RedisChannelEnum[] values = RedisChannelEnum.values();
    	String[] arr=new String[values.length];
    	for(int i=0;i<values.length;i++){
    		arr[i]=values[i].name;
    	}
    	return arr;
    }
    
    public static void main(String[] args) {
		String[] channelArr = getAllChannel();
		for(String str:channelArr){
			System.out.println(str);
		}
	}
    
}
