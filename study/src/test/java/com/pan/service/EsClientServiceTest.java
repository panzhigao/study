package com.pan.service;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pan.common.constant.EsConstant;
import com.pan.entity.Article;
import com.pan.service.impl.ArticleServiceImpl;
import com.pan.test.base.BaseTest;

public class EsClientServiceTest extends BaseTest{
	@Autowired
	private EsClientService esClientService;
	
	@Autowired
	private ArticleService articleService;
	
	@Test
	public void testCreateIndex() {
		Article selectByPrimaryKey = articleService.selectByPrimaryKey(1524252L);
		esClientService.createIndex("article2", "test", selectByPrimaryKey);
	}

	@Test
	public void testQueryByParamsWithHightLight() {
	}

	@Test
	public void testQueryCountByParams() {
	}
	
	@Test
	public void testDeleteRecord(){
		boolean deleteRecord = esClientService.deleteRecord(EsConstant.ES_ARTICLE,ArticleServiceImpl.TYPE_NAME, "nctAeWoBC-3gjC8hyB8Y");
		Assert.assertTrue(deleteRecord);
	}
	
}
