package com.pan.service.impl;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.exception.BusinessException;
import com.pan.entity.Comment;
import com.pan.entity.Praise;
import com.pan.mapper.CommentMapper;
import com.pan.mapper.PraiseMapper;
import com.pan.query.QueryPraise;
import com.pan.service.AbstractBaseService;
import com.pan.service.PraiseService;
import com.pan.util.IdUtils;
import com.pan.util.ValidationUtils;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class PraiseServiceImpl extends AbstractBaseService<Praise, PraiseMapper> implements PraiseService{
	
	private static final Logger logger=LoggerFactory.getLogger(PraiseServiceImpl.class);
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private PraiseMapper praiseMapper;
	
	@Override
	protected PraiseMapper getBaseMapper() {
		return praiseMapper;
	}
	
	@Override
	public void addPraise(Praise praise) {
		ValidationUtils.validateEntity(praise);
		Comment commentInDb=commentMapper.findByCommentId(praise.getCommentId());
		if(commentInDb==null){
			logger.error("评论信息不存在:{}",praise.getCommentId());
			throw new BusinessException("评论信息不存在");
		}
		QueryPraise queryPraise=new QueryPraise();
		queryPraise.setUserId(praise.getUserId());
		queryPraise.setCommentId(praise.getCommentId());
		int countByParams = praiseMapper.countByParams(queryPraise);
		if(countByParams>0){
			throw new BusinessException("已经点过赞了");
		}
		praise.setCreateTime(new Date());
		praise.setPraiseId(IdUtils.generatePraiseId());
		praiseMapper.insertSelective(praise);
		commentMapper.updatePraiseCounts(praise.getCommentId());
	}
}
