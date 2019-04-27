package com.pan.util;

import com.pan.common.constant.MyConstant;
import com.pan.common.constant.RedisChannelConstant;
import com.pan.common.enums.RedisChannelOperateEnum;
import com.pan.service.ArticleService;
import com.pan.service.impl.ArticleCategoryServiceImpl;
import com.pan.service.impl.LinkServiceImpl;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
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
                if(RedisChannelOperateEnum.RECACHE_ARTICLE_CATEGORY.getName().equals(type)){
                    logger.info("同步文章分类缓存");
                    String id=split[1];
                    ArticleCategoryServiceImpl.refreshCache(Long.valueOf(id));
                //系统配置
                }else if(RedisChannelOperateEnum.RECACHE_SYSTEM_CONFIG.getName().equals(type)){
                    logger.info("同步系统配置缓存");
                    String paramName=split[1];
                    SystemConfigUtils.refreshSystemConfig(paramName);
                //链接
                }else if(RedisChannelOperateEnum.RECACHE_LINK.getName().equals(type)){
                	logger.info("同步链接缓存");
                    LinkServiceImpl.onlineLinkCache.refresh(MyConstant.DEFAULT_KEY);
                //文章es修改    
                }else if(RedisChannelOperateEnum.ARTICLE_ES_CREATE.getName().equals(type)){
                	logger.info("文章es新增 ");
                	ArticleService articleService = SpringContextUtils.getBean(ArticleService.class);
                	List<String> list = JedisUtils.blpop(MyConstant.ARTICLE_ES_REDIS_LIST);
                	if(CollectionUtils.isNotEmpty(list)){
                		String id=list.get(1);
                		logger.info("修改文章es数据,id={}",id);
                		articleService.createArticleEs(Long.parseLong(id));
                	}
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
