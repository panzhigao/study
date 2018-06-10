package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class CollectionServiceImpl implements CollectionService {

	private static final Logger logger = LoggerFactory.getLogger(CollectionServiceImpl.class);
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CollectionMapper collectionMapper;
	
	@Override
	public Map<String, Object> findByParams(QueryCollection queryCollection) {
		Map<String,Object> pageData=new HashMap<String, Object>(2);
		List<Collection> list = new ArrayList<Collection>();
		try {
			logger.info("分页查询收藏参数为:{}", JsonUtils.toJson(queryCollection));
			list = collectionMapper.findByParams(queryCollection);
			pageData.put("data", list);
			int total=collectionMapper.getCountByParams(queryCollection);
			pageData.put("total", total);
			pageData.put("code", "200");
			pageData.put("msg", "");
		} catch (Exception e) {
			logger.error("分页收藏文章异常", e);
		}
		return pageData;
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
		collectionMapper.addCollection(collection);
	}

	@Override
	public void removeCollection(String userId, String articleId) {
		//TODO 增加判断
		collectionMapper.deleteByUserIdAndArticleId(userId, articleId);
	}

	@Override
	public Collection findUserCollection(String userId, String articleId) {
		Map<String,Object> params=new HashMap<String, Object>(2);
		params.put("userId", userId);
		params.put("articleId", articleId);
		Collection collection = collectionMapper.findUserCollection(userId, articleId);
		return collection;
	}

	@Override
	public int getCount(QueryCollection queryCollectionVO) {
		return collectionMapper.getCountByParams(queryCollectionVO);
	}	
}
