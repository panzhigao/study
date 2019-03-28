package com.pan.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.pan.common.constant.MyConstant;
import com.pan.common.enums.LinkStatusEnum;
import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.common.enums.UserStatusEnum;
import com.pan.common.exception.BusinessException;
import com.pan.entity.Link;
import com.pan.entity.User;
import com.pan.mapper.LinkMapper;
import com.pan.query.QueryLink;
import com.pan.service.AbstractBaseService;
import com.pan.service.LinkService;
import com.pan.service.OperateLogService;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;

/**
 * 链接管理
 * @author panzhigao
 */
@Service
public class LinkServiceImpl extends AbstractBaseService<Link, LinkMapper> implements LinkService {

	private static final Logger logger = LoggerFactory.getLogger(LinkServiceImpl.class);

	/**
	 * 上线链接
	 */
	public LoadingCache<String, List<Link>> onlineLinkCache;

	@Autowired
	private LinkMapper linkMapper;

	@Autowired
	private OperateLogService operateLogService;

	@Override
	protected LinkMapper getBaseMapper() {
		return linkMapper;
	}

	@PostConstruct
	public void initCache() {
		logger.info("初始化上线链接系统配置。。。");
		onlineLinkCache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.HOURS)
				.build(new CacheLoader<String, List<Link>>() {
					@Override
					public List<Link> load(String key) throws Exception {
						logger.info("onlineLinkCache加载上线链接，key={}", key);
						QueryLink queryLink = new QueryLink();
						queryLink.setStatus(LinkStatusEnum.STATUS_NORMAL.getCode());
						queryLink.setOrderCondition("sort asc");
						List<Link> findPageable = linkMapper.findPageable(queryLink);
						return findPageable;
					}
				});
	}

	/**
	 * 新增链接，链接默认为未启用状态，新增操作日志
	 */
	@Override
	public void addLink(Link link) {
		logger.info("新增链接，link:{}", link);
		ValidationUtils.validateEntity(link);
		link.setStatus(LinkStatusEnum.STATUS_BLOCKED.getCode());
		Date now = new Date();
		User loginUser = TokenUtils.getLoginUser();
		link.setCreateTime(now);
		link.setCreateUser(loginUser.getId());
		insertSelective(link);
		String content = String.format("链接名：%s，链接地址：%s", link.getLinkName(), link.getLinkUrl());
		operateLogService.addOperateLog(content, OperateLogTypeEnum.LINK_ADD);
	}

	/**
	 * 编辑链接，新增操作日志
	 */
	@Override
	public void editLink(Link link) {
		logger.info("编辑链接，link:{}", link);
		ValidationUtils.validateEntity(link);
		Link linkInDb = selectByPrimaryKey(link.getId());
		if (linkInDb == null) {
			logger.error("根据id未查询到链接信息,id={}", link.getId());
			throw new BusinessException("链接不存在");
		}
		Date now = new Date();
		link.setUpdateTime(now);
		link.setUpdateUser(TokenUtils.getLoginUserId());
		updateByPrimaryKeySelective(link);
		// 记录日志
		String changedFields = ValidationUtils.getChangedFields(linkInDb, link);
		operateLogService.addOperateLog("id=" + link.getId() + changedFields, OperateLogTypeEnum.LINK_EDIT);
		onlineLinkCache.refresh(MyConstant.ONLINE_LINK_LIST);
	}
	
	/**
	 * 删除链接
	 */
	@Override
	public void deleteLink(Long id) {
		Link linkInDb = selectByPrimaryKey(id);
		if (linkInDb == null) {
			logger.error("根据id未查询到链接信息,id={}", id);
			throw new BusinessException("链接不存在");
		}
		deleteByPrimaryKey(id);
		operateLogService.addOperateLog(linkInDb.toString(), OperateLogTypeEnum.LINK_DELETE);
		onlineLinkCache.refresh(MyConstant.ONLINE_LINK_LIST);
	}

	@Override
	public String changeLinkStatus(Long id, Integer status) {
		String message = null;
		Link linkInDb = selectByPrimaryKey(id);
		if (linkInDb == null) {
			logger.error("根据id未查询到链接信息,id={}", id);
			throw new BusinessException("链接不存在");
		}
		Link updateLink = new Link();
		updateLink.setId(id);
		updateLink.setStatus(status);
		updateLink.setUpdateTime(new Date());
		updateLink.setUpdateUser(TokenUtils.getLoginUserId());
		if (UserStatusEnum.STATUS_BLOCKED.getCode().equals(status)) {
			message = "下线链接成功";
			String content = "下线链接，链接id=" + id;
			updateByPrimaryKeySelective(updateLink);
			operateLogService.addOperateLog(content, OperateLogTypeEnum.LINK_ENABLE);
			onlineLinkCache.refresh(MyConstant.ONLINE_LINK_LIST);
		} else if (UserStatusEnum.STATUS_NORMAL.getCode().equals(status)) {
			message = "上线链接成功";
			String content = "上线链接，链接id=" + id;
			updateByPrimaryKeySelective(updateLink);
			operateLogService.addOperateLog(content, OperateLogTypeEnum.LINK_DISABLE);
			onlineLinkCache.refresh(MyConstant.ONLINE_LINK_LIST);
		} else {
			message = "操作错误，请稍后重试";
		}
		return message;
	}

	@Override
	public List<Link> getOnlineLinkList() {
		try {
			return (List<Link>) onlineLinkCache.get(MyConstant.ONLINE_LINK_LIST);
		} catch (ExecutionException e) {
			logger.error("获取上线链接失败",e);
		}
		return Lists.newArrayList();
	}
}
