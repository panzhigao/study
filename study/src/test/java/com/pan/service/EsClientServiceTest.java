package com.pan.service;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pan.entity.Article;
import com.pan.test.base.BaseTest;

public class EsClientServiceTest extends BaseTest{
	@Autowired
	private EsClientService esClientService;
	
	@Autowired
	private ArticleService articleService;
	
	@Test
	public void testCreateIndex() {
		Article selectByPrimaryKey = articleService.selectByPrimaryKey(1524252L);
		esClientService.createIndex("article", "test", selectByPrimaryKey);
	}

	@Test
	public void testQueryByParamsWithHightLight() {
	}

	@Test
	public void testQueryCountByParams() {
	}
	
}
