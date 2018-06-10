package com.pan.service;

import java.util.Map;
import com.pan.entity.ArticleCheck;
import com.pan.query.QueryArticleCheck;

public interface ArticleCheckService {
	
	public Map<String,Object> findByParams(QueryArticleCheck articleCheckVO);
	
	public void addArticleCheck(ArticleCheck articleCheck);
	
	public void passArticleCheck(String id);
	
	public void notPassArticle(String id,String reason);
	
	public ArticleCheck findById(String id);
}
