package com.pan.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pan.entity.ScoreHistory;
import com.pan.service.ScoreHistoryService;
import com.pan.test.base.BaseTest;

public class ScoreHistoryTest extends BaseTest{
	
	@Autowired
	private ScoreHistoryService historyService;
	
	@Test
	public void test1(){
		ScoreHistory history=new ScoreHistory();
		history.setId(1L);
		history.setUserId("2222");
		history.setCreateTime(new Date());
		history.setType("2");
		history.setScore(3);
		history.setScoreDate(new Date());
		historyService.save(history);
	}
	
}
