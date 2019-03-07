package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午3:49:39
 * 类说明
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryOperateLog extends QueryBase {
	
	private Integer operateType;
	
	private String username;
}
