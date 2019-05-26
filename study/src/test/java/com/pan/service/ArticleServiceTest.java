package com.pan.service;


import com.pan.entity.Article;
import org.junit.Test;
import com.pan.test.base.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleServiceTest extends BaseTest {

	@Autowired
	private IArticleService articleService;

	@Test
	public void saveArticle() throws Exception {
		Article article=new Article();
		article.setTitle("123");
		article.setContent("22222");
		articleService.saveArticle(article);
	}

	@Test
	public void findListByUserId() throws Exception {
	}

	@Test
	public void getAndCheckByUserId() throws Exception {
	}

	@Test
	public void updateArticle() throws Exception {
	}

	@Test
	public void getAndCheckByArticleId() throws Exception {
	}

	@Test
	public void deleteArticle() throws Exception {
	}

	@Test
	public void getCount() throws Exception {
	}

	@Test
	public void updateArticleCommentCount() throws Exception {
	}

	@Test
	public void updateArticleViewCount() throws Exception {
	}

	@Test
	public void saveSystemMessage() throws Exception {
	}

	@Test
	public void queryFromEsByCondition() throws Exception {
	}

	@Test
	public void searchArticleByTitle() throws Exception {
	}

	@Test
	public void getMaxStick() throws Exception {
	}

	@Test
	public void setArticle() throws Exception {
	}

	@Test
	public void checkAndGetArticle() throws Exception {
	}

	@Test
	public void testupdateArticleEs() {
		articleService.updateArticleEs(1571809L);
	}
	
	@Test
	public void testSyncArticleEsData(){
		articleService.syncArticleEsData();
	}

}
