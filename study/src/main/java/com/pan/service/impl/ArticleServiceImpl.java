package com.pan.service.impl;

import java.util.*;
import com.pan.service.*;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import com.pan.common.constant.EsConstant;
import com.pan.common.constant.MyConstant;
import com.pan.common.enums.ArticleOperateEnum;
import com.pan.common.enums.ArticleStatusEnum;
import com.pan.common.enums.ArticleTypeEnum;
import com.pan.common.enums.CheckTypeEnum;
import com.pan.common.enums.MessageTypeEnum;
import com.pan.common.exception.BusinessException;
import com.pan.dto.ArticleDTO;
import com.pan.entity.Article;
import com.pan.entity.ArticleCheck;
import com.pan.entity.Message;
import com.pan.entity.User;
import com.pan.mapper.ArticleMapper;
import com.pan.query.QueryArticle;
import com.pan.util.IdUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;
import com.pan.util.MessageUtils;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;

/**
 * 
 * @author Administrator
 * 
 */
@Service
public class ArticleServiceImpl extends AbstractBaseService<Article, ArticleMapper> implements ArticleService {

	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	/**
	 * 缓存时间,单位秒
	 */
	private static final int CACHE_SECONDS=1800;
	/**
	 * typename
	 */
	public static final String TYPE_NAME="article";
	
	@Autowired
	private EsClientService esClientService;

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private ArticleCheckService articleCheckService;

	@Override
	protected ArticleMapper getBaseMapper() {
		return articleMapper;
	}
	
	/**
	 * 校验当前操作码状态是否正常 1-草稿，2-审核中
	 * 
	 * @param status
	 * @return
	 */
	private boolean checkOpearteStatus(Integer status) {
		if (ArticleStatusEnum.SKETCH.getCode().equals(status) || ArticleStatusEnum.IN_CHECK.getCode().equals(status)) {
			return true;
		}
		return false;
	}

	/**
	 * 校验文章信息
	 * 
	 * @param article
	 */
	private void checkArticle(Article article) {
		ValidationUtils.validateEntity(article);
		if (!checkOpearteStatus(article.getStatus())) {
			logger.error("html页面被修改,方法参数错误");
			throw new BusinessException("文章状态有误,请刷新页面");
		}
	}

	/**
	 * 新增保存文章，如果是发布保存文章，新增一条审核记录，文章状态为待审核
	 * 如果是保存草稿，不新增审核记录，文章状态为草稿
	 */
	@Override
	public void saveArticle(Article article) {
		User loginUser = TokenUtils.getLoginUser();
		article.setUserId(loginUser.getId());
		article.setUsername(loginUser.getUsername());
		checkArticle(article);
		// 默认草稿状态
		if (article.getStatus()==null) {
			logger.info("文章无状态，默认设置为草稿状态");
			article.setStatus(ArticleStatusEnum.SKETCH.getCode());
		}
		article.setCreateTime(new Date());
		article.setUpdateTime(new Date());
		article.setId(IdUtils.generateId());
		article.setType(ArticleTypeEnum.TYPE_ARTICLE.getCode());
		articleMapper.insertSelective(article);
		//发布文章,新增审核记录
		if(ArticleStatusEnum.IN_CHECK.getCode().equals(article.getStatus())){
			ArticleCheck articleCheck = new ArticleCheck();
			articleCheck.setUserId(loginUser.getId());
			articleCheck.setUsername(loginUser.getUsername());
			articleCheck.setArticleId(article.getId());
			articleCheck.setTitle(article.getTitle());
			articleCheck.setContent(article.getContent());
			articleCheck.setCategoryId(article.getCategoryId());
			articleCheck.setCheckType(CheckTypeEnum.CREATE.getCode());
			articleCheckService.addArticleCheck(articleCheck);
		}
	}

	@Override
	public List<Article> findListByUserId(Long userId) {
		logger.info("用户id为:{}", userId);
		return articleMapper.findListByUserId(userId);
	}

	/**
	 * 通过文章id获取文章信息，并校验文章的userId是否与传入的userId一致
	 * @param userId
	 * @param articleId 
	 */
	@Override
	public Article getAndCheckByUserId(Long userId, Long articleId) {
		logger.info("查询文章信息,用户id为:{},文章id为:{}", userId, articleId);
		if (userId==null || articleId==null) {
			logger.info("查询文章详细信息参数有误,用户id为:{},文章id为:{}", userId, articleId);
		}
		Article article = getAndCheckByArticleId(articleId);
		if(!userId.equals(article.getUserId())){
			throw new BusinessException("您无权查看该文章信息");
		}
		return article;
	}

