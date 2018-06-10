package com.pan.service;

import java.util.List;
import com.pan.query.QueryBase;

/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午12:21:51
 * 类说明
 */
public interface EsClientService {
	
	public boolean createIndex(String index,String type,Object obj);
	/**
	 * 查询并高亮字段，支持分页
	 * 在需要高亮的字段上加上注解
	 * @param index
	 * @param type
	 * @param queryVO
	 * @param highLightFlag 为true时表示高亮字段
	 * @return
	 */
	public <T>List<T> queryByParamsWithHightLight(String index,String type,QueryBase queryBase,boolean highLightFlag,Class<?> T);
	/**
	 * 
	 * @param index
	 * @param type
	 * @param queryVO
	 * @return
	 */
	public long queryCountByParams(String index,String type,QueryBase queryVO);
}
