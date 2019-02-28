package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询用户拓展表VO
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryUserExtension extends QueryBase{
	/**
	 * 用户id
	 */
	private String userId;
}
