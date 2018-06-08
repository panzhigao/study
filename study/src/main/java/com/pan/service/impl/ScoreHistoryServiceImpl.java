package com.pan.service.impl;

import java.util.Date;
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
import com.pan.service.ScoreHistoryService;
import com.pan.vo.QueryScoreHistoryVO;

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

	@Override
	public void addLoginScore(String userId) {
		QueryScoreHistoryVO vo=new QueryScoreHistoryVO();
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
	public void addScoreHistory(String userId, ScoreType scoreType) {
		//登陆积分
		if(ScoreHistory.ScoreType.LOGIN==scoreType){
			QueryScoreHistoryVO vo=new QueryScoreHistoryVO();
			vo.setUserId(userId);
			vo.setType(scoreType.getCode());
			vo.setScoreDate(new Date());
			int counts = scoreHistoryMapper.getCountByParams(vo);
			if(counts>0){
				logger.debug("{}今日已获取过登陆积分",userId);
				return;
			}
		}
		ScoreHistory history=new ScoreHistory();
		history.setUserId(userId);
		history.setType(scoreType.getCode());
		history.setCreateTime(new Date());
		history.setScoreDate(new Date());
		history.setScore(scoreType.getScore());
		//保存积分历史
		scoreHistoryMapper.save(history);
		//用户拓展表增加积分
		UserExtension userExtension=new UserExtension();
		userExtension.setUserId(userId);
		userExtension.setUpdateTime(new Date());
		userExtension.setScore(scoreType.getScore());
		userExtensionMapper.updateUserExtensionByUserId(userExtension);
	}
}
