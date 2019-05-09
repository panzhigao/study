package com.pan.redissub;

/**
 * 订阅接口
 * @author Administrator
 *
 */
public interface SubProcessor {
	/**
	 * 判断是否是当前频道	
	 * @param channelName
	 * @return
	 */
	boolean checkChannel(String channelName);
	/**
	 * 处理当前消息	
	 * @param message
	 */
	void handel(String message);
}
