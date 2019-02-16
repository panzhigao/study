package com.pan.task;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pan.entity.ScoreHistory;
import com.pan.entity.UserExtension;
import com.pan.query.QueryScoreHistory;
import com.pan.query.QueryUserExtension;
import com.pan.service.ArticleService;
import com.pan.service.ScoreHistoryService;
import com.pan.service.UserExtensionService;
import com.pan.util.DateUtils;
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

	@Autowired
	private UserExtensionService userExtensionService;

	@Autowired
	private ScoreHistoryService scoreHistoryService;

	private final static String LOCK_COMMENT_COUNT = "lock:commentcount";

	private final static String LOCK_VIEW_COUNT = "lock:viewcount";

	private final static String LOCK_LOGIN_AND_CHECK_IN = "lock:loginandcheckin";

	/**
	 * 每5分钟更新文章数据库评论数
	 */
//	@Scheduled(cron = "0 0/5 * * * ?")
//	public void updateCommentCount() {
//		logger.info("执行定时任务,更新文章数据库评论数start");
//		Set<String> keys = JedisUtils.keys("comment_count:*");
//		for (String string : keys) {
//			String articleId = string.substring(14);
//			String countStr = JedisUtils.getString(string);
//			Integer commentCount = Integer.valueOf(countStr);
//			int updateArticleCommentCoumts = articleService.updateArticleCommentCount(articleId, commentCount);
//			if (updateArticleCommentCoumts == 1) {
//				JedisUtils.delete(string);
//			}
//		}
//		logger.info("执行定时任务,更新文章数据库评论数end");
////		String value = UUID.randomUUID().toString();
////		try {
////			if (RedisLock.tryGetDistributedLock(LOCK_COMMENT_COUNT, value, 10000)) {
////				logger.info("执行定时任务,更新文章数据库评论数start,锁[key={},value={}]", LOCK_COMMENT_COUNT, value);
////				Set<String> keys = JedisUtils.keys("comment_count:*");
////				for (String string : keys) {
////					String articleId = string.substring(14);
////					String countStr = JedisUtils.getString(string);
////					Integer commentCount = Integer.valueOf(countStr);
////					int updateArticleCommentCoumts = articleService.updateArticleCommentCount(articleId, commentCount);
////					if (updateArticleCommentCoumts == 1) {
////						JedisUtils.delete(string);
////					}
////				}
////				logger.info("执行定时任务,更新文章数据库评论数end");
////			}
////		} finally {
////			if (RedisLock.releaseDistributedLock(LOCK_COMMENT_COUNT, value)) {
////				logger.info("执行定时任务,更新文章数据库评论数,释放锁[key={},value={}]成功", LOCK_COMMENT_COUNT, value);
////			} else {
////				logger.info("执行定时任务,更新文章数据库评论数,释放锁[key={},value={}]失败", LOCK_COMMENT_COUNT, value);
////			}
////		}
//	}

	/**
	 * 每2分钟更新文章数据库阅读数
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	public void updateViewCount() {
//		Set<String> keys = JedisUtils.keys("article_view_count:*");
//		for (String string : keys) {
//			String articleId = string.substring(19);
//			String countStr = JedisUtils.getString(string);
//			Integer viewCount = Integer.valueOf(countStr);
//			int updateArticleCommentCoumts = articleService.updateArticleViewCount(articleId, viewCount);
//			if (updateArticleCommentCoumts == 1) {
//				JedisUtils.delete(string);
//			}
//		}
//		logger.info("------------------执行定时任务,更新文章数据库阅读数end------------------");
		String value = UUID.randomUUID().toString().split("-")[0];
		if (RedisLock.tryGetDistributedLock(LOCK_VIEW_COUNT, value, 100)) {
			try {
				logger.info("-------执行定时任务,更新文章数据库阅读数start,锁[key={},value={}]------", LOCK_VIEW_COUNT, value);
				Set<String> keys = JedisUtils.keys("article_view_count:*");
				for (String string : keys) {
					String articleId = string.substring(19);
					String countStr = JedisUtils.getString(string);
					Integer viewCount = Integer.valueOf(countStr);
					int updateArticleCommentCoumts = articleService.updateArticleViewCount(articleId, viewCount);
					if (updateArticleCommentCoumts == 1) {
						JedisUtils.delete(string);
					}
				}
				logger.info("------------------执行定时任务,更新文章数据库阅读数end------------------");

			} finally {
				if (RedisLock.releaseDistributedLock(LOCK_VIEW_COUNT, value)) {
					logger.info("执行定时任务,更新文章数据库阅读数,释放锁[key={},value={}]成功", LOCK_VIEW_COUNT, value);
				} else {
					logger.info("执行定时任务,更新文章数据库阅读数,释放锁[key={},value={}]失败", LOCK_VIEW_COUNT, value);
				}
			}
		}
	}

	private static final int PAGE_SIZE = 2;

	/**
	 * 每天凌晨0点更新登录天数和签到天数
	 */
