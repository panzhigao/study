package com.pan.query;

import lombok.Data;

import java.util.Date;


/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午3:49:39
 * 类说明
 */
@Data
public class QueryScoreHistory extends QueryBase{
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 积分类型，1-登陆，2-发表文章成功，3-回帖
	 */
	private String type;
	/**
	 * 积分
	 */
	private Integer score;
	/**
	 * 积分日期
	 */
	private Date scoreDate;
}
