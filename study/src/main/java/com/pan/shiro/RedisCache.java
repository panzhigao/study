package com.pan.shiro;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pan.util.JedisUtils;
import com.pan.util.SerializeUtils;

public class RedisCache<K, V> implements Cache<K, V> {
	
	private static final Logger logger=LoggerFactory.getLogger(MyRealm.class);
	
	public static final String CACHE_PREFIX = "redis-cache:";
	
	public static final int DEFAULT_SECONDS=3600;
	
	private int seconds;
	
	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	public RedisCache(){
		this.seconds=DEFAULT_SECONDS;
	}
	
	public RedisCache(int seconds){
		this.seconds=seconds;
	}
	
	private byte[] getKey(K key) {
	    if (key instanceof String) {
            String preKey = CACHE_PREFIX + key;
            return preKey.getBytes();
        } else if(key instanceof PrincipalCollection){
        	PrincipalCollection principalCollection=(PrincipalCollection) key;
        	String userId=(String) principalCollection.getPrimaryPrincipal();
            return (CACHE_PREFIX + userId).getBytes();
        }else{
            return SerializeUtils.serialize(key);
        }
	}

	@Override
	public void clear() throws CacheException {

	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K k) throws CacheException {
		logger.debug("从redis读取权限",k);
		byte[] key = getKey(k);
		if (key != null) {
			return ((V) SerializeUtils.deserialize(JedisUtils.get(key)));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		Set<byte[]> byteKeys = JedisUtils.getByteKeys((CACHE_PREFIX+"*").getBytes());
		if (CollectionUtils.isEmpty(byteKeys)) {
        	return Collections.emptySet();
        }
		Set<K> sets=new HashSet<K>();
		for(byte[] b:byteKeys){
			String s=new String(b);
			sets.add(((K)s));
		}
		return sets;
	}

	@Override
	public V put(K k, V v) throws CacheException {
		byte[] key = getKey(k);
		byte[] value = SerializeUtils.serialize(v);
		JedisUtils.setExpire(key, value, seconds);
		return  v;
	}

	@Override
	public V remove(K k) throws CacheException {
		V v = get(k);
		JedisUtils.decreaseKey(getKey(k));
		return v;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Collection<V> values() {
		return null;
	}
}
