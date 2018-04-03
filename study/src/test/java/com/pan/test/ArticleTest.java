package com.pan.test;


import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pan.entity.Article;
import com.pan.service.ArticleService;
import com.pan.test.base.BaseTest;
import com.pan.vo.QueryArticleVO;

/**
 * 
 * @author Administrator
 *
 */
public class ArticleTest extends BaseTest{
	@Autowired
	private ArticleService articleService;
	
	@Test
	public void test1(){
		QueryArticleVO queryArticleVO=new QueryArticleVO();
		queryArticleVO.setType("1");
		List<Article> list = articleService.queryFromEsByCondition(queryArticleVO);
		for (Article article : list) {
			System.out.println(article);
		}
	}
	
	@Test
	public void test2(){
	
	}
}