//	@Scheduled(cron = "0 0 0 * * ?")
//	public void updateLoginAndCheckInCount() {
//		QueryUserExtension queryUserExtensionVO = new QueryUserExtension();
//		int total = userExtensionService.countByParams(queryUserExtensionVO);
//		int pageTotal = total % PAGE_SIZE == 0 ? total / PAGE_SIZE : total / PAGE_SIZE + 1;
//		for (int i = 1; i <= pageTotal; i++) {
//			queryUserExtensionVO.setPageSize(PAGE_SIZE);
//			queryUserExtensionVO.setPageNo(i);
//			List<UserExtension> resultList = userExtensionService.findByParams(queryUserExtensionVO);
//			UserExtension updateExtension = new UserExtension();
//			QueryScoreHistory historyVO = new QueryScoreHistory();
//			historyVO.setScoreDate(DateUtils.getYesterdayDate());
//			for (UserExtension userExtension : resultList) {
//				updateExtension.setUserId(userExtension.getUserId());
//				// 查询昨日是否签到
//				historyVO.setUserId(userExtension.getUserId());
//				historyVO.setType(ScoreHistory.ScoreType.CHECK_IN.getCode());
//				int checkCount = scoreHistoryService.getCountByParams(historyVO);
//				if (checkCount == 0 && userExtension.getContinuousCheckInDays() != 0) {// 昨天没有签到,连续签到天数重置为0
//					updateExtension.setContinuousCheckInDays(0);
//				}
//				historyVO.setType(ScoreHistory.ScoreType.LOGIN.getCode());
//				int loginCount = scoreHistoryService.getCountByParams(historyVO);
//				if (loginCount == 0 && userExtension.getContinuousLoginDays() != 0) {// 昨天没有登录,连续登录天数重置为0
//					updateExtension.setContinuousLoginDays(0);
//				}
//				if (updateExtension.getContinuousCheckInDays() != null
//						|| updateExtension.getContinuousLoginDays() != null) {
//					updateExtension.setUpdateTime(new Date());
//					userExtensionService.updateByUserId(updateExtension);
//				}
//				updateExtension.setContinuousCheckInDays(null);
//				updateExtension.setContinuousLoginDays(null);
//			}
//		}
//		logger.info("------------------执行定时任务,更新登录天数和签到天数end------------------");
////		String value = UUID.randomUUID().toString();
////		try {
////			if (RedisLock.tryGetDistributedLock(LOCK_LOGIN_AND_CHECK_IN, value, 10000)) {
////				logger.info("------执行定时任务,更新登录天数和签到天数start,锁[key={},value={}]------", LOCK_LOGIN_AND_CHECK_IN, value);
////				QueryUserExtension queryUserExtensionVO = new QueryUserExtension();
////				int total = userExtensionService.countByParams(queryUserExtensionVO);
////				int pageTotal = total % PAGE_SIZE == 0 ? total / PAGE_SIZE : total / PAGE_SIZE + 1;
////				for (int i = 1; i <= pageTotal; i++) {
////					queryUserExtensionVO.setPageSize(PAGE_SIZE);
////					queryUserExtensionVO.setPageNo(i);
////					List<UserExtension> resultList = userExtensionService.findByParams(queryUserExtensionVO);
////					UserExtension updateExtension = new UserExtension();
////					QueryScoreHistory historyVO = new QueryScoreHistory();
////					historyVO.setScoreDate(DateUtils.getYesterdayDate());
////					for (UserExtension userExtension : resultList) {
////						updateExtension.setUserId(userExtension.getUserId());
////						// 查询昨日是否签到
////						historyVO.setUserId(userExtension.getUserId());
////						historyVO.setType(ScoreHistory.ScoreType.CHECK_IN.getCode());
////						int checkCount = scoreHistoryService.getCountByParams(historyVO);
////						if (checkCount == 0 && userExtension.getContinuousCheckInDays() != 0) {// 昨天没有签到,连续签到天数重置为0
////							updateExtension.setContinuousCheckInDays(0);
////						}
////						historyVO.setType(ScoreHistory.ScoreType.LOGIN.getCode());
////						int loginCount = scoreHistoryService.getCountByParams(historyVO);
////						if (loginCount == 0 && userExtension.getContinuousLoginDays() != 0) {// 昨天没有登录,连续登录天数重置为0
////							updateExtension.setContinuousLoginDays(0);
////						}
////						if (updateExtension.getContinuousCheckInDays() != null
////								|| updateExtension.getContinuousLoginDays() != null) {
////							updateExtension.setUpdateTime(new Date());
////							userExtensionService.updateByUserId(updateExtension);
////						}
////						updateExtension.setContinuousCheckInDays(null);
////						updateExtension.setContinuousLoginDays(null);
////					}
////				}
////				logger.info("------------------执行定时任务,更新登录天数和签到天数end------------------");
////			}
////		} finally {
////			if (RedisLock.releaseDistributedLock(LOCK_LOGIN_AND_CHECK_IN, value)) {
////				logger.info("执行定时任务,更新登录天数和签到天数,释放锁[key={},value={}]成功", LOCK_LOGIN_AND_CHECK_IN, value);
////			} else {
////				logger.info("执行定时任务,更新登录天数和签到天数,释放锁[key={},value={}]失败", LOCK_LOGIN_AND_CHECK_IN, value);
////			}
////		}
//	}
}
