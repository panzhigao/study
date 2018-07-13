package com.pan.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.pan.service.ScoreHistoryService;
import com.pan.util.TokenUtils;

/**
 * @author 作者
 * @version 创建时间：2018年6月15日 下午2:37:33
 * 类说明
 */
@Controller
public class ScoreHistoryController {
	
	@Autowired
	private ScoreHistoryService scoreHistoryService;
	
	/**
	 * 跳转积分历史页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/scoreHistory")
	@RequiresPermissions("/user/scoreHistory")
	public ModelAndView writeArticle(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/scoreHistory/scoreHistoryPage");
		return mav;
	}
	
	/**
	 * 加载积分历史信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/scoreHistory/getPageData")
	@ResponseBody
	@RequiresPermissions("/user/scoreHistory")
	public ResultMsg getScoreHistory(Integer pageSize,Integer pageNo){
		String loingUserId = TokenUtils.getLoingUserId();
		QueryScoreHistory queryScoreHistory=new QueryScoreHistory();
		queryScoreHistory.setUserId(loingUserId);
		queryScoreHistory.setPageSize(pageSize);
		queryScoreHistory.setPageNo(pageNo);
		queryScoreHistory.setOrderCondition("create_time desc");
		Map<String, List<ScoreHistory>> findShowData = scoreHistoryService.findShowData(queryScoreHistory);
		return ResultMsg.ok("获取积分历史数据成功", findShowData);
	}
}
