package com.pan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * id生成工具类
 * @author Administrator
 *
 */
public class IdUtils {
	
	private static final Logger logger=LoggerFactory.getLogger(IdUtils.class);

	private static final String GLOBAL_SYSTEM_ID="global_system_id";
	 /**
     * 日期起始点
     */
    private final static long EPOCH=1463108596098L;

    public static long generateId(){
		long value=0;
		try {
			if(!JedisUtils.existsKey(GLOBAL_SYSTEM_ID)){
				//默认值10000
				value=JedisUtils.increaseKey(GLOBAL_SYSTEM_ID, 10000L);
			}else{
				value=JedisUtils.increaseKey(GLOBAL_SYSTEM_ID);
			}
			value=((System.currentTimeMillis() - EPOCH) / 60000+value);
		} catch (Exception e) {
			logger.error("生成id失败",e);
		}
		return value;
	}
}
