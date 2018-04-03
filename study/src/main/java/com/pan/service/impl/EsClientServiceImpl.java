package com.pan.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RestHighLevelClient;
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
import com.pan.service.EsClientService;
import com.pan.util.JsonUtils;
import com.pan.vo.QueryVO;

/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午12:22:11 类说明
 */
@Service
public class EsClientServiceImpl implements EsClientService {

	private static final Logger logger = LoggerFactory
			.getLogger(EsClientServiceImpl.class);

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

	@Override
	public List<String> queryByParams(String index, String type, QueryVO queryVO) {
		return queryByParamsWithHightLight(index, type, queryVO, false);
	}

	/**
	 * 构建查询条件 遍历字段，获取字段上的注解，当查询类型为MATCH时，分词匹配 当查询类型为TERM时，不分词匹配
	 * 
	 * @param queryVO
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private BoolQueryBuilder generate(QueryVO queryVO) {
		BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		Class clazz = queryVO.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(queryVO);
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
						MatchQueryBuilder matchQueryBuilder = QueryBuilders
								.matchQuery(field.getName(), value);
						boolBuilder.must(matchQueryBuilder);
						break;
					case TERM:
						TermQueryBuilder termQueryBuilder = QueryBuilders
								.termQuery(field.getName(), value);
						boolBuilder.must(termQueryBuilder);
						break;
					default:
						break;
					}
				} else {
					MatchQueryBuilder matchQueryBuilder = QueryBuilders
							.matchQuery(field.getName(), value);
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
	@SuppressWarnings("rawtypes")
	private HighlightBuilder highParams(QueryVO queryVO) {
		HighlightBuilder highlightBuilder = null;
		Class clazz = queryVO.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(queryVO);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				continue;
			}
			if (value != null) {
				QueryParam annotation = field.getAnnotation(QueryParam.class);
				if (annotation != null && annotation.highLightFlag()) {
					if (highlightBuilder == null) {
						highlightBuilder = new HighlightBuilder();
						highlightBuilder.preTags("<h2>");
						highlightBuilder.postTags("</h2>");
					}
					highlightBuilder.field(field.getName());
				}
			}
		}
		return highlightBuilder;
	}

	@Override
	public List<String> queryByParamsWithHightLight(String index, String type,
			QueryVO queryVO, boolean highLightFlag) {
		// 查询条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if (queryVO.getOffset() != null && queryVO.getRow() != null) {
			searchSourceBuilder.from(queryVO.getOffset());
			searchSourceBuilder.size(queryVO.getRow());
		}
		BoolQueryBuilder boolQueryBuilder = generate(queryVO);
		searchSourceBuilder.query(boolQueryBuilder);
		
		if(highLightFlag){
			HighlightBuilder hiBuilder = highParams(queryVO);
			if (hiBuilder != null) {
				searchSourceBuilder.highlighter(hiBuilder);
			}
		}

		SearchRequest request = new SearchRequest(index);
		request.types(type);
		request.source(searchSourceBuilder);
		
		SearchResponse response = null;
		List<String> list = new ArrayList<String>();
		try {
			response = client.search(request);
		} catch (IOException e) {
			logger.error("查询索引失败,index:{},type:{},params:{}", index, type,JsonUtils.toJson(queryVO));
			return list;
		}
		SearchHit[] hits = response.getHits().getHits();
		for (SearchHit searchHit : hits) {
			String res = searchHit.getSourceAsString();
			list.add(res);
		}
		return list;
	}

	@Override
	public long queryCountByParams(String index, String type, QueryVO queryVO) {
		// 查询条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = generate(queryVO);
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
			logger.error("查询索引失败,index:{},type:{},params:{}", index, type,
					JsonUtils.toJson(queryVO));
		}
		long totalHits = response.getHits().getTotalHits();
		return totalHits;
	}
}
