package com.pan.task;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pan.service.ArticleService;
import com.pan.util.JedisUtils;
import com.pan.util.RedisLock;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class OperateCountTask {
	
	private static final Logger logger = LoggerFactory.getLogger(OperateCountTask.class);
	
	@Autowired
	private ArticleService articleService;
	
	private final static String LOCK_COMMENT_COUNT = "commentcount";
	
	private final static String LOCK_VIEW_COUNT ="viewcount";
	/**
	 * 每5分钟更新文章数据库评论数
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void updateCommentCount(){
		RedisLock lock=new RedisLock(LOCK_COMMENT_COUNT);
		try {
			if(lock.lock()){
				logger.info("------------------执行定时任务,更新文章数据库评论数------------------");
				Set<String> keys = JedisUtils.keys("comment_count:*");
				for (String string : keys) {
					String articleId=string.substring(14);
					String countStr = JedisUtils.getString(string);
					Integer commentCount=Integer.valueOf(countStr);
					int updateArticleCommentCoumts = articleService.updateArticleCommentCount(articleId, commentCount);
					if(updateArticleCommentCoumts==1){				
						JedisUtils.delete(string);
					}
				}
			}
		} catch (InterruptedException e) {
			logger.error("执行定时任务,更新文章数据库评论数异常",e);
		}finally{
			lock.unlock();
		}
	}
	
	/**
	 * 每2分钟更新文章数据库阅读数
	 */
	@Scheduled(cron = "0 0/2 * * * ?")
	public void updateViewCount(){
		RedisLock lock=new RedisLock(LOCK_VIEW_COUNT);
		try {
			if(lock.lock()){
				logger.info("------------------执行定时任务,更新文章数据库阅读数------------------");
				Set<String> keys = JedisUtils.keys("article_view_count:*");
				for (String string : keys) {
					String articleId=string.substring(19);
					String countStr = JedisUtils.getString(string);
					Integer viewCount=Integer.valueOf(countStr);
					int updateArticleCommentCoumts = articleService.updateArticleViewCount(articleId, viewCount);
					if(updateArticleCommentCoumts==1){				
						JedisUtils.delete(string);
					}
				}
			}
		} catch (InterruptedException e) {
			logger.error("执行定时任务,更新文章数据库阅读数异常",e);
		}finally{
			lock.unlock();
		}
	}
}
