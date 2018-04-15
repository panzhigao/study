package com.pan.util;

import java.util.Random;

/**
 * 
 * @author panzhigao-wb
 *
 */
public class RedisLock {

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
    private int timeoutMillis = 10 * 1000;

    private volatile boolean locked = false;
    
    /**
     * 1纳秒=0.000001 毫秒
     */
    private static final long MILLIS_NANO_TIME = 100000000L;

    private static Random random = new Random();

    public RedisLock(String lockKey) {
        this.lockKey = "lock:"+lockKey ;
    }

    public RedisLock(String lockKey, int timeoutMillis) {
        this(lockKey);
        this.timeoutMillis = timeoutMillis;
    }

    public RedisLock(String lockKey, int expireMillis, int timeoutMillis) {
        this(lockKey, timeoutMillis);
        this.expireMillis = expireMillis;
    }
    
   
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
                if (oldValueStr != null && Long.parseLong(oldValueStr)<System.currentTimeMillis()) {
                	System.out.println("oldValueStr");
                    this.locked = true;
                    return true;
                }
            }
            //短暂休眠，避免活锁
            Thread.sleep(5, random.nextInt(30));
        }
        return false;
    }
        
    public synchronized void unlock() {
        if (locked) {
        	System.out.println("dellockKey");
            Long delete = delete(lockKey);
            System.out.println("deletenum:"+delete);
            this.locked = false;
            System.out.println("dddd");
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
    public boolean isLocked(){
    	return this.locked;
    }
}