	/**
	 * 更新文章信息 要校验当前文章是否是当前登录用户下的文章
	 * 发布中文章不能修改
	 */
	@Override
	public void updateArticle(Article article) {
		logger.info("前台传来的文章信息,{}", article);
		User loginUser = TokenUtils.getLoginUser();
		article.setUserId(loginUser.getId());
		article.setUsername(loginUser.getUsername());
		// 校验前台传来的数据
		checkArticle(article);
		Long articleId = article.getId();
		Article articleInDb = getAndCheckByUserId(loginUser.getId(),articleId);
		if (ArticleTypeEnum.TYPE_SYSTEM_NOTICE.getCode().equals(articleInDb.getType())) {
			logger.error("系统公告不可修改", articleInDb);
			throw new BusinessException("系统公告不可修改");
		}
		if (ArticleStatusEnum.IN_CHECK.getCode().equals(articleInDb.getStatus())) {
			logger.error("当前文章处于审核中,不可修改", articleInDb);
			throw new BusinessException("当前文章处于审核中,不可修改");
		}
		article.setUpdateTime(new Date());
		articleMapper.updateByPrimaryKeySelective(article);
		//文章处于审核状态，新增审核记录
		if(ArticleStatusEnum.IN_CHECK.getCode().equals(article.getStatus())){
			//新增审核记录
			ArticleCheck articleCheck = new ArticleCheck();
			articleCheck.setUserId(loginUser.getId());
			articleCheck.setUsername(loginUser.getUsername());
			articleCheck.setArticleId(article.getId());
			articleCheck.setTitle(article.getTitle());
			articleCheck.setContent(article.getContent());
			articleCheck.setCategoryId(article.getCategoryId());
			articleCheck.setCheckType(CheckTypeEnum.UPDATE.getCode());
			articleCheckService.addArticleCheck(articleCheck);
		}
	}


	@Override
	public Article getAndCheckByArticleId(Long articleId) {
		Article article = selectByPrimaryKey(articleId);
		if(article==null){
			logger.info("根据文章id{}未查询到文章信息", articleId);
			throw new BusinessException("文章不存在");
		}
		return article;
	}

	@Override
	public void deleteArticle(Long articleId, Long userId) {
		if (articleId==null) {
			throw new BusinessException("文章id不能为空");
		}
		Article article = getAndCheckByUserId(userId, articleId);
		if (article == null) {
			logger.info("根据文章id{}未查询到文章信息", articleId);
			throw new BusinessException("文章不存在");
		}
		if (ArticleStatusEnum.IN_CHECK.getCode().equals(article.getStatus())) {
			logger.info("审核中文章不能删除");
			throw new BusinessException("审核中文章不能删除");
		}
		if (ArticleStatusEnum.PUBLIC_SUCCESS.getCode().equals(article.getStatus())) {
			logger.info("发布中文章不能删除");
			throw new BusinessException("发布中文章不能删除");
		}
		int num = this.articleMapper.deleteByUserIdAndArticleId(userId, articleId);
		if (num != 1) {
			logger.info("删除文章失败");
			throw new BusinessException("删除文章失败");
		}
	}

	@Override
	public int getCount(QueryArticle queryArticle) {
		logger.info("查询文章条数条件：{}", JsonUtils.toJson(queryArticle));
		return articleMapper.countByParams(queryArticle);
	}


	@Override
	public int updateArticleCommentCount(Long articleId, Integer commentCount) {
		Article article = new Article();
		article.setId(articleId);
		article.setCommentCount(commentCount);
		return articleMapper.updateByPrimaryKeySelective(article);
	}

	@Override
	public int updateArticleViewCount(Long articleId, Integer viewCount) {
		Article article = new Article();
		article.setId(articleId);
		article.setViewCount(viewCount);
		return articleMapper.updateByPrimaryKeySelective(article);
	}
	
