package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.common.exception.BusinessException;
import com.pan.entity.Article;
import com.pan.mapper.ArticleMapper;
import com.pan.service.ArticleService;
import com.pan.util.IdUtils;
import com.pan.util.JsonUtils;

/**
 * 
 * @author Administrator
 * 
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Autowired
	private ArticleMapper articleMapper;
	
	
	/**
	 * 校验当前操作码状态是否正常
	 * 1-草稿，2-审核中
	 * @param status
	 * @return
	 */
	private boolean checkOpearteStatus(String status){
		if(Article.STATUS_SKETCH.equals(status)||Article.STATUS_IN_REVIEW.equals(status)){
			return true;
		}
		return false;
	}
	
	/**
	 * 校验文章信息
	 * 
	 * @param article
	 */
	private void checkArticle(Article article) {
		if (StringUtils.isBlank(article.getUserId())) {
			logger.error("用户信息有误");
			throw new BusinessException("用户信息有误,请重新登陆");
		}
		if (StringUtils.isBlank(article.getTitle())) {
			logger.error("文章标题不能为空");
			throw new BusinessException("文章标题不能为空");
		}
		if (StringUtils.isBlank(article.getContent())) {
			logger.error("文章内容不能为空");
			throw new BusinessException("文章内容不能为空");
		}
		if (StringUtils.isBlank(article.getOutline())) {
			logger.error("文章概要不能为空");
			throw new BusinessException("文章内容不能为空");
		}
		if(!checkOpearteStatus(article.getStatus())){
			logger.error("html页面被修改,方法参数错误");
			throw new BusinessException("文章状态有误,请刷新页面");
		}
	}

	/**
	 * 新增文章
	 */
	public void saveArticle(Article article) {
		checkArticle(article);
		// 默认草稿状态
		if (StringUtils.isBlank(article.getStatus())) {
			logger.info("文章无状态，默认设置为草稿状态");
			article.setStatus(Article.STATUS_SKETCH);
		}
		article.setCreateTime(new Date());
		article.setArticleId(IdUtils.generateArticleId());
		articleMapper.saveArticle(article);
	}

	public List<Article> findListByUserId(String userId) {
		logger.info("用户id为:{}", userId);
		return articleMapper.findListByUserId(userId);
	}

	public List<Article> findByParams(Map<String, Object> params) {
		List<Article> list = new ArrayList<Article>();
		try {
			logger.info("分页查询文章参数为:{}", JsonUtils.toJson(params));
			String userId=(String) params.get("userId");
			if(StringUtils.isBlank(userId)){
				logger.info("用户id有误",userId);
				return list;
			}
			list = articleMapper.findByParams(params);
		} catch (Exception e) {
			logger.error("分页查询文章异常", e);
		}
		return list;
	}

	public Article getByUserIdAndArticleId(String userId, String articleId) {
		logger.info("查询文章信息,用户id为:{},文章id为:{}", userId, articleId);
		if(StringUtils.isAllBlank(userId)||StringUtils.isBlank(articleId)){
			logger.info("查询文章详细信息参数有误,用户id为:{},文章id为:{}", userId, articleId);
		}
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("userId", userId);
		params.put("articleId", articleId);
		List<Article> list = findByParams(params);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	


	/**
	 * 更新文章信息 要校验当前文章是否是当前登录用户下的文章
	 */
	public void updateArticle(Article article) {
		logger.info("前台传来的文章信息,{}",article);
		//校验前台传来的数据
		checkArticle(article);
		String articleId = article.getArticleId();
		Article articleInDb = articleMapper.findByArticleId(articleId);
		if (articleInDb == null) {
			logger.error("根据文章id未查询到数据,articleId:{}", articleId);
			throw new BusinessException("修改文章信息有误，文章已不存在");
		}
		if(Article.STATUS_IN_REVIEW.equals(articleInDb.getStatus())){
			logger.error("当前文章处于审核状态,不可修改", articleInDb);
			throw new BusinessException("当前文章处于审核状态,不可修改");
		}
		if(Article.STATUS_PUBLISHED.equals(articleInDb.getStatus())){
			logger.error("当前文章处于发布状态,不可修改", articleInDb);
			throw new BusinessException("当前文章处于发布状态,不可修改");
		}
		String userIdInDb = articleInDb.getUserId();
		// 判断当前文章是当前登录用户下的文章
		if (!StringUtils.equals(article.getUserId(), userIdInDb)) {
			logger.error("文章用户id有误,不是当前用户的文章,登录用户id:{},数据库中用户id:{}",article.getUserId(), articleInDb.getUserId());
			throw new BusinessException("修改文章信息失败，文章已不存在，请返回文章列表页");
		}
		try {
			article.setUpdateTime(new Date());
			articleMapper.updateArticle(article);
		} catch (Exception e) {
			logger.error("修改文章失败", e);
			throw new BusinessException("修改文章信息失败,请稍后");
		}
	}
}
