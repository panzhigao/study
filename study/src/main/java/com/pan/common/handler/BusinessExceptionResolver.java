package com.pan.common.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.util.JsonUtils;

/**
 * 异常类处理
 * 
 * @author Administrator
 * 
 */
public class BusinessExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(BusinessExceptionResolver.class);
	
	public static boolean isAjax(HttpServletRequest request) {  
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));  
    }  
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error(ex.getMessage());
		BusinessException businessException = null;
		// 如果抛出的是系统自定义的异常则直接转换
		if (ex instanceof BusinessException) {
			businessException = (BusinessException) ex;
		} else {
			// 如果抛出的不是系统自定义的异常则重新构造一个未知错误异常
			// 这里我就也有CustomException省事了，实际中应该要再定义一个新的异常
			businessException = new BusinessException("系统未知错误");
		}
		if(isAjax(request)){
			 try {  
				 response.setCharacterEncoding("UTF-8");
                 PrintWriter writer = response.getWriter(); 
                 ResultMsg resultMsg=ResultMsg.fail(ex.getMessage());
                 writer.write(JsonUtils.toJson(resultMsg));
                 writer.flush();  
             } catch (IOException e) {  
                 e.printStackTrace();  
             }  
             return null;  
		}
		// 向前台返回错误信息
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", businessException.getMessage());
		modelAndView.setViewName("error/404");
		return modelAndView;

	}

}
