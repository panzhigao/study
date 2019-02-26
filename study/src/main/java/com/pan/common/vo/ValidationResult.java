package com.pan.common.vo;

import lombok.Data;

import java.util.Map;

/**
 * 校验结果类
 * @author Administrator
 *
 */
@Data
public class ValidationResult {
	    /**
	     * 校验结果是否有错
	     */
		private boolean hasErrors;
		
		/**
		 * 校验错误信息
		 */
		private Map<String,String> errorMsg;

}
