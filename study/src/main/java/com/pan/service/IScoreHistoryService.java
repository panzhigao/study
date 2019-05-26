package com.pan.service;

import java.util.List;
import java.util.Map;

import com.pan.common.enums.ScoreTypeEnum;
import com.pan.entity.ScoreHistory;
import com.pan.query.QueryScoreHistory;
import com.pan.vo.ScoreHistoryVO;

/**
 * @author panzhigao
 */
public interface IScoreHistoryService extends BaseService<ScoreHistory>{
//    /**
//     * 登陆奖励积分
//     */
//    void addLoginScore(Long userId);
    /**
     * 签到
     * @param userId 用户id
     */
    void checkIn(Long userId);
    /**
     * 保存积分
     * @param userId 用户id
     * @param scoreTypeEnum 积分类型
     * @return
     */
    ScoreHistory addScoreHistory(Long userId, ScoreTypeEnum scoreTypeEnum);
    /**
     * 多条件查询，支持分页
     *
     * @param queryScoreHistory
     * @return
     */
    List<ScoreHistoryVO> findVOPageable(QueryScoreHistory queryScoreHistory);
    /**
     * 获取今日签到积分
     * @param continuousLoginDays
     * @return
     */
    int getTodayCheckInScore(int continuousLoginDays);

    /**
     * 获取积分数据，按日期分组，用于前端展示
     * @param queryScoreHistory
     * @return
     */
    Map<String, List<ScoreHistory>> findShowData(QueryScoreHistory queryScoreHistory);
}
