package com.pan.redissub;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

/**
 * @author panzhigao
 */
public class RedisSubscriber extends JedisPubSub{

    private static final Logger logger = LoggerFactory.getLogger(RedisSubscriber.class);
    
    private List<SubProcessor> processorList;
    
    public void setProcessorList(List<SubProcessor> processorList) {
		this.processorList = processorList;
	}
    
	@Override
    public void onMessage(String channel, String message) {
        //休眠2秒，让数据库数据提交
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        	logger.error("Subscriber线程休眠异常");
        }
        logger.info("redis subscribe onMessage,channel={},message={}",channel,message);
        super.onMessage(channel, message);
        processorList.forEach(s->{
        	if(s.checkChannel(channel)){
        		s.handel(message);
        	}
        });
//        if(RedisChannelConstant.CHANNEL_CACHE_SYNC.equals(channel)){
//            String[] split = message.split(":");
//            int length=2;
//            if(split.length==length){
//                String type=split[0];
//                //文章分类
//                if(RedisChannelEnum.RECACHE_ARTICLE_CATEGORY.getName().equals(type)){
//                    logger.info("同步文章分类缓存...");
//                    String id=split[1];
//                    ArticleCategoryServiceImpl.refreshCache(Long.valueOf(id));
//                //系统配置
//                }else if(RedisChannelEnum.RECACHE_SYSTEM_CONFIG.getName().equals(type)){
//                    logger.info("同步系统配置缓存...");
//                    String paramName=split[1];
//                    SystemConfigUtils.refreshSystemConfig(paramName);
//                //链接
//                }else if(RedisChannelEnum.RECACHE_LINK.getName().equals(type)){
//                	logger.info("同步链接缓存...");
//                    LinkServiceImpl.onlineLinkCache.refresh(MyConstant.DEFAULT_KEY);
//                //文章es更新	
//                }else if(RedisChannelEnum.ARTICLE_ES_CREATE_OR_UPDATE.getName().equals(type)){
//                	logger.info("文章es更新... ");
//                	ArticleService articleService = SpringContextUtils.getBean(ArticleService.class);
//                	String id = JedisUtils.brpoplpush(MyConstant.ARTICLE_ES_REDIS_LIST, MyConstant.ARTICLE_ES_REDIS_LIST_BAK);
//                	if(StringUtils.isNumeric(id)){
//                		logger.info("更新文章es数据,id={}",id);
//                		articleService.updateArticleEs(Long.parseLong(id));
//                	}
//                }
//            }
//        }
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
