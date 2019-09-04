package com.pan.service.impl;

import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.entity.OperateLog;
import com.pan.entity.User;
import com.pan.mapper.OperateLogMapper;
import com.pan.query.QueryOperateLog;
import com.pan.service.AbstractBaseService;
import com.pan.service.IOperateLogService;
import com.pan.util.BeanUtils;
import com.pan.util.IPUtils;
import com.pan.util.JsonUtils;
import com.pan.util.TokenUtils;
import com.pan.vo.OperateLogVO;

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
public class OperateLogServiceImpl extends AbstractBaseService<OperateLog,OperateLogMapper> implements IOperateLogService {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
    @Autowired
    private OperateLogMapper operateLogMapper;


    /**
     * 新增操作日志
     */
    @Override
    public int addOperateLog(String content, OperateLogTypeEnum operateLogTypeEnum) {
        User loginUser = TokenUtils.getLoginUser();
        StringBuilder stringBuilder=new StringBuilder(loginUser.getNickname()+"("+loginUser.getUsername()+")");
        stringBuilder.append("【"+operateLogTypeEnum.getName()+"】  ");
        stringBuilder.append(content);
        OperateLog operateLog=new OperateLog();
        operateLog.setUserId(loginUser.getId());
        operateLog.setUsername(loginUser.getUsername());
        operateLog.setContent(stringBuilder.toString());
        operateLog.setOperateType(operateLogTypeEnum.getCode());
        operateLog.setIp(IPUtils.ip2Integer(TokenUtils.getIp()));
        operateLog.setCreateTime(new Date());
        return operateLogMapper.insertSelective(operateLog);
    }

	@Override
	public Map<String, Object> findByParams(QueryOperateLog queryOperateLog) {
		Map<String, Object> pageData = new HashMap<>(4);
		List<OperateLogVO> list = new ArrayList<>();
		try {
			logger.info("分页查询操作日志参数为:{}", JsonUtils.toJson(queryOperateLog));
			int total = operateLogMapper.countByParams(queryOperateLog);
			// 当查询记录大于0时，查询数据库记录，否则直接返回空集合
			if (total > 0) {
				List<OperateLog> findPageable = operateLogMapper.findPageable(queryOperateLog);
				findPageable.forEach(log->{
					OperateLogVO logVO=new OperateLogVO();
					BeanUtils.copyProperties(log, logVO);
					logVO.setIpStr(IPUtils.integer2Ip(log.getIp()));
					logVO.setOperateTypeName(OperateLogTypeEnum.getNameByCode(log.getOperateType()));
					list.add(logVO);
				});
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
