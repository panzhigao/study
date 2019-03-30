package com.pan.service.impl;

import java.util.Date;
import com.pan.service.AbstractBaseService;
import com.pan.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.exception.BusinessException;
import com.pan.entity.Article;
import com.pan.entity.Collection;
import com.pan.mapper.CollectionMapper;
import com.pan.query.QueryCollection;
import com.pan.service.ArticleService;
import com.pan.service.CollectionService;


/**
 * 
 * @author Administrator
 * 
 */
@Service
public class CollectionServiceImpl extends AbstractBaseService<Collection,CollectionMapper> implements CollectionService {

	private static final Logger logger = LoggerFactory.getLogger(CollectionServiceImpl.class);

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CollectionMapper collectionMapper;

	@Override
	protected CollectionMapper getBaseMapper() {
		return collectionMapper;
	}


	@Override
	public void addCollection(Collection collection) {
		ValidationUtils.validateEntity(collection);
		Article articleInDb = articleService.selectByPrimaryKey(collection.getArticleId());
		if(articleInDb==null){
			throw new BusinessException("文章不存在");
		}
		boolean collected = checkArticleCollected(collection.getUserId(), collection.getArticleId());
		if(collected){
			logger.info("该文章已收藏");
			return;
		}
		collection.setCreateTime(new Date());
		collection.setTitle(articleInDb.getTitle());
		collectionMapper.insertSelective(collection);
	}

	/**
	 * 取消收藏
	 * @param userId
	 * @param id 文章id
	 */
	@Override
	public void removeCollection(Long userId,Long id) {
		Collection collection = collectionMapper.selectByPrimaryKey(id);
		if(collection==null){
			logger.error("根据id={}，未查询到收藏信息",id);
			throw new BusinessException("该收藏信息不存在");
		}
		if(userId.equals(collection.getUserId())){
			logger.error("被取消的收藏用户id={},当前登录人userId={}，两者不一致，不能取消非当前登录人的收藏，收藏id={}",collection.getUserId(),userId,id);
			throw new BusinessException("只能取消自己的收藏");
		}
		collectionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public boolean checkArticleCollected(Long userId, Long articleId) {
		QueryCollection queryCollection=new QueryCollection();
		queryCollection.setUserId(userId);
		queryCollection.setArticleId(articleId);
		int i = collectionMapper.countByParams(queryCollection);
		return i>0;
	}
}
