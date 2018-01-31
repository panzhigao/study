package com.pan.mapper;

import java.util.Map;

import com.pan.entity.Praise;

/**
 * 
 * @author Administrator
 *
 */
public interface PraiseMapper {
	public void addPraise(Praise praise);
	
	public Praise findByParams(Map<String,Object> params);
}
