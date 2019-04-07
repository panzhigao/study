package com.pan.service.impl;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.common.exception.BusinessException;
import com.pan.entity.ArticleCategory;
import com.pan.mapper.ArticleCategoryMapper;
import com.pan.service.AbstractBaseService;
import com.pan.service.ArticleCategoryService;
import com.pan.service.OperateLogService;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;

@Service
public class ArticleCategoryServiceImpl extends AbstractBaseService<ArticleCategory, ArticleCategoryMapper> implements ArticleCategoryService{
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleCategoryServiceImpl.class);
	
	@Autowired
	private ArticleCategoryMapper articleCategoryMapper;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	protected ArticleCategoryMapper getBaseMapper() {
		return articleCategoryMapper;
	}
	
	/**
	 * 新增文章分类，记录日志
	 */
	@Override
	public void addArticleCategory(ArticleCategory articleCategory) {
		ValidationUtils.validateEntity(articleCategory);
		articleCategory.setCreateTime(new Date());
		articleCategory.setCreateUserId(TokenUtils.getLoginUserId());
		articleCategoryMapper.insertSelective(articleCategory);
		operateLogService.addOperateLog("分类名称"+articleCategory.getCategoryName(), OperateLogTypeEnum.ARTICLE_CATEGORY_ADD);
	}
	
	/**
	 * 删除文章分类
	 */
	@Override
	public int deleteByArticleCategoryId(Long articleCategoryId) {
		ArticleCategory articleCategory = articleCategoryMapper.selectByPrimaryKey(articleCategoryId);
		if(articleCategory==null){
			logger.error("根据文章分类id未查询到信息，id={}",articleCategoryId);
			throw new BusinessException("该文章分类不存在");
		}
		int deleteByPrimaryKey = articleCategoryMapper.deleteByPrimaryKey(articleCategoryId);
		if(deleteByPrimaryKey<=0){
			throw new BusinessException("删除文章分类信息失败");
		}
		operateLogService.addOperateLog(articleCategory.toString(), OperateLogTypeEnum.ARTICLE_CATEGORY_DELETE);
		return deleteByPrimaryKey;
	}

	@Override
	public void updateArticleCategory(ArticleCategory articleCategory) {
		if(articleCategory.getId()==null){
			throw new BusinessException("分类ID不能为空");
		}
		ValidationUtils.validateEntity(articleCategory);
		ArticleCategory articleCategoryInDb = articleCategoryMapper.selectByPrimaryKey(articleCategory.getId());
		if(articleCategoryInDb==null){
			logger.error("根据文章分类id未查询到信息，id={}",articleCategory.getId());
			throw new BusinessException("该文章分类不存在");
		}
		String changedFields = ValidationUtils.getChangedFields(articleCategoryInDb, articleCategory);
		operateLogService.addOperateLog(changedFields, OperateLogTypeEnum.ARTICLE_CATEGORY_EDIT);
	}
}
