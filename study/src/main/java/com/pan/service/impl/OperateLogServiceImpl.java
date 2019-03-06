package com.pan.service.impl;

import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.entity.OperateLog;
import com.pan.entity.User;
import com.pan.mapper.OperateLogMapper;
import com.pan.query.QueryOperateLog;
import com.pan.service.AbstractBaseService;
import com.pan.service.OperateLogService;
import com.pan.util.JsonUtils;
import com.pan.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author panzhigao
 */
@Service
public class OperateLogServiceImpl extends AbstractBaseService<OperateLog,OperateLogMapper> implements OperateLogService {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    protected OperateLogMapper getBaseMapper() {
        return operateLogMapper;
    }

    @Override
    public int addOperateLog(String content, OperateLogTypeEnum operateLogTypeEnum) {
        User loginUser = TokenUtils.getLoginUser();
        StringBuilder stringBuilder=new StringBuilder(loginUser.getNickname()+"("+loginUser.getUsername()+")");
        stringBuilder.append(operateLogTypeEnum.getName()).append(":");
        stringBuilder.append(content);
        OperateLog operateLog=new OperateLog();
        operateLog.setUserId(loginUser.getUserId());
        operateLog.setUsername(loginUser.getUsername());
        operateLog.setContent(stringBuilder.toString());
        operateLog.setOperateType(operateLogTypeEnum.getCode());
        operateLog.setIpStr(TokenUtils.getIp());
        operateLog.setCreateTime(new Date());
        return operateLogMapper.insertSelective(operateLog);
    }

	@Override
	public Map<String, Object> findByParams(QueryOperateLog queryOperateLog) {
		Map<String, Object> pageData = new HashMap<String, Object>(2);
		List<OperateLog> list = new ArrayList<OperateLog>();
		try {
			logger.info("分页查询操作日志参数为:{}", JsonUtils.toJson(queryOperateLog));
			int total = operateLogMapper.countByParams(queryOperateLog);
			// 当查询记录大于0时，查询数据库记录，否则直接返回空集合
			if (total > 0) {
				list = operateLogMapper.findPageable(queryOperateLog);
			}
			pageData.put("data", list);
			pageData.put("total", total);
			pageData.put("code", "200");
			pageData.put("msg", "");
		} catch (Exception e) {
			logger.error("分页查询操作日志异常", e);
		}
		return pageData;
	}
}
