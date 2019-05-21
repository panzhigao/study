package com.pan.redissub;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

/**
 * @author panzhigao
 * redis消息订阅
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
