package com.pan.common.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.util.JsonUtils;
import com.pan.util.TokenUtils;

/**
 * 异常类处理
 * 
 * @author Administrator
 * 
 */
public class BusinessExceptionResolver implements HandlerExceptionResolver {
	
	private String deaultMessage="不好意思，出错了";
	
	private String viewName="html/error/500";
	
	private static final Logger logger = LoggerFactory.getLogger(BusinessExceptionResolver.class);
	
	public static boolean isAjax(HttpServletRequest request) {  
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));  
    }  
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error(ex.getMessage());
		BusinessException businessException = null;
		// 如果抛出的是系统自定义的异常则直接转换
		if (ex instanceof BusinessException) {
			businessException = (BusinessException) ex;
		}else if(ex instanceof AuthorizationException){//权限异常
			viewName="html/error/unauth";
			businessException = new BusinessException("权限异常,没有当前权限");
		}else if(ex instanceof AuthenticationException){
			businessException = new BusinessException("用户登陆失败");
		}else {
			// 如果抛出的不是系统自定义的异常则重新构造一个未知错误异常
			// 这里我就也有CustomException省事了，实际中应该要再定义一个新的异常
			businessException = new BusinessException(deaultMessage);
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
