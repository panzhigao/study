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
		jedis.close();
		System.out.println("set:"+set);
	}
	
	/**
	 * 设置值的时候同时设置过期时间,过期时间不能小于0
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
		jedis.close();
	}
	
	/**
	 * 设置过期时间,当为-1时，不过期
	 * @param key
	 */
	public static Long expire(String key,int seconds){
		Jedis jedis = jedisPool.getResource();
		Long resultCounts = jedis.expire(key,seconds);
		jedis.close();
		return resultCounts;
	}
	
	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public static boolean existsKey(String key){
		if(key==null){
			return false;
		}
		Jedis jedis = jedisPool.getResource();
		boolean flag=jedis.exists(key);
		jedis.close();
		return flag;
	}
	
	/**
	 * 自增key值,默认1
	 * @param key
	 * @return
	 */
	public static Long increaseKey(String key){
		Jedis jedis = jedisPool.getResource();
		Long value = jedis.incr(key);
		jedis.close();
		return value;
	} 
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long increaseKey(String key,Long value){
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incrBy(key, value);
		jedis.close();
		return result;
	} 
	
	public static Long sadd(String key, String... members) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = jedisPool.getResource();
			res = jedis.sadd(key, members);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return res;
	}
	
	public static Long scard(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = jedisPool.getResource();
			res = jedis.scard(key);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return res;
	}
}
