package com.pan.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.pan.service.EsClientService;
import com.pan.test.base.BaseTest;
import com.pan.vo.QueryArticleVO;




/**
 * @author 作者
 * @version 创建时间：2018年4月3日 上午10:44:00 类说明
 */
public class ElasticsearchTest extends BaseTest{
	
	@Autowired
	private EsClientService esClientService;
	
	@Test
	public void test1(){
		QueryArticleVO articleVO=new QueryArticleVO();
		articleVO.setTitle("刚刚");
		long queryCountByParams = esClientService.queryCountByParams("article", "doc", articleVO);
		System.out.println(queryCountByParams+"-----------------");
	}
	
	@Test
	public void test2(){
		
	}
	
	@Test
	public void test3(){
		
	}
}
