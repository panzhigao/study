package com.pan.service;

import com.pan.entity.Praise;

/**
 * 
 * @author Administrator
 *
 */
public interface PraiseService extends BaseService<Praise>{
	/**
	 * 点赞评论
	 * @param praise
	 */
	void addCommentPraise(Praise praise);
}
