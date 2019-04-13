package com.pan.query;

import java.sql.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午3:49:39
 * 类说明
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryScoreHistory extends QueryBase{
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 积分类型，1-登陆，2-发表文章成功，3-回帖
	 */
	private Integer type;
	/**
	 * 积分
	 */
	private Integer score;
	/**
	 * 积分日期
	 */
	private Date scoreDate;
	/**
	 * 积分开始日期
	 */
	private Date scoreDateStart;
	/**
	 * 积分结束日期
	 */
	private Date scoreDateEnd;
}
