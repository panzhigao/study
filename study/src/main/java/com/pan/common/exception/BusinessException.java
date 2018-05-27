package com.pan.common.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 业务异常类
 * @author Administrator
 *
 */
public class BusinessException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8957950778864895347L;
	
	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}



	public BusinessException(String message) {
		super(message);
	}



	public BusinessException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
