package com.pan.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.common.exception.BusinessException;
import com.pan.entity.ScoreHistory;
import com.pan.mapper.ScoreHistoryMapper;
import com.pan.service.ScoreHistoryService;
import com.pan.vo.QueryScoreHistoryVO;

@Service
public class ScoreHistoryImpl implements ScoreHistoryService{
	
	@Autowired
	private ScoreHistoryMapper scoreHistoryMapper;
	
	@Override
	public void save(ScoreHistory scoreHistory) {
		scoreHistoryMapper.save(scoreHistory);
	}
	
	/**
	 * 签到，可得5分
	 */
	@Override
	public void checkIn(String userId) {
		QueryScoreHistoryVO query=new QueryScoreHistoryVO();
		query.setUserId(userId);
		query.setScoreDate(new Date());
		int count = scoreHistoryMapper.getCountByParams(query);
		if(count>0){
			throw new BusinessException("今天已签到过了");
		}
		ScoreHistory scoreHistory=new ScoreHistory();
		scoreHistory.setUserId(userId);
		scoreHistory.setType(ScoreHistory.ScoreType.CHECK_IN.getName());
		scoreHistory.setScore(ScoreHistory.ScoreType.CHECK_IN.getScore());
		scoreHistory.setScoreDate(new Date());
		scoreHistory.setCreateTime(new Date());
		scoreHistoryMapper.save(scoreHistory);
	}
}
