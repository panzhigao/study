package com.pan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.pan.common.enums.ArticleStatusEnum;
import com.pan.common.enums.ScoreTypeEnum;
import com.pan.dto.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Article;
import com.pan.entity.ScoreHistory;
import com.pan.entity.UserExtension;
import com.pan.query.QueryArticle;
import com.pan.query.QueryScoreHistory;
import com.pan.query.QueryUserExtension;
import com.pan.service.ArticleService;
import com.pan.service.ScoreHistoryService;
import com.pan.service.UserExtensionService;
import com.pan.util.TokenUtils;
import com.pan.vo.ScoreHistoryVO;

/**
 * 网站首页
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	
	@Autowired
	private UserExtensionService userExtensionService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ScoreHistoryService scoreHistoryService;
	
	/**
	 * 跳转网站主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/","/index"})
	public ModelAndView toLogin(){
		ModelAndView mav=new ModelAndView("html/index");
		//用户已登录
		if(TokenUtils.isAuthenticated()){
			UserExtension extension = userExtensionService.selectByPrimaryKey(TokenUtils.getLoginUserId());
			mav.addObject("checkInDays", extension.getContinuousCheckInDays());
			//查询今日是否已签到
			QueryScoreHistory queryScoreHistory=new QueryScoreHistory();
			queryScoreHistory.setUserId(TokenUtils.getLoginUserId());
			queryScoreHistory.setType(ScoreTypeEnum.CHECK_IN.getCode());
			queryScoreHistory.setScoreDate(new java.sql.Date(System.currentTimeMillis()));
			List<ScoreHistory> list = scoreHistoryService.findPageable(queryScoreHistory);
			//今日已签到
			if(list.size()==1){
				mav.addObject("checked", "Y");
				mav.addObject("score", list.get(0).getScore());
			}else{//计算今日签到可获取积分
				int todayCheckInScore = scoreHistoryService.getTodayCheckInScore(extension.getContinuousCheckInDays());
				mav.addObject("todayCheckInScore", todayCheckInScore);
				mav.addObject("checked", "N");
			}
		}
		return mav;
	}
	
	/**
	 * 查询置顶帖，查询前4条
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/api/getStickData")
	@ResponseBody
	public Map<String,Object> loadStickData(){
		QueryArticle queryArticle=new QueryArticle();
		queryArticle.setPageNo(1);
		queryArticle.setPageSize(4);
		queryArticle.setStatus(ArticleStatusEnum.PUBLIC_SUCCESS.getCode());
		queryArticle.setWhereCondition("stick>0");
		Map<String, Object> dtoPageableMap = articleService.findDTOPageableMap(queryArticle);
		return dtoPageableMap;
	}
	
	/**
	 * 查询活跃用户信息，取前12条数据
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/api/getActiveUsers")
	@ResponseBody
	public ResultMsg loadActiveUsers(){
		QueryUserExtension queryUserExtension=new QueryUserExtension();
		queryUserExtension.setPageNo(1);
		queryUserExtension.setPageSize(12);
		queryUserExtension.setOrderCondition("comment_counts desc");
		queryUserExtension.setWhereCondition("comment_counts>0");
		List<UserExtension> list = userExtensionService.findPageable(queryUserExtension);
		return ResultMsg.ok("获取活跃用户成功", list);
	}
	
	/**
	 * 查询活跃排行榜
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/api/getActiveRanking")
	@ResponseBody
	public ResultMsg getActiveRanking(){
		//最新签到
		QueryScoreHistory historyVO=new QueryScoreHistory();
		historyVO.setType(ScoreTypeEnum.CHECK_IN.getCode());
		historyVO.setScoreDate(new java.sql.Date(System.currentTimeMillis()));
		historyVO.setOrderCondition("create_time desc");
		historyVO.setPageNo(1);
		historyVO.setPageSize(20);
		List<ScoreHistoryVO> newList = scoreHistoryService.findVOPageable(historyVO);
		//最快签到
		historyVO.setOrderCondition("create_time asc");
		List<ScoreHistoryVO> fastList = scoreHistoryService.findVOPageable(historyVO);
		//总签到榜
		QueryUserExtension queryUserExtension=new QueryUserExtension();
		queryUserExtension.setPageNo(1);
		queryUserExtension.setPageSize(20);
		queryUserExtension.setWhereCondition("continuous_check_in_days>0");
		queryUserExtension.setOrderCondition("continuous_check_in_days desc");
		List<UserExtension> rankingList = userExtensionService.findPageable(queryUserExtension);
		Map<String,Object> map=new HashMap<>(3);
		map.put("0", newList);
		map.put("1", fastList);
		map.put("2", rankingList);
		return ResultMsg.ok("获取首页排行榜数据成功", map);
	}
}
