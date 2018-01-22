package com.pan.util;


import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

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
	
	private static final String REDIS_ERROR="Could not get a resource from the pool";
	
	public void setJedisPool(JedisPool jedisPool) {
		JedisUtils.jedisPool = jedisPool;
		Jedis jedis=null;
        try {
        	jedis = getJedisPool().getResource();
        } catch (JedisConnectionException e) {
        	String message = StringUtils.trim(e.getMessage());
        	if(REDIS_ERROR.equalsIgnoreCase(message)){
        		System.out.println("++++++++++reids服务启动失败++++++++");
        		System.out.println("++++++++++请检查你的redis服务++++++++");
        		//停止项目
        		System.exit(0);
        	}
        	throw new JedisConnectionException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally{
        	if(jedis!=null){
        		jedis.close();
        	}
        }
	}
	
	public static Jedis getJedis(){
		return jedisPool.getResource();
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
	
	/**
	 * 设置key的值,并返回一个旧值
	 * 
	 * @param key
	 * @param value
	 * @return 旧值 如果key不存在 则返回null
	 */
	public static String getset(String key, String value) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = jedisPool.getResource();
			res = jedis.getSet(key, value);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return res;
	}
	
	/**
	 * 设置key value,如果key已经存在则返回0,nx==> not exist
	 * 
	 * @param key
	 * @param value
	 * @return 成功返回1 如果存在 和 发生异常 返回 0
	 */
	public static Long setnx(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.setnx(key, value);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
			return 0L;
		} finally {
			jedis.close();
		}
	}
	
	/**
	 * 删除指定的key,也可以传入一个包含key的数组
	 * 
	 * @param keys
	 *            一个key 也可以使 string 数组
	 * @return 返回删除成功的个数
	 */
	public static Long delete(String... keys) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.del(keys);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
			return 0L;
		} finally {
			jedis.close();
		}
	}
}
