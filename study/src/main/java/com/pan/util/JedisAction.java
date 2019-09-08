package com.pan.util;

import redis.clients.jedis.Jedis;

/**
 * Created by panzhigao on 2019/9/8.
 */
public interface JedisAction<T> {
    T exec(Jedis jedis);
}
