package com.pan.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pan.common.enums.ScoreTypeEnum;
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
			UserExtension extension = userExtensionService.findByUserId(TokenUtils.getLoginUserId());
			mav.addObject("checkInDays", extension.getContinuousCheckInDays());
			//查询今日是否已签到
			QueryScoreHistory historyVO=new QueryScoreHistory();
			historyVO.setUserId(TokenUtils.getLoginUserId());
			historyVO.setType(ScoreTypeEnum.CHECK_IN.getCode());
			historyVO.setScoreDate(new Date());
			List<ScoreHistory> list = scoreHistoryService.findByParams(historyVO);
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
	public ResultMsg loadStickData(){
		QueryArticle queryArticle=new QueryArticle();
		queryArticle.setPageNo(1);
		queryArticle.setPageSize(4);
		queryArticle.setStatus(Article.STATUS_PUBLISHED);
		queryArticle.setWhereCondition("stick>0");
		List<Article> list = articleService.findPagable(queryArticle);
		return ResultMsg.ok("获取置顶帖成功", list);
	}
	
	/**
	 * 查询活跃用户信息，取前12条数据
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/api/getActiveUsers")
	@ResponseBody
	public ResultMsg loadActiveUsers(){
		QueryUserExtension extensionVO=new QueryUserExtension();
		extensionVO.setPageNo(1);
		extensionVO.setPageSize(12);
		extensionVO.setOrderCondition("comment_counts desc");
		extensionVO.setWhereCondition("comment_counts>0");
		List<UserExtension> list = userExtensionService.findByParams(extensionVO);
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
		historyVO.setScoreDate(new Date());
		historyVO.setOrderCondition("create_time desc");
		historyVO.setPageNo(1);
		historyVO.setPageSize(20);
		List<ScoreHistoryVO> newList = scoreHistoryService.findVOByParams(historyVO);
		//最快签到
		historyVO.setOrderCondition("create_time asc");
		List<ScoreHistoryVO> fastList = scoreHistoryService.findVOByParams(historyVO);
		//总签到榜
		QueryUserExtension extensionVO=new QueryUserExtension();
		extensionVO.setPageNo(1);
		extensionVO.setPageSize(20);
		extensionVO.setWhereCondition("continuous_check_in_days>0");
		extensionVO.setOrderCondition("continuous_check_in_days desc");
		List<UserExtension> rankingList = userExtensionService.findByParams(extensionVO);
		Map<String,Object> map=new HashMap<>();
		map.put("0", newList);
		map.put("1", fastList);
		map.put("2", rankingList);
		return ResultMsg.ok("获取首页排行榜数据成功", map);
	}
}
