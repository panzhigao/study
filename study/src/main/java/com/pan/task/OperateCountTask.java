package com.pan.task;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.pan.common.constant.PageConstant;
import com.pan.common.enums.ScoreTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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

	/**
	 * 文章评论数锁
	 */
	private final static String LOCK_COMMENT_COUNT = "lock:commentcount";
	/**
	 * 文章阅读数锁
	 */
	private final static String LOCK_VIEW_COUNT = "lock:viewcount";
	/**
	 * 登陆签到锁
	 */
	private final static String LOCK_LOGIN_AND_CHECK_IN = "lock:loginandcheckin";
	/**
	 * 每次扫描的数量
	 */
	private final static int SCAN_COUNT = 1000;
	/**
	 * 过期时间100秒
	 */
	private final static int EXPIRE_TIME = 100;

	private static final String KEY_COMMENT = "comment_count:";

	/**
	 * 每5分钟更新文章数据库评论数
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void updateCommentCount() {
		String value = UUID.randomUUID().toString().split("-")[0];
		if (RedisLock.tryGetDistributedLock(LOCK_COMMENT_COUNT, value, EXPIRE_TIME)) {
			try {
				logger.info("------->>执行定时任务,更新文章数据库评论数start,锁[key={},value={}]", LOCK_COMMENT_COUNT, value);
				List<String> keys = JedisUtils.scan(KEY_COMMENT + "*", SCAN_COUNT);
				for (String string : keys) {
					String articleId = string.substring(KEY_COMMENT.length());
					String countStr = JedisUtils.getString(string);
					Integer commentCount = Integer.valueOf(countStr);
					int updateArticleCommentCoumts = articleService.updateArticleCommentCount(articleId, commentCount);
					if (updateArticleCommentCoumts == 1) {
						JedisUtils.delete(string);
					}
				}
				logger.info("------->>执行定时任务,更新文章数据库评论数end");
			} finally {
				if (RedisLock.releaseDistributedLock(LOCK_COMMENT_COUNT, value)) {
					logger.info("------->>执行定时任务,更新文章数据库评论数,释放锁[key={},value={}]成功", LOCK_COMMENT_COUNT, value);
				} else {
					logger.info("------->>执行定时任务,更新文章数据库评论数,释放锁[key={},value={}]失败", LOCK_COMMENT_COUNT, value);
				}
			}
		}
	}

	private static final String KEY_VIEW = "article_view_count:";

	/**
	 * 每3分钟更新文章数据库阅读数
	 */
	@Scheduled(cron = "0 0/3 * * * ?")
	public void updateViewCount() {
		String value = UUID.randomUUID().toString().split("-")[0];
		if (RedisLock.tryGetDistributedLock(LOCK_VIEW_COUNT, value, EXPIRE_TIME)) {
			try {
				logger.info("------->>执行定时任务,更新文章数据库阅读数start,锁[key={},value={}]", LOCK_VIEW_COUNT, value);
				List<String> keys = JedisUtils.scan(KEY_VIEW + "*", SCAN_COUNT);
				for (String string : keys) {
					String articleId = string.substring(KEY_VIEW.length());
					String countStr = JedisUtils.getString(string);
					Integer viewCount = Integer.valueOf(countStr);
					int updateArticleCommentCoumts = articleService.updateArticleViewCount(articleId, viewCount);
					if (updateArticleCommentCoumts == 1) {
						JedisUtils.delete(string);
					}
				}
				logger.info("------->>执行定时任务,更新文章数据库阅读数end");

			} finally {
				if (RedisLock.releaseDistributedLock(LOCK_VIEW_COUNT, value)) {
					logger.info("------->>执行定时任务,更新文章数据库阅读数,释放锁[key={},value={}]成功", LOCK_VIEW_COUNT, value);
				} else {
					logger.info("------->>执行定时任务,更新文章数据库阅读数,释放锁[key={},value={}]失败", LOCK_VIEW_COUNT, value);
				}
			}
		}
	}

	/**
	 * 每天凌晨0点更新登录天数和签到天数
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void updateLoginAndCheckInCount() {
		String value = UUID.randomUUID().toString().split("-")[0];
		try {
			if (RedisLock.tryGetDistributedLock(LOCK_LOGIN_AND_CHECK_IN, value, EXPIRE_TIME)) {
				logger.info("------->>执行定时任务,更新登录天数和签到天数start,锁[key={},value={}]------", LOCK_LOGIN_AND_CHECK_IN,
						value);
				QueryUserExtension queryUserExtension = new QueryUserExtension();
				int total = userExtensionService.countByParams(queryUserExtension);
				int pageTotal = total % PageConstant.PAGE_SIZE_20 == 0 ? total / PageConstant.PAGE_SIZE_20
						: total / PageConstant.PAGE_SIZE_20 + 1;
				for (int i = 1; i <= pageTotal; i++) {
					queryUserExtension.setPageSize(PageConstant.PAGE_SIZE_20);
					queryUserExtension.setPageNo(i);
					List<UserExtension> resultList = userExtensionService.findPageable(queryUserExtension);
					UserExtension updateExtension = new UserExtension();
					QueryScoreHistory queryScoreHistory = new QueryScoreHistory();
					queryScoreHistory.setScoreDate(new java.sql.Date(DateUtils.getYesterdayDate().getTime()));
					for (UserExtension userExtension : resultList) {
						updateExtension.setUserId(userExtension.getUserId());
						// 查询昨日是否签到
						queryScoreHistory.setUserId(userExtension.getUserId());
						queryScoreHistory.setType(ScoreTypeEnum.CHECK_IN.getCode());
						int checkCount = scoreHistoryService.countByParams(queryScoreHistory);
						// 昨天没有签到,连续签到天数重置为0
						if (checkCount == 0 && userExtension.getContinuousCheckInDays() != 0) {
							updateExtension.setContinuousCheckInDays(0);
						}
						queryScoreHistory.setType(ScoreTypeEnum.LOGIN.getCode());
						int loginCount = scoreHistoryService.countByParams(queryScoreHistory);
						// 昨天没有登录,连续登录天数重置为0
						if (loginCount == 0 && userExtension.getContinuousLoginDays() != 0) {
							updateExtension.setContinuousLoginDays(0);
						}
						if (updateExtension.getContinuousCheckInDays() != null
								|| updateExtension.getContinuousLoginDays() != null) {
							updateExtension.setUpdateTime(new Date());
							userExtensionService.updateByUserId(updateExtension);
						}
						updateExtension.setContinuousCheckInDays(null);
						updateExtension.setContinuousLoginDays(null);
					}
				}
				logger.info("------->>执行定时任务,更新登录天数和签到天数end");
			}
		} finally {
			if (RedisLock.releaseDistributedLock(LOCK_LOGIN_AND_CHECK_IN, value)) {
				logger.info("------->>执行定时任务,更新登录天数和签到天数,释放锁[key={},value={}]成功", LOCK_LOGIN_AND_CHECK_IN, value);
			} else {
				logger.info("------->>执行定时任务,更新登录天数和签到天数,释放锁[key={},value={}]失败", LOCK_LOGIN_AND_CHECK_IN, value);
			}
		}
	}
}
