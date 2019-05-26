package com.pan.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pan.entity.LoginHistory;
import com.pan.query.QueryLoginHistory;
import com.pan.test.base.BaseTest;
import com.pan.vo.LoginHistoryVO;

public class LoginHistoryServiceTest extends BaseTest{
	
	@Autowired
	private ILoginHistoryService loginHistoryService;
	
	@Test
	public void testFindVOPageable() {
		QueryLoginHistory queryLoginHistory=new QueryLoginHistory();
		queryLoginHistory.setUsername("admin");
		queryLoginHistory.setPageNo(1);
		queryLoginHistory.setPageSize(5);
		List<LoginHistoryVO> findVOPageable = loginHistoryService.findVOPageable(queryLoginHistory);
		Assert.assertEquals(5, findVOPageable.size());
	}

	@Test
	public void testSelectByPrimaryKey() {
		LoginHistory selectByPrimaryKey = loginHistoryService.selectByPrimaryKey(2L);
		assertTrue(selectByPrimaryKey!=null);
	}

	@Test
	public void testDeleteByPrimaryKey() {
		
	}

	@Test
	public void testInsertSelective() {
		
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		
	}

	@Test
	public void testFindPageable() {
		
	}

	@Test
	public void testCountByParams() {
		
	}

}
