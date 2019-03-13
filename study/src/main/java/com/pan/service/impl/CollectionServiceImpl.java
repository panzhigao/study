package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pan.service.AbstractBaseService;
import org.apache.commons.collections.CollectionUtils;
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
import com.pan.util.IdUtils;
import com.pan.util.JsonUtils;


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
		Article articleInDb = articleService.getByArticleId(collection.getArticleId());
		if(articleInDb==null){
			throw new BusinessException("文章不存在");
		}
		Collection collectionInDb = findUserCollection(collection.getUserId(), collection.getArticleId());
		if(collectionInDb!=null){
			return;
		}
		collection.setCreateTime(new Date());
		collection.setTitle(articleInDb.getTitle());
		collection.setCollectionId(IdUtils.generateCollectionId());
		collectionMapper.insertSelective(collection);
	}

	@Override
	public void removeCollection(String userId, String articleId) {
		//TODO 增加判断
		collectionMapper.deleteByUserIdAndArticleId(userId, articleId);
	}

	@Override
	public Collection findUserCollection(String userId, String articleId) {
		QueryCollection queryCollection=new QueryCollection();
		queryCollection.setUserId(userId);
		queryCollection.setArticleId(articleId);
		List<Collection> list = collectionMapper.findPageable(queryCollection);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
}
