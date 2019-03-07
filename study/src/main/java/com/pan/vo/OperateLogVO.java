package com.pan.vo;

import com.pan.entity.OperateLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志实体
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OperateLogVO extends OperateLog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -166018333914182658L;
	
	private String ipStr;
	/**
	 * 操作类型名
	 */
	private String operateTypeName;
}
