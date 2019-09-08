package com.pan.util;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * redis消息发送
 * @author panzhigao
 */
@Slf4j
public class Publisher {

    /**
     * 发送redis订阅消息
     * @param channel 消息频道
     * @param message 消息
     * @return
     */
    public static boolean sendMessage(String channel,String message){
        Jedis jedis=null;
        try {
            jedis= JedisUtils.getJedis();
            Long successCount = jedis.publish(channel, message);
            log.info("----->>>redis发送消息成功，channel={},message={},接收成功客户端数={}",channel,message,successCount);
            return true;
        }catch (Exception e){
            log.error("----->>>redis发布消息失败，channel={},message={}",channel,message,e);
        }finally {
            JedisUtils.closeJedis(jedis);
        }
        return false;
    }

}
