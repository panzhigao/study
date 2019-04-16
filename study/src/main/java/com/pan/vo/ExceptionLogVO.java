package com.pan.vo;

import com.pan.entity.ExceptionLog;
import com.pan.entity.OperateLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常日志实体
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ExceptionLogVO extends ExceptionLog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -166018333914182658L;
	
	private String ipStr;
}
