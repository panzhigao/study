package com.pan.mapper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pan.entity.ArticleCheck;
import com.pan.test.base.BaseTest;

public class ArticleCheckMapperTest extends BaseTest {
	
	@Autowired
	private ArticleCheckMapper articleCheckMapper;
	
	@Test
	public void testFindVOByParams() {
		
	}

	@Test
	public void testSelectByPrimaryKey() {
		
	}

	@Test
	public void testDeleteByPrimaryKey() {
		int deleteByPrimaryKey = articleCheckMapper.deleteByPrimaryKey(-2L);
		assertEquals(deleteByPrimaryKey, 0);
	}

	@Test
	public void testInsertSelective() {
		
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		ArticleCheck selectByPrimaryKey = articleCheckMapper.selectByPrimaryKey(1L);
		System.out.println(selectByPrimaryKey);
	}

	@Test
	public void testFindPagable() {
		
	}

	@Test
	public void testCountByParams() {
		
	}

}
