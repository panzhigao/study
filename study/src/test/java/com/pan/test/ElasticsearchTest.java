package com.pan.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pan.entity.User;
import com.pan.service.EsClientService;
import com.pan.test.base.BaseTest;
import com.pan.vo.PzgVO;




/**
 * @author 作者
 * @version 创建时间：2018年4月3日 上午10:44:00 类说明
 */
public class ElasticsearchTest extends BaseTest{
	
	@Autowired
	private EsClientService esClientService;
	
	@Test
	public void test1(){
		String index="pzg";
		String type="hello";
		User user=new User();
		user.setNickname("毛毛饱满");
		user.setPassword("6744");
		user.setCreateTime(new Date());
		boolean createIndex = esClientService.createIndex(index, type, user);
		System.out.println(createIndex);
	}
	
	@Test
	public void test2(){
		String index="pzg";
		String type="hello";
		PzgVO pzgVO=new PzgVO();
		pzgVO.setNickname("毛77777777777");
		esClientService.matchQuery(index, type, pzgVO);
	}
}
