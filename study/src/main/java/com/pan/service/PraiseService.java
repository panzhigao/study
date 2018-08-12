package com.pan.service;

import com.pan.entity.Praise;
import com.pan.query.QueryPraise;

/**
 * 
 * @author Administrator
 *
 */
public interface PraiseService {
	/**
	 * 点赞
	 * @param praise
	 */
	public void addPraise(Praise praise);
	/**
	 * 查询条数
	 * @param queryPraise
	 * @return
	 */
	public int getCount(QueryPraise queryPraise);
}
