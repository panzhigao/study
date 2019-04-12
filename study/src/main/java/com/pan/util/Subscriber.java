package com.pan.util;

import com.pan.common.constant.RedisChannelConstant;
import com.pan.common.enums.CacheSyncEnum;
import com.pan.service.impl.ArticleCategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

/**
 * @author panzhigao
 */
public class Subscriber extends JedisPubSub{

    private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

    @Override
    public void onMessage(String channel, String message) {
        //休眠2秒，让数据库数据提交
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("redis subscribe onMessage,channel={},message={}",channel,message);
        super.onMessage(channel, message);
        if(RedisChannelConstant.CHANNEL_CACHE_SYNC.equals(channel)){
            String[] split = message.split(":");
            int length=2;
            if(split.length==length){
                String type=split[0];
                //文章分类
                if(CacheSyncEnum.ARTICLE_CATEGORY.getName().equals(type)){
                    logger.info("同步文章分类缓存");
                    String id=split[1];
                    ArticleCategoryServiceImpl.refreshCache(Long.valueOf(id));
                //系统配置
                }else if(CacheSyncEnum.SYSTEM_CONFIG.getName().equals(type)){
                    logger.info("同步文章系统配置缓存");
                    SystemConfigUtils.refreshSystemConfig();
                }
            }
        }
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("redis subscribe onSubscribe,channel={},subscribedChannels={}",channel,subscribedChannels);
        super.onSubscribe(channel, subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        logger.info("redis subscribe onUnsubscribe,channel={},subscribedChannels={}",channel,subscribedChannels);
        super.onUnsubscribe(channel, subscribedChannels);
    }
}
