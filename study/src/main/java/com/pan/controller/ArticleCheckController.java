package com.pan.controller;

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
import com.pan.service.ArticleCheckService;
import com.pan.vo.QueryArticleCheckVO;

@Controller
public class ArticleCheckController {
	
//	private static final Logger logger=LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleCheckService articleCheckService;
	
	/**
	 * 跳转文章审核页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/check")
	@RequiresPermissions("/user/article/doSave")
	public ModelAndView toIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/user/check");
		return mav;
	}
	
	/**
	 * 加载审核数据
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/check/getPageData")
	@ResponseBody
	@RequiresPermissions(value="/user/check")
	public Map<String,Object> getArticleCheckList(Integer pageSize,Integer pageNo,String completeFlag){
		QueryArticleCheckVO queryArticleCheckVO=new QueryArticleCheckVO();
		queryArticleCheckVO.setPageSize(pageSize);
		queryArticleCheckVO.setPageNo(pageNo);
		queryArticleCheckVO.setCompleteFlag(completeFlag);
		Map<String,Object> pageData=articleCheckService.findByParams(queryArticleCheckVO);
		return pageData;
	}
	
	/**
	 * 文章审核通过
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/check/pass")
	@ResponseBody
	@RequiresPermissions(value="/user/check")
	public ResultMsg articlePass(String id){
		articleCheckService.passArticleCheck(id);
		return ResultMsg.ok("文章通过审核");
	}
	
	/**
	 * 文章未审核通过
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/check/notPass")
	@ResponseBody
	@RequiresPermissions(value="/user/check")
	public ResultMsg articleNotPass(String id,String reason){
		articleCheckService.notPassArticle(id, reason);
		return ResultMsg.ok("文章未通过审核");
	}
}
