package com.pan.service;

import java.util.List;

import com.pan.vo.QueryVO;

/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午12:21:51
 * 类说明
 */
public interface EsClientService {
	
	public boolean createIndex(String index,String type,Object obj);
	
	/**
	 * 查询索引数据，不高亮
	 * @param index 索引名称
	 * @param type 类型
	 * @param queryVO 查询参数
	 * @return
	 */
	public List<String> queryByParams(String index,String type,QueryVO queryVO);
	
	/**
	 * 查询并高亮字段
	 * 在需要高亮的字段上加上注解
	 * @param index
	 * @param type
	 * @param queryVO
	 * @return
	 */
	public List<String> queryByParamsWithHightLight(String index,String type,QueryVO queryVO,boolean highLightFlag);
	/**
	 * 
	 * @param index
	 * @param type
	 * @param queryVO
	 * @return
	 */
	public long queryCountByParams(String index,String type,QueryVO queryVO);
}
