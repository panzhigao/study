package com.pan.mapper;


import com.pan.entity.Praise;
import com.pan.query.QueryPraise;

/**
 * 
 * @author Administrator
 *
 */
public interface PraiseMapper {
	
	public void addPraise(Praise praise);
	
	public Praise findByParams(QueryPraise queryPraise);
	
	public int getCount(QueryPraise queryPraise);
}
