package com.pan.service;

import com.pan.entity.Repertory;

/**
 * 库存表
 * @author Administrator
 *
 */
public interface IRepertoryService extends BaseService<Repertory>{
	/**
	 * 新增库存
	 * @param repertory
	 */
	void addRepertory(Repertory repertory);
	
}
