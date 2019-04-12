package com.pan.init;

import com.pan.common.constant.RedisChannelConstant;
import com.pan.util.JedisUtils;
import com.pan.util.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import redis.clients.jedis.Jedis;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author panzhigao
 */
public class InitTheContext implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(InitTheContext.class);

	ExecutorService threadPool=new ThreadPoolExecutor(0, 10,
			60L, TimeUnit.SECONDS,
			new SynchronousQueue<Runnable>());

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("spring容器onApplicationEvent。。。。");
		cacheSync();
	}

	/**
	 * 缓存同步
	 */
	private void cacheSync(){
		threadPool.execute(()->{
			Jedis jedis=null;
			boolean redisState=true;
			while(true){
				try {
					if(!redisState){
						logger.info("----->>>redis 重连。。。。");
					}
					jedis= JedisUtils.getJedis();
					if(!redisState && jedis !=null && JedisUtils.REDIS_CONNECT_SUCCESS.equals(jedis.ping())){
						logger.info("----->>>redis 重连成功。。。");
						redisState=true;
					}
					jedis.subscribe(new Subscriber(), RedisChannelConstant.CHANNEL_CACHE_SYNC);
				}catch (Exception e){
					redisState=false;
					logger.error("----->>>redis subscribe 发生异常",e);
				}
				try {
					JedisUtils.closeJedis(jedis);
				}catch (Exception e){
					logger.error("----->>>关闭redis连接失败",e);
				}
				if(!redisState){
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e1) {
						logger.error("----->>>redis subscribe 线程异常",e1);
					}
				}
			}
		});
	}
}
