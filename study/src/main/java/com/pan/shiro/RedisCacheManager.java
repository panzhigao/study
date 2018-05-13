package com.pan.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class RedisCacheManager implements CacheManager{

	@Override
	public <K, V> Cache<K, V> getCache(String arg0) throws CacheException {
		return new RedisCache<K, V>();
	}

}
