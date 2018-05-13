package com.pan.shiro;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import com.pan.entity.User;
import com.pan.util.JedisUtils;
import com.pan.util.SerializeUtils;

public class RedisCache<K, V> implements Cache<K, V> {

	public static final String CACHE_PREFIX = "redis-cache:";

	private byte[] getKey(K key) {
	    if (key instanceof String) {
            String preKey = CACHE_PREFIX + key;
            return preKey.getBytes();
        } else if(key instanceof PrincipalCollection){
        	PrincipalCollection principalCollection=(PrincipalCollection) key;
        	User user=(User)principalCollection.getPrimaryPrincipal();
            return (CACHE_PREFIX + user.getUserId()).getBytes();
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
		System.out.println("从redis读取权限");
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
			System.out.println(s);
			sets.add(((K)s));
		}
		return sets;
	}

	@Override
	public V put(K k, V v) throws CacheException {
		byte[] key = getKey(k);
		byte[] value = SerializeUtils.serialize(v);
		JedisUtils.setExpire(key, value, 3600);
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
