package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.pan.common.enums.ScoreTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.exception.BusinessException;
import com.pan.entity.ScoreHistory;
import com.pan.entity.UserExtension;
import com.pan.mapper.ScoreHistoryMapper;
import com.pan.mapper.UserExtensionMapper;
import com.pan.query.QueryScoreHistory;
import com.pan.service.AbstractBaseService;
import com.pan.service.ScoreHistoryService;
import com.pan.util.DateUtils;
import com.pan.vo.ScoreHistoryVO;

/**
 * @author panzhigao
 */
@Service
public class ScoreHistoryServiceImpl extends AbstractBaseService<ScoreHistory, ScoreHistoryMapper> implements ScoreHistoryService{
	
	private static final Logger logger=LoggerFactory.getLogger(ScoreHistoryServiceImpl.class);
	
	@Autowired
	private ScoreHistoryMapper scoreHistoryMapper;
	
	@Autowired
	private UserExtensionMapper userExtensionMapper;
	
	@Override
	protected ScoreHistoryMapper getBaseMapper() {
		return scoreHistoryMapper;
	}
		
	/**
	 * 签到，可得5分
	 */
	@Override
	public void checkIn(Long userId) {
		QueryScoreHistory query=new QueryScoreHistory();
		query.setUserId(userId);
		query.setScoreDate(new java.sql.Date(System.currentTimeMillis()));
		int count = scoreHistoryMapper.countByParams(query);
		if(count>0){
			throw new BusinessException("今天已签到过了");
		}
		ScoreHistory scoreHistory=new ScoreHistory();
		scoreHistory.setUserId(userId);
		scoreHistory.setType(ScoreTypeEnum.CHECK_IN.getCode());
		scoreHistory.setTypeName(ScoreTypeEnum.CHECK_IN.getName());
		scoreHistory.setScore(ScoreTypeEnum.CHECK_IN.getScore());
		scoreHistory.setScoreDate(new Date());
		scoreHistory.setCreateTime(new Date());
		scoreHistoryMapper.insertSelective(scoreHistory);
	}

//	@Override
//	public void addLoginScore(Long userId) {
//		QueryScoreHistory vo=new QueryScoreHistory();
//		vo.setUserId(userId);
//		vo.setType(ScoreTypeEnum.LOGIN.getCode());
//		vo.setScoreDate(new java.sql.Date(System.currentTimeMillis()));
//		int counts = scoreHistoryMapper.countByParams(vo);
//		if(counts>0){
//			logger.debug("{}今日已获取过登陆积分",userId);
//			return;
//		}
//		ScoreHistory history=new ScoreHistory();
//		BeanUtils.copyProperties(vo, history);
//		history.setCreateTime(new Date());
//		history.setScore(ScoreTypeEnum.LOGIN.getScore());
//		scoreHistoryMapper.insertSelective(history);
//		//登录加5分
//		UserExtension userExtension=new UserExtension();
//		userExtension.setId(userId);
//		userExtension.setUpdateTime(new Date());
//		userExtension.setTotalScore(ScoreTypeEnum.LOGIN.getScore());
//		userExtensionMapper.updateByPrimaryKeySelective(userExtension);
//	}
	
	/**
	 * 保存积分
	 * @param userId 用户id
	 * @param scoreType 积分类型
	 */
	@Override
	public ScoreHistory addScoreHistory(Long userId, ScoreTypeEnum scoreType) {
		//签到积分
		Integer score=scoreType.getScore();
		UserExtension userExtension = userExtensionMapper.selectByPrimaryKey(userId);
		//签到积分
		if(ScoreTypeEnum.CHECK_IN==scoreType){
			QueryScoreHistory vo=new QueryScoreHistory();
			vo.setUserId(userId);
			vo.setType(scoreType.getCode());
			vo.setScoreDate(new java.sql.Date(System.currentTimeMillis()));
			int counts = scoreHistoryMapper.countByParams(vo);
			//今日获取过签到积分，不能再次获取
			if(counts>0){
				logger.debug("{}今日已获取过签到积分",userId);
				throw new BusinessException("今日已签到过了");
			}
			//查询昨日是否签到
			vo.setScoreDate(new java.sql.Date(DateUtils.getYesterdayDate().getTime()));
			int lastDayCount = scoreHistoryMapper.countByParams(vo);
			//昨日没有签到过，按5积分算,用户连续签到天数置为0
			if(lastDayCount>0){
				Integer continuousCheckInDays = userExtension.getContinuousCheckInDays();
				continuousCheckInDays=continuousCheckInDays==null?0:continuousCheckInDays;
				score=getTodayCheckInScore(continuousCheckInDays);	
			}
			//登陆积分
		}else if(ScoreTypeEnum.LOGIN==scoreType){
			QueryScoreHistory vo=new QueryScoreHistory();
			vo.setUserId(userId);
			vo.setType(scoreType.getCode());
			vo.setScoreDate(new java.sql.Date(System.currentTimeMillis()));
			int counts = scoreHistoryMapper.countByParams(vo);
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
		history.setTotalScore(score+userExtension.getTotalScore());
		//保存积分历史
		scoreHistoryMapper.insertSelective(history);
		return history;
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

	
	/**
	 * 获取积分数据，按日期分组，用于前端展示
	 * @return
	 */
	@Override
	public Map<String,List<ScoreHistory>> findShowData(QueryScoreHistory historyVO) {
		List<ScoreHistory> list = scoreHistoryMapper.findPageable(historyVO);
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

	@Override
	public List<ScoreHistoryVO> findVOPageable(QueryScoreHistory historyVO) {
		return scoreHistoryMapper.findVOPageable(historyVO);
	}


}