	/**
	 * 保存系统信息，同时向用户发送通知，除了自己
	 */
	@Override
	public void saveSystemMessage(Article article) {
		ValidationUtils.validateEntity(article);
		//状态为发布成功
		article.setStatus(ArticleStatusEnum.PUBLIC_SUCCESS.getCode());
		article.setCreateTime(new Date());
		article.setPublishTime(new Date());
		article.setId(IdUtils.generateId());
		//系统消息文章
		article.setType(ArticleTypeEnum.TYPE_SYSTEM_NOTICE.getCode());
		Message message = new Message();
		message.setMessageType(MessageTypeEnum.NOTICE.getCode());
		message.setContentName(article.getTitle());
		message.setCommentContent(article.getContent());
		Long loginUserId = TokenUtils.getLoginUserId();
		Set<Long> set = new HashSet<Long>();
		set.add(loginUserId);
		MessageUtils.sendMessageToAllExceptionUser(JsonUtils.toJson(message), set);
		articleMapper.insertSelective(article);
	}

	/**
	 * 根据条件查询es中的文章信息
	 */
	@Override
	public List<ArticleDTO> queryFromEsByCondition(QueryArticle queryArticle) {
		List<ArticleDTO> list=new ArrayList<>();
		try {
			list=esClientService.queryByParamsWithHightLight(EsConstant.DEFAULT_INDEX_NAME, TYPE_NAME, queryArticle, true, ArticleDTO.class);
			list.forEach(item->{
				item.setCategoryName(ArticleCategoryServiceImpl.getCategoryNameByIdThroughCache(item.getCategoryId()));
			});
		} catch (Exception e) {
			logger.error("查询文章es信息出错,查询条件{}",queryArticle);
		}
		return list;
	}

	/**
	 * 通过文章标题查询文章信息
	 * 查询es
	 */
	@Override
	public List<ArticleDTO> searchArticleByTitle(String title) {
		QueryArticle queryArticleVO = new QueryArticle();
		queryArticleVO.setTitle(title);
		return queryFromEsByCondition(queryArticleVO);
	}

	/**
	 * 获取文章最大置顶值
	 * @return
	 */
	@Override
	public int getMaxStick() {
		return articleMapper.getMaxStick();
	}
		
	/**
	 * 文章置顶/加精
	 */
	@Override
	public void setArticle(Long articleId, Integer stick, Integer highQuality) {
		Article articleInDb = selectByPrimaryKey(articleId);
		if(articleInDb==null){
			throw new BusinessException("文章不存在");
		}
		boolean flag=false;
		Article article=new Article();
		article.setId(articleInDb.getId());
		//取消置顶
		if(ArticleOperateEnum.CANCEL_STICK.getCode().equals(stick)){
			article.setStick(0);
			flag=true;
		//置顶	
		}else if(ArticleOperateEnum.STICK.getCode().equals(stick)){
			int maxStick = getMaxStick();
			maxStick++;
			article.setStick(maxStick);
			flag=true;
		}
		//加精或取消加精
		if(ArticleOperateEnum.NOT_HIGH_QUALITY.getCode().equals(highQuality)||
				ArticleOperateEnum.HIGH_QUALITY.getCode().equals(highQuality)){
			flag=true;
			article.setHighQuality(highQuality);
		}
		if(!flag){
			throw new BusinessException("参数有误");
		}
		article.setUpdateTime(new Date());
		articleMapper.updateByPrimaryKeySelective(article);
		//清除文章缓存
		JedisUtils.delete(MyConstant.ARTICLE_PREFIX+article.getId());
	}
	
