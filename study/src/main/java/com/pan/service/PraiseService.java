package com.pan.service;

import com.pan.entity.Praise;

/**
 * 
 * @author Administrator
 *
 */
public interface PraiseService extends BaseService<Praise>{
	/**
	 * 点赞
	 * @param praise
	 */
	void addPraise(Praise praise);
}
