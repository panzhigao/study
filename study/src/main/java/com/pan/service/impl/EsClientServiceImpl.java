package com.pan.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.annotation.QueryParam;
import com.pan.common.enums.QueryTypeEnum;
import com.pan.query.QueryBase;
import com.pan.service.EsClientService;
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
	public boolean createIndex(String index, String type, Object obj) {
		IndexRequest indexRequest = new IndexRequest(index, type);
		String source = JsonUtils.toJson(obj);
		indexRequest.source(source, XContentType.JSON);
		try {
			client.index(indexRequest);
		} catch (IOException e) {
			logger.error("创建索引失败,index:{},type:{}", index, type);
			return false;
		}
		return true;
	}

	/**
	 * 查询文章信息，不高亮
	 */
//	@Override
//	public List<String> queryByParams(String index, String type, QueryVO queryVO) {
//		return queryByParamsWithHightLight(index, type, queryVO, true);
//	}

	/**
	 * 构建查询条件 遍历字段，获取字段上的注解，当查询类型为MATCH时，分词匹配 当查询类型为TERM时，不分词匹配
	 * 
	 * @param queryBase
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private BoolQueryBuilder generate(QueryBase queryBase) {
		BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
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
				QueryParam annotation = field.getAnnotation(QueryParam.class);
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
				} else {
					MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(field.getName(), value);
					boolBuilder.must(matchQueryBuilder);
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
//	@SuppressWarnings("rawtypes")
//	private HighlightBuilder highParams(QueryVO queryVO) {
//		HighlightBuilder highlightBuilder = null;
//		Class clazz = queryVO.getClass();
//		Field[] fields = clazz.getDeclaredFields();
//		for (Field field : fields) {
//			field.setAccessible(true);
//			Object value = null;
//			try {
//				value = field.get(queryVO);
//			} catch (IllegalArgumentException | IllegalAccessException e) {
//				e.printStackTrace();
//				continue;
//			}
//			if (value != null) {
//				QueryParam annotation = field.getAnnotation(QueryParam.class);
//				if (annotation != null && annotation.highLightFlag()) {
//					if (highlightBuilder == null) {
//						highlightBuilder = new HighlightBuilder();
//						highlightBuilder.preTags("<h2>");
//						highlightBuilder.postTags("</h2>");
//					}
//					highlightBuilder.field(field.getName());
//				}
//			}
//		}
//		return highlightBuilder;
//	}
	
	/**
	 * 构造高亮查询参数
	 * 
	 * @param queryBase
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private HighlightBuilder highParams(QueryBase queryBase,List<String> fieldList) {
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
				QueryParam annotation = field.getAnnotation(QueryParam.class);
				if (annotation != null && annotation.highLightFlag()) {
					if (highlightBuilder == null) {
						highlightBuilder = new HighlightBuilder();
						highlightBuilder.preTags("<strong style='color:red;'>");
						highlightBuilder.postTags("</strong>");
					}
					if(fieldList!=null){
						fieldList.add(field.getName());
					}
					highlightBuilder.field(field.getName());
				}
			}
		}
		return highlightBuilder;
	}


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
			logger.error("查询索引失败,index:{},type:{},params:{}", index, type, JsonUtils.toJson(queryBase));
		}
		long totalHits = response.getHits().getTotalHits();
		return totalHits;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryByParamsWithHightLight(String index, String type, QueryBase queryBase, boolean highLightFlag,
			Class<?> T) {
		// 查询条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if (queryBase.getOffset() != null && queryBase.getRow() != null) {
			searchSourceBuilder.from(queryBase.getOffset());
			searchSourceBuilder.size(queryBase.getRow());
		}
		BoolQueryBuilder boolQueryBuilder = generate(queryBase);
		searchSourceBuilder.query(boolQueryBuilder);
		
		List<String> fieldList=new ArrayList<String>();
		if (highLightFlag) {
			HighlightBuilder hiBuilder = highParams(queryBase,fieldList);
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
			response = client.search(request);
		} catch (IOException e) {
			logger.error("查询索引失败,index:{},type:{},params:{}", index, type, JsonUtils.toJson(queryBase), e);
			return list;
		}
		SearchHit[] hits = response.getHits().getHits();
		for (SearchHit searchHit : hits) {
			String res = searchHit.getSourceAsString();
			Object obj = JsonUtils.fromJson(res,T);
			if(highLightFlag){
				for(String filedStr:fieldList){
					Text[] texts = searchHit.getHighlightFields().get(filedStr).getFragments();
					String highText="";
					for(Text t:texts){
						highText+=t.toString();
					}
				    setValue(obj,filedStr,highText);
				    list.add(((T) obj));
				}
			}
		}
		return list;
	}
	
	
	@SuppressWarnings("rawtypes")
	private void setValue(Object obj,String filedStr,Object value){
		Class clazz=obj.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		Field[] parentFileds = clazz.getSuperclass().getDeclaredFields();
		Field[] all = (Field[]) ArrayUtils.addAll(declaredFields, parentFileds);
		for (Field field : all) {
			if(StringUtils.equals(filedStr, field.getName())){
				try {
					field.setAccessible(true);
					field.set(obj, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}	
		}
	}
	
}
