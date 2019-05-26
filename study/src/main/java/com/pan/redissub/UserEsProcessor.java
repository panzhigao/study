package com.pan.redissub;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.pan.common.constant.MyConstant;
import com.pan.common.enums.RedisChannelEnum;
import com.pan.service.IUserService;
import com.pan.util.JedisUtils;
import com.pan.util.SpringContextUtils;

/**
 * 用户es更新
 * @author Administrator
 *
 */
@Service
public class UserEsProcessor implements SubProcessor{
	
	private static final Logger logger = LoggerFactory.getLogger(UserEsProcessor.class);
	
	@Override
	public boolean checkChannel(String channelName) {
		return RedisChannelEnum.USER_ES_CREATE_OR_UPDATE.getName().equals(channelName);
	}
	
	@Override
	public void handel(String message) {
		logger.info("用户es更新,message={}",message);
		IUserService userService = SpringContextUtils.getBean(IUserService.class);
    	String id = JedisUtils.brpoplpush(MyConstant.USER_ES_REDIS_LIST, MyConstant.USER_ES_REDIS_LIST_BAK);
    	if(StringUtils.isNumeric(id)){
    		logger.info("用户文章es数据,id={}",id);
    		boolean updateUserEs = userService.updateUserEs(Long.parseLong(id));
    		if(updateUserEs){
    			JedisUtils.lrem(MyConstant.USER_ES_REDIS_LIST_BAK, 0, id);
    		}
    	}
	}


}
