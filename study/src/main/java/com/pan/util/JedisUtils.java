package com.pan.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @author Administrator
 *
 */
public class JedisUtils {
	
	private JedisUtils(){
		
	}
	
	private static JedisPool jedisPool;
	
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		JedisUtils.jedisPool = jedisPool;
	}

	public static String getString(String key){
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}
	
	public static void setString(String key,String value){
		Jedis jedis = jedisPool.getResource();
		String set = jedis.set(key, value);
		System.out.println("set:"+set);
	}
	
	/**
	 * 过期时间不能小于0
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public static void setStringExpire(String key,String value,int seconds){
		if(seconds<=0){
			return;
		}
		Jedis jedis = jedisPool.getResource();
		jedis.setex(key, seconds, value);
	}
}
