package com.pan.service;

import java.util.List;

import com.pan.entity.Link;

/**
 * @author panzhigao
 */
public interface LinkService extends BaseService<Link>{
	/**
	 * 新增链接
	 * @param link
	 */
	void addLink(Link link);
	/**
	 * 编辑链接
	 * @param link
	 */
	void editLink(Link link);
	/**
	 * 编辑链接
	 * @param id
	 */
	void deleteLink(Long id);
	/**
	 * 修改链接状态
	 * @param id
	 * @param status
	 * @return
	 */
	String changeLinkStatus(Long id, Integer status);
	/**
	 * 获取上线链接
	 * @return
	 */
	List<Link> getOnlineLinkList();
}
