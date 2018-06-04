package com.pan.service;

import com.pan.entity.ScoreHistory;

public interface ScoreHistoryService {
	
	public void save(ScoreHistory scoreHistory);
	/**
	 * 签到
	 * @param scoreHistory
	 */
	public void checkIn(String userId);
}
