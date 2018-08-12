package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.exception.BusinessException;
import com.pan.entity.ScoreHistory;
import com.pan.entity.ScoreHistory.ScoreType;
import com.pan.entity.UserExtension;
import com.pan.mapper.ScoreHistoryMapper;
import com.pan.mapper.UserExtensionMapper;
import com.pan.query.QueryScoreHistory;
import com.pan.service.ScoreHistoryService;
import com.pan.util.DateUtils;
import com.pan.vo.ScoreHistoryVO;

@Service
public class ScoreHistoryServiceImpl implements ScoreHistoryService{
	
	private static final Logger logger=LoggerFactory.getLogger(ScoreHistoryServiceImpl.class);
	
	@Autowired
	private ScoreHistoryMapper scoreHistoryMapper;
	
	@Autowired
	private UserExtensionMapper userExtensionMapper;
	
	@Override
	public void save(ScoreHistory scoreHistory) {
		scoreHistoryMapper.save(scoreHistory);
	}
	
	/**
	 * 签到，可得5分
	 */
	@Override
	public void checkIn(String userId) {
		QueryScoreHistory query=new QueryScoreHistory();
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

	@Override
	public void addLoginScore(String userId) {
		QueryScoreHistory vo=new QueryScoreHistory();
		vo.setUserId(userId);
		vo.setType(ScoreHistory.ScoreType.LOGIN.getCode());
		vo.setScoreDate(new Date());
		int counts = scoreHistoryMapper.getCountByParams(vo);
		if(counts>0){
			logger.debug("{}今日已获取过登陆积分",userId);
			return;
		}
		ScoreHistory history=new ScoreHistory();
		BeanUtils.copyProperties(vo, history);
		history.setCreateTime(new Date());
		history.setScore(ScoreHistory.ScoreType.LOGIN.getScore());
		scoreHistoryMapper.save(history);
		//登录加5分
		UserExtension userExtension=new UserExtension();
		userExtension.setUserId(userId);
		userExtension.setUpdateTime(new Date());
		userExtension.setScore(ScoreHistory.ScoreType.LOGIN.getScore());
		userExtensionMapper.updateUserExtensionByUserId(userExtension);
	}
	
	/**
	 * 保存积分
	 * @param userId 用户id
	 * @param scoreType 积分类型
	 */
	@Override
	public ScoreHistory addScoreHistory(String userId, ScoreType scoreType) {
		//签到积分
		Integer score=scoreType.getScore();
		//签到积分
		if(ScoreHistory.ScoreType.CHECK_IN==scoreType){
			QueryScoreHistory vo=new QueryScoreHistory();
			vo.setUserId(userId);
			vo.setType(scoreType.getCode());
			vo.setScoreDate(new Date());
			int counts = scoreHistoryMapper.getCountByParams(vo);
			//今日获取过签到积分，不能再次获取
			if(counts>0){
				logger.debug("{}今日已获取过签到积分",userId);
				throw new BusinessException("今日已签到过了");
			}
			//查询昨日是否签到
			vo.setScoreDate(DateUtils.getYesterdayDate());
			int lastDayCount = scoreHistoryMapper.getCountByParams(vo);
			//昨日没有签到过，按5积分算,用户连续签到天数置为0
			if(lastDayCount>0){
				UserExtension userExtension = userExtensionMapper.findByUserId(userId);
				Integer continuousCheckInDays = userExtension.getContinuousCheckInDays();
				continuousCheckInDays=continuousCheckInDays==null?0:continuousCheckInDays;
				score=getTodayCheckInScore(continuousCheckInDays);	
			}
		}else if(ScoreHistory.ScoreType.LOGIN==scoreType){//登陆积分
			QueryScoreHistory vo=new QueryScoreHistory();
			vo.setUserId(userId);
			vo.setType(scoreType.getCode());
			vo.setScoreDate(new Date());
			int counts = scoreHistoryMapper.getCountByParams(vo);
			//今日获取过登陆积分，不能再次获取
			if(counts>0){
				logger.debug("{}今日已获取过登陆积分",userId);
				return null;
			}
		}
		ScoreHistory history=new ScoreHistory();
		history.setUserId(userId);
		history.setType(scoreType.getCode());
		history.setTypeName(scoreType.getName());
		history.setCreateTime(new Date());
		history.setScoreDate(new Date());
		history.setScore(score);
		//保存积分历史
		scoreHistoryMapper.save(history);
		return history;
	}

	@Override
	public int getCountByParams(QueryScoreHistory historyVO) {
		return scoreHistoryMapper.getCountByParams(historyVO);
	}

	@Override
	public List<ScoreHistory> findByParams(QueryScoreHistory historyVO) {
		return scoreHistoryMapper.findByParams(historyVO);
	}
	
	/**
	 * 计算今天签到分数
	 */
	@Override
	public int getTodayCheckInScore(int continuousCheckInDays) {
	    int checkInScore;
		if(continuousCheckInDays<5){
			checkInScore=5;
		}else if(continuousCheckInDays>=5&&continuousCheckInDays<15){
			checkInScore=10;
		}else if(continuousCheckInDays>=15&&continuousCheckInDays<30){
			checkInScore=15;
		}else if(continuousCheckInDays>=30&&continuousCheckInDays<100){
			checkInScore=20;
		}else if(continuousCheckInDays>=100&&continuousCheckInDays<365){
			checkInScore=30;
		}else{
			checkInScore=50;
		}	
		return checkInScore;
	}

	@Override
	public List<ScoreHistoryVO> findVOByParams(QueryScoreHistory historyVO) {
		return scoreHistoryMapper.findVOByParams(historyVO);
	}
	
	/**
	 * 获取积分数据，按日期分组，用于前端展示
	 * @return
	 */
	@Override
	public Map<String,List<ScoreHistory>> findShowData(QueryScoreHistory historyVO) {
		List<ScoreHistory> list = scoreHistoryMapper.findByParams(historyVO);
		Map<String,List<ScoreHistory>> resultMap=new LinkedHashMap<String, List<ScoreHistory>>(list.size());
		for (ScoreHistory scoreHistory : list) {
			Date scoreDate = scoreHistory.getScoreDate();
			String dateStr = DateUtils.getDateStr(scoreDate,DateUtils.FORMAT_DATE);
			if(resultMap.get(dateStr)!=null){
				List<ScoreHistory> histories=resultMap.get(dateStr);
				histories.add(scoreHistory);
			}else{
				List<ScoreHistory> list2=new ArrayList<>();
				list2.add(scoreHistory);
				resultMap.put(dateStr,list2);
			}	
		}
		return resultMap;
	}
}
