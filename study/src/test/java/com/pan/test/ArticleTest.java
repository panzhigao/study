package com.pan.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.pan.entity.Article;
import com.pan.mapper.ArticleMapper;
import com.pan.test.base.BaseTest;

/**
 * 
 * @author Administrator
 *
 */
public class ArticleTest extends BaseTest{
	@Autowired
	private ArticleMapper articleMapper;
	
	@Test
	public void test1(){
		Article article = articleMapper.findByArticleId("1");
		System.out.println(article);
	}

}
