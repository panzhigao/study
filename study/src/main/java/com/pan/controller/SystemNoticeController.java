package com.pan.controller;


import com.pan.common.enums.ArticleStatusEnum;
import com.pan.common.enums.ArticleTypeEnum;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Article;
import com.pan.query.QueryArticle;
import com.pan.service.ArticleService;
import com.pan.util.TokenUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

/**
 * 系统公告
 * @author panzhigao
 */
@Controller
public class SystemNoticeController {

    private static final Logger logger= LoggerFactory.getLogger(SystemNoticeController.class);

    @Autowired
    private ArticleService articleService;

    /**
     * 跳转发送系统公告页面
     * @return
     */
    @RequestMapping(method= RequestMethod.GET,value="/user/systemNotice")
    @RequiresPermissions("/user/systemNotice")
    public ModelAndView toSendMessageIndex(){
        ModelAndView mav=new ModelAndView("html/systemNotice/systemNoticePage");
        return mav;
    }

    /**
     * 发布系统公告
     * @param article
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="/user/systemNotice/send")
    @ResponseBody
    @RequiresPermissions(value="/user/systemNotice")
    public ResultMsg sendMessage(Article article){
        logger.info("发布系统公告开始");
        String userId= TokenUtils.getLoginUserId();
        article.setUserId(userId);
        articleService.saveSystemMessage(article);
        return ResultMsg.ok("发布系统公告成功");
    }

    /**
     * 加载系统公告列表，分页查询
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="/user/systemNotice/getPageData")
    @ResponseBody
    @RequiresPermissions(value="/user/systemNotice")
    public Map<String,Object> getArticleList(QueryArticle queryArticle){
        queryArticle.setStatus(ArticleStatusEnum.PUBLIC_SUCCESS.getCode());
        queryArticle.setType(ArticleTypeEnum.TYPE_SYSTEM_NOTICE.getCode());
        Map<String,Object> pageData=articleService.findPageableMap(queryArticle);
        return pageData;
    }
}
