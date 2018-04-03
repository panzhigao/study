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
	
	public List<String> matchQuery(String index,String type,QueryVO queryVO);
	
}
