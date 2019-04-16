package com.pan.service;

import com.pan.entity.ExceptionLog;
import com.pan.query.QueryExceptionLog;
import java.util.Map;

/**
 * @author panzhigao
 */
public interface ExceptionLogService extends BaseService<ExceptionLog>{
    /**
     * 多条件查询，支持分页
     * @param queryExceptionLog
     * @return
     */
    Map<String, Object> findByParams(QueryExceptionLog queryExceptionLog);
}
