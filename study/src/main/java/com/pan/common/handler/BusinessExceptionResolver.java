package com.pan.common.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pan.common.constant.MyConstant;
import com.pan.common.enums.MessageStatusEnum;
import com.pan.entity.ExceptionLog;
import com.pan.service.ExceptionLogService;
import com.pan.util.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;

/**
 * 异常类处理
 * 
 * @author Administrator
 * 
 */
public class BusinessExceptionResolver implements HandlerExceptionResolver {
	
	private static final String DEFAULT_MESSAGE="不好意思，出错了";
	
	private String viewName="html/error/500";
	
	private static final Logger logger = LoggerFactory.getLogger(BusinessExceptionResolver.class);

	@Autowired
	private ExceptionLogService exceptionLogService;

	public static boolean isAjax(HttpServletRequest request) {  
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));  
    }  
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error(ex.getMessage());
		BusinessException businessException;
		// 如果抛出的是系统自定义的异常则直接转换
		if (ex instanceof BusinessException) {
			businessException = (BusinessException) ex;
		//权限异常
		}else if(ex instanceof AuthorizationException){
			viewName="html/error/unauth";
			businessException = new BusinessException("权限异常,没有当前权限");
		}else if(ex instanceof AuthenticationException){
			businessException = new BusinessException("用户登陆失败");
		}else {
			// 如果抛出的不是系统自定义的异常则重新构造一个未知错误异常
			if (handler instanceof HandlerMethod) {
				logger.error("------------->>记录异常信息到数据库");
				HandlerMethod handlerMethod = (HandlerMethod) handler;

				String className = handlerMethod.getBeanType().getName();
				String methodName = handlerMethod.getMethod().getName();
				StringWriter sw = new StringWriter();
				ex.printStackTrace(new PrintWriter(sw, true));

				ExceptionLog exceptionLog=new ExceptionLog();
				exceptionLog.setIp(IPUtils.ip2Integer(TokenUtils.getIp()));
				exceptionLog.setClassName(className);
				exceptionLog.setMethodName(methodName);
				//消息未读
				exceptionLog.setIsView(MessageStatusEnum.MESSAGE_NOT_READED.getCode());
				exceptionLog.setExceptionMsg(sw.toString());
				exceptionLog.setExceptionType(ex.getClass().getSimpleName());
				exceptionLog.setCreateTime(new Date());
				if(TokenUtils.isAuthenticated()){
					exceptionLog.setUsername(TokenUtils.getLoginUser().getUsername());
				}else{
					exceptionLog.setUsername(MyConstant.USERNAME_SYSTEM);
				}
				exceptionLogService.insertSelective(exceptionLog);
				EmailUtils.sendMail("网站发生问题，请尽快处理",sw.toString(), SystemConfigUtils.getParamValue("email_receviers"),true);
			}
			businessException = new BusinessException(DEFAULT_MESSAGE);
		}
		if(isAjax(request)){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");  
            PrintWriter writer= null;
			 try {  
				 writer=response.getWriter(); 
                 ResultMsg resultMsg=ResultMsg.fail(businessException.getMessage());
                 writer.write(JsonUtils.toJson(resultMsg));
                 writer.flush();  
             } catch (IOException e) {  
                 e.printStackTrace();  
             } finally{
            	 if(writer!=null){
            		 writer.close();
            	 }
             }
             return null;  
		}
		// 向前台返回错误信息
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", businessException.getMessage());
		modelAndView.addObject("user", TokenUtils.getLoginUser());
		modelAndView.setViewName(viewName);
		return modelAndView;

	}

}
