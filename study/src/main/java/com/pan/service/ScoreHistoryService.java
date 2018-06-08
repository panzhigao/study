package com.pan.service;

import com.pan.entity.ScoreHistory;

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
	public void addScoreHistory(String userId,ScoreHistory.ScoreType scoreType);
}
