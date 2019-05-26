package com.pan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.ScoreHistory;
import com.pan.query.QueryScoreHistory;
import com.pan.service.IScoreHistoryService;
import com.pan.util.TokenUtils;

/**
 * @author 作者
 * @version 创建时间：2018年6月15日 下午2:37:33
 * 积分历史
 */
@Controller
public class ScoreHistoryController {
	
	@Autowired
	private IScoreHistoryService scoreHistoryService;
	
	/**
	 * 跳转积分历史页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/scoreHistory")
	@RequiresPermissions("scoreHistory:load")
	public ModelAndView scoreHistoryPage(){
		ModelAndView mav=new ModelAndView("html/scoreHistory/scoreHistoryPage");
		return mav;
	}
	
	/**
	 * 加载积分历史信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/scoreHistory/getPageData")
	@ResponseBody
	@RequiresPermissions("scoreHistory:load")
	public ResultMsg getScoreHistory(QueryScoreHistory queryScoreHistory){
		Long loginUserId = TokenUtils.getLoginUserId();
		queryScoreHistory.setUserId(loginUserId);
		queryScoreHistory.setOrderCondition("create_time desc");
		Map<String, List<ScoreHistory>> findShowData = scoreHistoryService.findShowData(queryScoreHistory);
		queryScoreHistory.setScoreDateEnd(null);
		int loadedCount = scoreHistoryService.countByParams(queryScoreHistory);
		//总条数
		QueryScoreHistory queryScoreHistory2=new QueryScoreHistory();
		queryScoreHistory2.setUserId(loginUserId);
		int total = scoreHistoryService.countByParams(queryScoreHistory2);
		Map<String,Object> resultMap=new HashMap<>(4);
		resultMap.put("total", total);
		resultMap.put("data", findShowData);
		resultMap.put("hasData", total>loadedCount);
		return ResultMsg.ok("获取积分历史数据成功", resultMap);
	}
}
