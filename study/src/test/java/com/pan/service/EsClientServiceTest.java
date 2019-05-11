package com.pan.service;


import java.io.IOException;
import java.util.Map;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.InnerHitBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.join.query.HasChildQueryBuilder;
import org.elasticsearch.join.query.HasParentQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.pan.common.constant.EsConstant;
import com.pan.entity.Article;
import com.pan.service.impl.ArticleServiceImpl;
import com.pan.test.base.BaseTest;

public class EsClientServiceTest extends BaseTest{
	
	@Autowired
	private RestHighLevelClient client;
	
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
	
	/**
	 * 搜索文章
	 */
	@Test
	public void testHasParent(){
		SearchRequest request = new SearchRequest("es_data");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		MatchQueryBuilder mat=new MatchQueryBuilder("title","林萌萌");
		HasParentQueryBuilder builder=new HasParentQueryBuilder("user",mat,true);
		searchSourceBuilder.query(builder);
		request.source(searchSourceBuilder);
		try {
			SearchResponse search = client.search(request);
			SearchHits hits = search.getHits();
			SearchHit[] hits2 = hits.getHits();
			for(SearchHit s:hits2){
				System.out.println(s.getSourceAsString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 搜索用户
	 */
	@Test
	public void testHasChild(){
		MatchQueryBuilder mat=new MatchQueryBuilder("user_id","123");
		HasChildQueryBuilder builder=new HasChildQueryBuilder("article",mat,ScoreMode.Avg);
		builder.innerHit(new InnerHitBuilder());
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(builder);
		SearchRequest request = new SearchRequest("article_index");
		request.source(searchSourceBuilder);
		try {
			SearchResponse search = client.search(request);
			SearchHits hits = search.getHits();
			SearchHit[] hits2 = hits.getHits();
			for(SearchHit s:hits2){
				System.out.println(s.getInnerHits());
				System.out.println(s.getSourceAsString());
				Map<String, SearchHits> innerHits = s.getInnerHits();
				for(Map.Entry<String, SearchHits> entry : innerHits.entrySet()){
					SearchHits value = entry.getValue();
					SearchHit[] hits3 = value.getHits();
					for(SearchHit s2:hits3){
						System.out.println(s2.getSourceAsString());
					}
				}
				System.out.println(s.getSourceAsString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
