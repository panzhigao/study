package com.pan.service.impl;

import java.util.Date;
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
public class ArticleServiceImpl implements ArticleService{
	
	private static final Logger logger=LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	@Autowired
	private ArticleMapper articleMapper;
	
	public void saveArticle(Article article) {
		if(StringUtils.isBlank(article.getUserId())){
			logger.error("用户信息有误");
			throw new BusinessException("用户信息有误");
		}
		if(StringUtils.isBlank(article.getTitle())){
			logger.error("文章标题不能为空");
			throw new BusinessException("文章标题不能为空");
		}
		if(StringUtils.isBlank(article.getContent())){
			logger.error("文章内容不能为空");
			throw new BusinessException("文章内容不能为空");
		}
		//默认草稿状态
		if(StringUtils.isBlank(article.getStatus())){
			logger.info("文章无状态，默认设置为草稿状态");
			article.setStatus(Article.STATUS_SKETCH);
		}
		article.setCreateTime(new Date());
		article.setArticleId(IdUtils.generateArticleId());
		articleMapper.saveArticle(article);
	}


	public List<Article> findListByUserId(String userId) {
		logger.info("用户id为:{}",userId);
		return articleMapper.findListByUserId(userId);
	}


	public List<Article> findByParams(Map<String, Object> params) {
		List<Article> list=null;
		try {			
			logger.info("分页查询文章参数为:{}",JsonUtils.toJson(params));
			list=articleMapper.findByParams(params);
		} catch (Exception e) {
			logger.error("分页查询文章异常",e);
		}
		return list;
	}
}
