package com.pan.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.annotation.QueryEsParam;
import com.pan.common.enums.QueryTypeEnum;
import com.pan.query.QueryBase;
import com.pan.service.EsClientService;
import com.pan.util.BeanUtils;
import com.pan.util.ClassUtils;
import com.pan.util.JsonUtils;

/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午12:22:11 类说明
 */
@Service
public class EsClientServiceImpl implements EsClientService {

	private static final Logger logger = LoggerFactory.getLogger(EsClientServiceImpl.class);

	@Autowired
	private RestHighLevelClient client;

	@Override
	public IndexRequest buildIndexRequest(String index, String type, Object obj) {
		IndexRequest indexRequest = new IndexRequest(index, type);
		if (obj instanceof Map) {
			Object id = ((Map<?, ?>) obj).get("id");
			Object userId = ((Map<?, ?>) obj).get("userId");
			if (id != null) {
				indexRequest.id(String.valueOf(id));
			}
			if(userId!=null){
				indexRequest.routing(String.valueOf(userId));
			}else if(id!=null){
				indexRequest.routing(String.valueOf(id));
			}
		} else {
			Object id = ClassUtils.getFieldValue(obj, "id");
			if (id != null) {
				indexRequest.id(String.valueOf(id));
			}
			Object userId = ClassUtils.getFieldValue(obj, "userId");
			if (userId != null) {
				indexRequest.routing(String.valueOf(userId));
			}else if(id!=null){
				indexRequest.routing(String.valueOf(id));
			}
		}
		String source = JsonUtils.toJson(obj);
		return indexRequest.source(source, XContentType.JSON);
	}

	@Override
	public boolean createIndex(String index, String type, Object obj) {
		IndexRequest indexRequest = buildIndexRequest(index, type, obj);
		try {
			IndexResponse response = client.index(indexRequest);
			return response.getShardInfo().getSuccessful() > 0;
		} catch (IOException e) {
			logger.error("创建索引失败,index:{},type:{}", index, type);
			return false;
		}
	}


