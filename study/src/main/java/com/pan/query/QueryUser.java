package com.pan.query;

import com.pan.common.annotation.QueryEsParam;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者
 * @version 创建时间：2018年3月28日 下午5:17:24
 * 类说明
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryUser extends QueryBase{
	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户性别 0-未知，1-男，2-女
	 */
	private Integer sex;
	/**
	 * 用户昵称
	 */
	@QueryEsParam(highLightFlag = true)
	private String nickname;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 手机号
	 */
	private String telephone;
}
