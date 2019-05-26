package com.pan.service;

import java.util.Map;
import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.entity.OperateLog;
import com.pan.query.QueryOperateLog;

/**
 * @author panzhigao
 */
public interface IOperateLogService extends BaseService<OperateLog>{
    /**
     * 新增操作日志
     * @param content
     * @param operateLogTypeEnum
     * @return
     */
    int addOperateLog(String content, OperateLogTypeEnum operateLogTypeEnum);
    /**
     * 多条件查询，支持分页
     * @param queryOperateLog
     * @return
     */
    Map<String, Object> findByParams(QueryOperateLog queryOperateLog);
}
