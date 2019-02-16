package com.pan.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.pan.common.exception.BusinessException;

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
		jedis.set(key, value);
		jedis.close();
	}
	
	public static void set(byte[] key,byte[] value){
		Jedis jedis = jedisPool.getResource();
		jedis.set(key, value);
		jedis.close();
	}
	
	public static void setExpire(byte[] key,byte[] value, int seconds){
		Jedis jedis = jedisPool.getResource();
		jedis.set(key, value);
		jedis.expire(key, seconds);
		jedis.close();
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
	
	/**
	 * 自减
	 * @param key
	 * @return
	 */
	public static Long decreaseKey(String key){
		Jedis jedis = jedisPool.getResource();
		Long value = jedis.decr(key);
		jedis.close();
		return value;
	} 
	
	public static Long decreaseKey(byte[] key){
		Jedis jedis = jedisPool.getResource();
		Long value = jedis.del(key);
		jedis.close();
		return value;
	}
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long decreaseKey(String key,Long value){
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.decrBy(key, value);
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
			closeJedis(jedis);
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
			closeJedis(jedis);
		}
		return res;
	}
	
	public static Set<String> smembers(String key){
		Jedis jedis = null;
		Set<String> smembers=new HashSet<String>();
		try {
			jedis = jedisPool.getResource();
			smembers = jedis.smembers(key);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
		}
		return smembers;
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
			closeJedis(jedis);
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
			closeJedis(jedis);
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
			closeJedis(jedis);
		}
	}
	
	/**
	 * 返回满足pattern表达式的所有key keys(*) 返回所有的key
	 * 
	 * @param pattern
	 * @return
	 */
	public static Set<String> keys(String pattern) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = jedisPool.getResource();
			res = jedis.keys(pattern);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
		}
		return res;
	}
	
	
	public static Set<byte[]> getByteKeys(byte[] key) {
		Jedis jedis = null;
		Set<byte[]> res = null;
		try {
			jedis = jedisPool.getResource();
			res = jedis.keys(key);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
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
	public static Long setnx(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.setnx(key, value);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
			return 0L;
		} finally {
			closeJedis(jedis);
		}
	}
	
	public static byte[] get(byte[] key){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	public static List<String> mget(String[] keys) {
		Jedis jedis = null;
		List<String> list =new ArrayList<String>();
		try {
			jedis = jedisPool.getResource();
			list= jedis.mget(keys);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
		}
		return list;
	}
	
	public static Long hdel(String key,String... fields) {
		Jedis jedis = null;
		Long res=0L;
		try {
			jedis = jedisPool.getResource();
			res= jedis.hdel(key, fields);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
		}
		return res;
	}
	
	public static String hmset(String key,Map<String, String> hash) {
		Jedis jedis = null;
		String res=null;
		try {
			jedis = jedisPool.getResource();
			res= jedis.hmset(key, hash);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
		}
		return res;
	}
	
	public static String hgset(String key,String field) {
		Jedis jedis = null;
		String res=null;
		try {
			jedis = jedisPool.getResource();
			res= jedis.hget(key, field);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
		}
		return res;
	}
	
	public static Map<String, String> hgetAll(String key) {
		Jedis jedis = null;
		Map<String, String> map=new HashMap<String, String>(20);
		try {
			jedis = jedisPool.getResource();
			map= jedis.hgetAll(key);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
		}
		return map;
	}
	
	public static Long hset(String key,String field,String value) {
		Long res=null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			res= jedis.hset(key, field, value);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
		}
		return res;
	}
	
	public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
	
	public static Boolean hexists(String key,String field) {
		Boolean res=false;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			res= jedis.hexists(key, field);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
			throw new BusinessException("redis hset操作失败 ");
		} finally {
			jedis.close();
		}
		return res;
	}
	
	public static String hget(String key,String field) {
		Jedis jedis = null;
		String res=null;
		try {
			jedis = jedisPool.getResource();
			res = jedis.hget(key, field);
		} catch (Exception e) {
			jedis.close();
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return res;
	}
}
