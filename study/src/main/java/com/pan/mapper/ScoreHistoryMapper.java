package com.pan.mapper;

import java.util.List;
import com.pan.entity.ScoreHistory;
import com.pan.query.QueryScoreHistory;
import com.pan.vo.ScoreHistoryVO;

/**
 * 
 * @author Administrator
 *
 */
public interface ScoreHistoryMapper {
	/**
	 * 新增积分历史
	 */
	public void save(ScoreHistory scoreHistory);
	/**
	 * 查询条数
	 * @param queryScoreHistoryVO
	 * @return
	 */
	public int getCountByParams(QueryScoreHistory queryScoreHistoryVO);
	/**
	 * 多条件查询积分历史记录
	 * @param queryScoreHistoryVO
	 * @return
	 */
	public List<ScoreHistory> findByParams(QueryScoreHistory queryScoreHistoryVO);
	/**
	 * 多条件查询积分历史记录
	 * @param queryScoreHistoryVO
	 * @return
	 */
	public List<ScoreHistoryVO> findVOByParams(QueryScoreHistory queryScoreHistoryVO);
}
