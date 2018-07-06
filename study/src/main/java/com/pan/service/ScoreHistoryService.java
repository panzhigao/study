package com.pan.service;

import java.util.List;
import java.util.Map;

import com.pan.entity.ScoreHistory;
import com.pan.query.QueryScoreHistory;
import com.pan.vo.ScoreHistoryVO;

public interface ScoreHistoryService {
	/**
	 * 登陆奖励积分
	 */
	public void addLoginScore(String userId);
	/**
	 * 保存积分记录
	 * @param scoreHistory
	 */
	public void save(ScoreHistory scoreHistory);
	/**
	 * 签到
	 * @param scoreHistory
	 */
	public void checkIn(String userId);
	/**
	 * 保存积分
	 * @param userId 用户id
	 * @param scoreType 积分类型
	 */
	public ScoreHistory addScoreHistory(String userId,ScoreHistory.ScoreType scoreType);
	/**
	 * 查询条数
	 * @param historyVO
	 * @return
	 */
	public int getCountByParams(QueryScoreHistory historyVO);
	/**
	 * 多条件查询，支持分页
	 * @param historyVO
	 * @return
	 */
	public List<ScoreHistory> findByParams(QueryScoreHistory historyVO);
	/**
	 * 多条件查询，支持分页
	 * @param historyVO
	 * @return
	 */
	public List<ScoreHistoryVO> findVOByParams(QueryScoreHistory historyVO);
	/**
	 * 获取今日签到积分
	 * @return
	 */
	public int getTodayCheckInScore(int continuousLoginDays);
	/**
	 * 获取积分数据，按日期分组，用于前端展示
	 * @return
	 */
	public Map<String,List<ScoreHistory>> findShowData(QueryScoreHistory historyVO);
}
