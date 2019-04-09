package com.pan.service.impl;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.pan.common.enums.ArticleCategoryStatusEnum;
import com.pan.common.enums.UserStatusEnum;
import com.pan.entity.SystemConfig;
import com.pan.query.QueryArticleCategory;
import com.pan.util.TransFieldUtils;
import org.apache.commons.lang3.StringUtils;
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

import javax.annotation.PostConstruct;

/**
 * 文章分类
 * @author panzhigao
 */
@Service
public class ArticleCategoryServiceImpl extends AbstractBaseService<ArticleCategory, ArticleCategoryMapper> implements ArticleCategoryService{
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleCategoryServiceImpl.class);

	public static LoadingCache<Long, ArticleCategory> categoryCache;

	@Autowired
	private ArticleCategoryMapper articleCategoryMapper;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	protected ArticleCategoryMapper getBaseMapper() {
		return articleCategoryMapper;
	}

	@PostConstruct
	public void init(){
		logger.info("初始化文章分类缓存。。。");
		categoryCache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).build(new CacheLoader<Long,ArticleCategory>() {
			@Override
			public ArticleCategory load(Long key) throws Exception {
				logger.info("初始化文章分类缓存，key={}",key);
				ArticleCategory articleCategory = articleCategoryMapper.selectByPrimaryKey(1L);
				if(articleCategory==null){
					articleCategory=new ArticleCategory();
					articleCategory.setCategoryName("");
				}
				return articleCategory;
			}
		});
	}
	
	/**
	 * 新增文章分类，
	 * 校验是否有同名分类，如果有，不允许添加
	 * 记录日志
	 */
	@Override
	public void addArticleCategory(ArticleCategory articleCategory) {
		ValidationUtils.validateEntity(articleCategory);
		//校验分类名称唯一性
		checkUniqueByCategoryName(articleCategory.getCategoryName());
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

	/**
	 * 校验分类名称唯一性
	 * @param categoryName
	 */
	private void checkUniqueByCategoryName(String categoryName){
		QueryArticleCategory queryArticleCategory=new QueryArticleCategory();
		queryArticleCategory.setCategoryName(categoryName);
		int i = articleCategoryMapper.countByParams(queryArticleCategory);
		if(i>0){
			throw new BusinessException("已存在同名的文章分类，不能重复添加");
		}
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
		//修改了分类名称
		if(!StringUtils.equals(articleCategory.getCategoryName(),articleCategoryInDb.getCategoryName())){
			checkUniqueByCategoryName(articleCategory.getCategoryName());
		}
		articleCategoryMapper.updateByPrimaryKeySelective(articleCategory);
		String changedFields = ValidationUtils.getChangedFields(articleCategoryInDb, articleCategory);
		operateLogService.addOperateLog(changedFields, OperateLogTypeEnum.ARTICLE_CATEGORY_EDIT);
	}

	/**
	 * 修改文章分类状态
	 * @param articleCategoryId 分类id
	 * @param status
	 * @return 返回状态内容
	 */
	@Override
	public String changeCategoryStatus(Long articleCategoryId, Integer status) {
		String message;
		ArticleCategory articleCategory = articleCategoryMapper.selectByPrimaryKey(articleCategoryId);
		if(articleCategory==null){
			logger.error("根据文章分类id未查询到信息，id={}",articleCategoryId);
			throw new BusinessException("文章分类不存在");
		}
		ArticleCategory updateArticleCategory=new ArticleCategory();
		Long loginUserId = TokenUtils.getLoginUserId();
		updateArticleCategory.setId(articleCategoryId);
		updateArticleCategory.setStatus(status);
		updateArticleCategory.setUpdateTime(new Date());
		updateArticleCategory.setUpdateUserId(loginUserId);
		if (ArticleCategoryStatusEnum.STATUS_BLOCKED.getCode().equals(status)) {
			message = "下线文章分类成功";
			articleCategoryMapper.updateByPrimaryKeySelective(updateArticleCategory);
			String content="下线文章分类："+articleCategory.getCategoryName();
			operateLogService.addOperateLog(content, OperateLogTypeEnum.ARTICLE_CATEGORY_DISABLE);
		} else if (UserStatusEnum.STATUS_NORMAL.getCode().equals(status)) {
			message = "上线文章分类成功";
			articleCategoryMapper.updateByPrimaryKeySelective(updateArticleCategory);
			String content="上线文章分类"+articleCategory.getCategoryName();
			operateLogService.addOperateLog(content, OperateLogTypeEnum.ARTICLE_CATEGORY_ENABLE);
		} else {
			message = "操作错误，请稍后重试";
		}
		return message;
	}

	@Override
	public String getCategoryNameByIdThroughCache(Long articleCategoryId) {
		try {
			ArticleCategory articleCategory = categoryCache.get(articleCategoryId);
			return articleCategory.getCategoryName();
		} catch (ExecutionException e) {
			logger.error("通过loadingCache获取分类名称失败，id={}",articleCategoryId);
		}
		return "";
	}
}
