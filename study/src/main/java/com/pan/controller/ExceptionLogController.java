package com.pan.controller;


import com.pan.common.enums.MessageStatusEnum;
import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.entity.ExceptionLog;
import com.pan.query.QueryExceptionLog;
import com.pan.service.ExceptionLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

/**
 * @author panzhigao
 */
@Controller
@RequestMapping("/user/exceptionLog")
public class ExceptionLogController {

    @Autowired
    private ExceptionLogService exceptionLogService;

    /**
     * 跳转操作异常日志列表
     */
    @RequestMapping(method= RequestMethod.GET,value="")
    @RequiresPermissions("/user/exceptionLog")
    public ModelAndView toLogList(){
        ModelAndView mav=new ModelAndView("html/system/exceptionLog");
        mav.addObject("types", OperateLogTypeEnum.getEnums());
        return mav;
    }

    /**
     * 加载异常日志数据，分页查询
     * @return
     */
    @RequestMapping(method=RequestMethod.POST,value="getPageData")
    @ResponseBody
    @RequiresPermissions("/user/exceptionLog")
    public Map<String,Object> getUserArticleList(QueryExceptionLog queryExceptionLog){
        return exceptionLogService.findByParams(queryExceptionLog);
    }

    /**
     * 日志详情
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,value="detail")
    @RequiresPermissions(value="/user/exceptionLog")
    public ModelAndView exceptionMsgDetail(Long id){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("html/system/exceptionLogDetail");
        ExceptionLog exceptionLog = exceptionLogService.selectByPrimaryKey(id);
        //日志标记为已读
        ExceptionLog update=new ExceptionLog();
        update.setId(id);
        update.setIsView(MessageStatusEnum.MESSAGE_READED.getCode());
        exceptionLogService.updateByPrimaryKeySelective(update);
        mav.addObject("exceptionLog", exceptionLog);
        return mav;
    }
}
