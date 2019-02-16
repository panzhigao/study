package com.pan.util;

import java.util.Collections;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * 
 *
 */
public class RedisLock {

	private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

	/**
	 * Lock key
	 */
	private String lockKey;
	/**
	 * 锁失效时间，防止死锁无限等待
	 */
	private int expireMillis = 60 * 1000;
	/**
	 * 获取锁时的等待超时时间，防止线程饥饿
	 */
	private int timeoutMillis = 1000;

	private volatile boolean locked = false;

	/**
	 * 1纳秒=0.000001 毫秒
	 */
	private static final long MILLIS_NANO_TIME = 1000000L;

	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	/**
	 * ex秒 px毫秒
	 */
	private static final String SET_WITH_EXPIRE_TIME = "EX";

	private static Random random = new Random();

	public RedisLock(String lockKey) {
		this.lockKey = "lock:" + lockKey;
	}

	public RedisLock(String lockKey, int timeoutMillis) {
		this(lockKey);
		this.timeoutMillis = timeoutMillis;
	}

	public RedisLock(String lockKey, int expireMillis, int timeoutMillis) {
		this(lockKey, timeoutMillis);
		this.expireMillis = expireMillis;
	}

	/**
	 * 获取锁，如果没有获取到锁，则休眠，再重新尝试获取锁，如果超时，则获取锁失败
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized boolean lock() throws InterruptedException {
		final long nano = System.nanoTime();
		final long timeout = timeoutMillis * MILLIS_NANO_TIME;
		while (System.nanoTime() - nano < timeout) {
			long expireTime = System.currentTimeMillis() + expireMillis + 1;
			String expireStr = String.valueOf(expireTime);
			if (this.setNX(lockKey, expireStr)) {
				this.locked = true;
				return true;
			}
			String currentValueStr = this.get(lockKey);
			if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
				String oldValueStr = this.getSet(lockKey, expireStr);
				if (oldValueStr != null && Long.parseLong(oldValueStr) < System.currentTimeMillis()) {
					logger.info("lockKey={},value={}", lockKey, oldValueStr);
					this.locked = true;
					return true;
				}
			}
			// 短暂休眠，避免活锁
			Thread.sleep(5, random.nextInt(30));
		}
		return false;
	}

	public synchronized void unlock() {
		if (locked) {
			delete(lockKey);
			this.locked = false;
		}
	}

	private Long delete(String key) {
		return JedisUtils.delete(key);
	}

	private boolean setNX(final String key, final String value) {
		Long success = JedisUtils.setnx(key, value);
		return success != null && success == 1;
	}

	private String getSet(final String key, final String value) {
		return JedisUtils.getset(key, value);
	}

	private String get(final String key) {
		return JedisUtils.getString(key);
	}

	public String getLockKey() {
		return this.lockKey;
	}

	public boolean isLocked() {
		return this.locked;
	}

	/**
	 * 尝试获取分布式锁
	 * 
	 * @param lockKey
	 *            锁名
	 * @param lockValue
	 *            锁值
	 * @param expireTime
	 *            超期时间，单位秒
	 * @return 是否获取成功
	 */
	public static boolean tryGetDistributedLock(String lockKey, String lockValue, int expireTime) {
		Jedis jedis = null;
		try {
			jedis = JedisUtils.getJedis();
			String result = jedis.set(lockKey, lockValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
			if (LOCK_SUCCESS.equals(result)) {
				return true;
			}
		} catch (Exception e) {
			logger.error("获取分布式锁失败,");
		} finally {
			JedisUtils.closeJedis(jedis);
		}
		return false;
	}

	private static final Long RELEASE_SUCCESS = 1L;

	/**
	 * 释放分布式锁
	 * 
	 * @param lockKey
	 *            锁名
	 * @param lockValue
	 *            锁值
	 * @return 是否释放成功
	 */
	public static boolean releaseDistributedLock(String lockKey, String lockValue) {
		Jedis jedis = null;
		try {
			jedis = JedisUtils.getJedis();
			String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
			Object result = jedis.eval(script, Collections.singletonList(lockKey),
					Collections.singletonList(lockValue));
			if (RELEASE_SUCCESS.equals(result)) {
				return true;
			}
		} catch (Exception e) {
			logger.error("释放分布式锁失败,lockKey={},lockValue={}", lockKey, lockValue, e);
		} finally {
			JedisUtils.closeJedis(jedis);
		}
		return false;
	}
}
