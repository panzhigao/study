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
public interface ScoreHistoryMapper extends BaseMapper<ScoreHistory>{
	/**
	 * 多条件查询积分历史记录
	 * @param queryScoreHistoryVO
	 * @return
	 */
	public List<ScoreHistoryVO> findVOPageable(QueryScoreHistory queryScoreHistoryVO);
}