	/**
	 * 构建查询条件 遍历字段，获取字段上的注解，当查询类型为MATCH时，分词匹配 当查询类型为TERM时，不分词匹配
	 * 
	 * @param queryBase
	 * @return
	 */
	private BoolQueryBuilder generate(QueryBase queryBase) {
		BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		Field[] fields = ClassUtils.getAllFields(queryBase);
		for (Field field : fields) {
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(queryBase);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				continue;
			}
			if (value != null) {
				QueryEsParam annotation = field.getAnnotation(QueryEsParam.class);
				if (annotation != null) {
					QueryTypeEnum queryType = annotation.queryType();
					switch (queryType) {
					case MATCH:
						MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(field.getName(), value);
						boolBuilder.must(matchQueryBuilder);
						break;
					case TERM:
						TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(field.getName(), value);
						boolBuilder.must(termQueryBuilder);
						break;
					default:
						break;
					}
				} 
			}
		}
		return boolBuilder;
	}

	/**
	 * 构造高亮查询参数
	 * 
	 * @param queryVO
	 * @return
	 */
	// @SuppressWarnings("rawtypes")
	// private HighlightBuilder highParams(QueryVO queryVO) {
	// HighlightBuilder highlightBuilder = null;
	// Class clazz = queryVO.getClass();
	// Field[] fields = clazz.getDeclaredFields();
	// for (Field field : fields) {
	// field.setAccessible(true);
	// Object value = null;
	// try {
	// value = field.get(queryVO);
	// } catch (IllegalArgumentException | IllegalAccessException e) {
	// e.printStackTrace();
	// continue;
	// }
	// if (value != null) {
	// QueryParam annotation = field.getAnnotation(QueryParam.class);
	// if (annotation != null && annotation.highLightFlag()) {
	// if (highlightBuilder == null) {
	// highlightBuilder = new HighlightBuilder();
	// highlightBuilder.preTags("<h2>");
	// highlightBuilder.postTags("</h2>");
	// }
	// highlightBuilder.field(field.getName());
	// }
	// }
	// }
	// return highlightBuilder;
	// }

	/**
	 * 构造高亮查询参数
	 * 
	 * @param queryBase
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public HighlightBuilder highlightParams(QueryBase queryBase, List<String> fieldList) {
		HighlightBuilder highlightBuilder = null;
		Class clazz = queryBase.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(queryBase);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				continue;
			}
			if (value != null) {
				QueryEsParam annotation = field.getAnnotation(QueryEsParam.class);
				if (annotation != null && annotation.highLightFlag()) {
					if (highlightBuilder == null) {
						highlightBuilder = new HighlightBuilder();
						highlightBuilder.preTags("<strong style='color:red;'>");
						highlightBuilder.postTags("</strong>");
					}
					if (fieldList != null) {
						fieldList.add(field.getName());
					}
					highlightBuilder.field(field.getName());
				}
			}
		}
		return highlightBuilder;
	}
	
	/**
	 * 查询索引数量
	 */
	@Override
	public long queryCountByParams(String index, String type, QueryBase queryBase) {
		// 查询条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = generate(queryBase);
		searchSourceBuilder.query(boolQueryBuilder);
		searchSourceBuilder.size(0);

		SearchRequest request = new SearchRequest(index);
		request.types(type);
		request.source(searchSourceBuilder);
		request.searchType(SearchType.QUERY_THEN_FETCH);
		SearchResponse response = null;
		try {
			response = client.search(request);
		} catch (IOException e) {
			logger.error("查询索引数量失败,index:{},type:{},params:{}", index, type, JsonUtils.toJson(queryBase));
		}
		long totalHits = response.getHits().getTotalHits();
		return totalHits;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryByParamsWithHightLight(SearchSourceBuilder searchSourceBuilder, String index, String type,
			QueryBase queryBase, boolean highLightFlag, Class<?> T) {
		buildPageable(searchSourceBuilder,queryBase);
		// 高亮字段
		List<String> highlightFieldList = new ArrayList<String>();
		if (highLightFlag) {
			HighlightBuilder hiBuilder = highlightParams(queryBase, highlightFieldList);
			if (hiBuilder != null) {
				searchSourceBuilder.highlighter(hiBuilder);
			}
		}
		SearchRequest request = new SearchRequest(index);
		request.types(type);
		request.source(searchSourceBuilder);
		SearchResponse response = null;
		List<T> list = new ArrayList<T>();
		try {
			request.types(type);
			request.source(searchSourceBuilder);
			response = client.search(request);
		} catch (IOException e) {
			logger.error("查询索引失败,index:{},type:{},params:{}", index, type, JsonUtils.toJson(queryBase), e);
			return list;
		}
		SearchHit[] hits = response.getHits().getHits();
		for (SearchHit searchHit : hits) {
			String res = searchHit.getSourceAsString();
			T sourceAll = (T) JsonUtils.fromJson(res, T);

			Map<String, SearchHits> innerHits = searchHit.getInnerHits();
			if (MapUtils.isNotEmpty(innerHits)) {
				SearchHits searchHits = innerHits.get("user");
				if(searchHit!=null){
					for (SearchHit ss : searchHits) {
						String innerRes = ss.getSourceAsString();
						T source2 = (T) JsonUtils.fromJson(innerRes, T);
						BeanUtils.copyPropertiesIgnoreNull(source2, sourceAll);
					}
				}
			}
			//setValue(source1, "esId", searchHit.getId());

			Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
			if (MapUtils.isNotEmpty(highlightFields) && highLightFlag) {
				for (String filedStr : highlightFieldList) {
					Text[] texts = highlightFields.get(filedStr).getFragments();
					String highText = "";
					for (Text t : texts) {
						highText += t.toString();
					}
					ClassUtils.setValue(sourceAll, filedStr, highText);
				}
			}
			list.add(((T) sourceAll));
		}
		return list;
	}
	
	/**
	 * 构造分页参数
	 * @param searchSourceBuilder
	 * @param queryBase
	 */
	private void buildPageable(SearchSourceBuilder searchSourceBuilder, QueryBase queryBase) {
		if (searchSourceBuilder !=null && queryBase.getOffset() != null && queryBase.getRow() != null) {
			searchSourceBuilder.from(queryBase.getOffset());
			searchSourceBuilder.size(queryBase.getRow());
		}
	}

	@Override
	public <T> List<T> queryByParamsWithHightLight(String index, String type, QueryBase queryBase,
			boolean highLightFlag, Class<?> T) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = generate(queryBase);
		searchSourceBuilder.query(boolQueryBuilder);
		return queryByParamsWithHightLight(searchSourceBuilder, index, type, queryBase, highLightFlag, T);
	}

	@Override
	public UpdateRequest buildUpdateRequest(String index, String type, String id, Map<String, Object> mapContent) {
		UpdateRequest updateRequest = new UpdateRequest(index, type, id);
		try {
			XContentBuilder xBuild = XContentFactory.jsonBuilder().startObject();
			for (String key : mapContent.keySet()) {
				xBuild.field(key, mapContent.get(key));
			}
			xBuild.endObject();
			UpdateRequest doc = updateRequest.doc(xBuild);
			return doc;
		} catch (Exception e) {
			logger.error("创建更新es索引失败，index={},type={},id={}", index, type, id);
		}
		return null;
	}

	@Override
	public boolean updateRecord(String index, String type, String id, Map<String, Object> mapContent) {
		try {
			UpdateRequest buildUpdateRequest = buildUpdateRequest(index, type, id, mapContent);
			UpdateResponse update = client.update(buildUpdateRequest);
			int successful = update.getShardInfo().getSuccessful();
			return successful > 0;
		} catch (IOException e) {
			logger.error("更新es失败，index={},type={},id={}", index, type, id);
		}
		return false;
	}

	/**
	 * 删除索引记录
	 * 
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteRecord(String index, String type, String id) {
		DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
		try {
			DeleteResponse delete = client.delete(deleteRequest);
			return delete.getShardInfo().getSuccessful() > 0;
		} catch (IOException e) {
			logger.error("更新es失败");
		}
		return false;
	}

	/**
	 * 批量处理
	 */
	@Override
	public BulkResponse bulk(BulkRequest bulkRequest) {
		try {
			BulkResponse bulk = client.bulk(bulkRequest);
			if (bulk.hasFailures()) {
				logger.error("批量执行es操作存在失败操作");
				logger.error(bulk.buildFailureMessage());
			}
		} catch (IOException e) {
			logger.error("批量执行es操作失败", e);
		}
		return null;
	}

	/**
	 * 根据id查询唯一数据
	 * 
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getById(String index, String type, String id, Class<?> T) {
		GetRequest getRequest = new GetRequest(index, type, id);
		try {
			GetResponse getResponse = client.get(getRequest);
			String sourceAsString = getResponse.getSourceAsString();
			return (T) JsonUtils.fromJson(sourceAsString, T);
		} catch (Exception e) {
			logger.error("根据id查询es索引失败,index={},type={},id={}", index, type, id);
		}
		return null;
	}
}