	/**
	 * 获取文章信息，校验是否有当前文章信息的权限
	 * 先从redis中读取，如果不存在，则从数据库读取，如果数据库存在且文章已发布，加入redis缓存，如果数据库不存在，添加一个默认缓存，缓存过期时间60秒
	 * @param queryArticleVO
	 * @return
	 */
	@Override
	public Article checkAndGetArticle(QueryArticle queryArticleVO) {
		Jedis jedis =null;
		Article article;
		try {
			jedis= JedisUtils.getJedis();
			//存在缓存
			if(jedis.exists(MyConstant.ARTICLE_PREFIX+queryArticleVO.getArticleId())){
				String string = jedis.get(MyConstant.ARTICLE_PREFIX+queryArticleVO.getArticleId());
				logger.debug("从缓存读取数据,{}",string);
				if(MyConstant.REDIS_NULL.equals(string)){
					throw new BusinessException("文章不存在");
				}else{
					article=(Article) JsonUtils.fromJson(string, Article.class);
					//如果文章不为发布状态，且不属于当前用户的文章，不能浏览
					if(!ArticleStatusEnum.PUBLIC_SUCCESS.getCode().equals(article.getStatus())&& !queryArticleVO.getUserId().equals(article.getUserId())){
						logger.error("文章不属于当前用户",queryArticleVO.getArticleId());
						throw new BusinessException("文章不存在");
					}
				}
			}else{//不存在缓存
				article=articleMapper.selectByPrimaryKey(queryArticleVO.getArticleId());
				logger.debug("从数据库读取文章数据,{}",article);
				if(article==null){
					jedis.setex(MyConstant.ARTICLE_PREFIX+queryArticleVO.getArticleId(),CACHE_SECONDS,MyConstant.REDIS_NULL);
					throw new BusinessException("文章不存在");
				}
				if(!ArticleStatusEnum.PUBLIC_SUCCESS.getCode().equals(article.getStatus())&&!queryArticleVO.getUserId().equals(article.getUserId())){
					logger.error("文章不属于当前用户",queryArticleVO.getArticleId());
					throw new BusinessException("文章不存在");
				}
				if(ArticleStatusEnum.PUBLIC_SUCCESS.getCode().equals(article.getStatus())){					
					jedis.setex(MyConstant.ARTICLE_PREFIX+queryArticleVO.getArticleId(),CACHE_SECONDS,JsonUtils.toJson(article));
				}
			}	
		}catch(BusinessException ex){
			throw new BusinessException(ex.getMessage());
		}catch (Exception e) {
			logger.error("获取文章信息失败",e);
			throw new BusinessException("获取文章信息失败");
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return article;
	}

	/**
	 * 分页查询
	 * @param queryArticle
	 * @return
	 */
	@Override
	public Map<String, Object> findDTOPageableMap(QueryArticle queryArticle) {
		Map<String, Object> pageData = new HashMap<>(4);
		List<ArticleDTO> list = new ArrayList<>();
		int total=countByParams(queryArticle);
		if(total>0){
			list=articleMapper.findDTOPageable(queryArticle);
			for(ArticleDTO articleDTO:list){
				String name = ArticleCategoryServiceImpl.getCategoryNameByIdThroughCache(articleDTO.getCategoryId());
				articleDTO.setCategoryName(name);
			}
		}
		pageData.put("data", list);
		pageData.put("total", total);
		pageData.put("code", "200");
		pageData.put("msg", "");
		return pageData;
	}
	
	
	@Override
	public boolean updateArticleInEs(Long articleId) {
		List<ArticleDTO> DTOList = findByArticleId(articleId);
		if(CollectionUtils.isEmpty(DTOList)){
			logger.info("文章数据不存在，id={}",articleId);
			return false;
		}
		ArticleDTO article = DTOList.get(0);
		Map<String, Object> newContent = JsonUtils.objectToMap(article);
		QueryArticle queryArticle=new QueryArticle();
		queryArticle.setArticleId(articleId);
		List<ArticleDTO> list = queryFromEsByCondition(queryArticle);
		if(CollectionUtils.isNotEmpty(list)){
			String id=list.get(0).getId().toString();
			esClientService.updateRecord(EsConstant.DEFAULT_INDEX_NAME, TYPE_NAME, id, newContent);
		}else{
			createArticleEs(articleId);
		}
		return false;
	}

	@Override
	public boolean createArticleEs(Long articleId) {
		List<ArticleDTO> list = findByArticleId(articleId);
		if(CollectionUtils.isEmpty(list)){
			logger.info("文章数据不存在，id={}",articleId);
			return false;
		}
		try {
			esClientService.createIndex(EsConstant.DEFAULT_INDEX_NAME, TYPE_NAME, list.get(0));
			return true;
		} catch (Exception e) {
			logger.error("创建文章索引失败，id={}",articleId,e);
		}
		return false;
	}

	@Override
	public List<ArticleDTO> findByArticleId(Long articleId) {
		QueryArticle queryArticle=new QueryArticle();
		queryArticle.setArticleId(articleId);
		return articleMapper.findDTOPageable(queryArticle);
	}
}
