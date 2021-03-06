package com.pan.service;

import java.util.List;
import java.util.Map;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import com.pan.query.QueryBase;

/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午12:21:51
 * 类说明
 */
public interface IEsClientService {
	/**
	 * 创建索引
	 * @param index
	 * @param type
	 * @param obj
	 * @return
	 */
	IndexRequest buildIndexRequest(String index,String type,Object obj);
	/**
	 * 创建索引
	 * @return
	 */
	boolean createIndex(IndexRequest indexRequest);
	/**
	 * 创建索引
	 * @param index
	 * @param type
	 * @param obj
	 * @return
	 */
	boolean createIndex(String index, String type, Object obj);
	/**
	 * 查询并高亮字段，支持分页
	 * 在需要高亮的字段上加上注解
	 * @param index
	 * @param type
	 * @param queryBase 查询参数
	 * @param highLightFlag 为true时表示高亮字段
	 * @return
	 */
	<T>List<T> queryByParamsWithHightLight(String index,String type,QueryBase queryBase,boolean highLightFlag,Class<?> T);
	<T>List<T> queryByParamsWithHightLight(SearchSourceBuilder searchSourceBuilder,String index,String type,QueryBase queryBase,boolean highLightFlag,Class<?> T);
	/**
	 * 
	 * @param index
	 * @param type
	 * @param queryVO
	 * @return
	 */
	long queryCountByParams(String index,String type,QueryBase queryVO);
	/**
	 * 创建更新索引
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	UpdateRequest buildUpdateRequest(String index,String type,String id,Map<String, Object> mapContent);
	/**
	 * 更新
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	boolean updateRecord(String index, String type, String id,Map<String, Object> mapContent);
	/**
	 * 删除记录
	 * @return
	 */
	boolean deleteRecord(String index, String type, String id);
	/**
	 * 批量执行
	 * @param bulkRequest
	 * @return
	 */
	BulkResponse bulk(BulkRequest bulkRequest);
	/**
	 * 根据id查询唯一数据
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	<T> T getById(String index,String type,String id,Class<?> T);
	/**
	 * 生成高亮查询参数
	 * @param queryBase
	 * @param fieldList
	 * @return
	 */
	HighlightBuilder highlightParams(QueryBase queryBase, List<String> fieldList);
}
