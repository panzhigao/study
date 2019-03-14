package com.pan.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
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
import com.pan.service.AbstractBaseService;
import com.pan.service.ArticleCheckService;
import com.pan.service.ArticleService;
import com.pan.service.EsClientService;
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
		article.setUserId(loginUser.getUserId());
		article.setUsername(loginUser.getUsername());
		checkArticle(article);
		// 默认草稿状态
		if (article.getStatus()==null) {
			logger.info("文章无状态，默认设置为草稿状态");
			article.setStatus(ArticleStatusEnum.SKETCH.getCode());
		}
		article.setCreateTime(new Date());
		article.setUpdateTime(new Date());
		article.setArticleId(IdUtils.generateArticleId());
		article.setType(ArticleTypeEnum.TYPE_ARTICLE.getCode());
		articleMapper.insertSelective(article);
		//发布文章,新增审核记录
		if(ArticleStatusEnum.IN_CHECK.getCode().equals(article.getStatus())){
			ArticleCheck articleCheck = new ArticleCheck();
			articleCheck.setUserId(loginUser.getUserId());
			articleCheck.setUsername(loginUser.getUsername());
			articleCheck.setArticleId(article.getArticleId());
			articleCheck.setTitle(article.getTitle());
			articleCheck.setContent(article.getContent());
			articleCheck.setCheckType(CheckTypeEnum.CREATE.getCode());
			articleCheckService.addArticleCheck(articleCheck);
		}
	}

	@Override
	public List<Article> findListByUserId(String userId) {
		logger.info("用户id为:{}", userId);
		return articleMapper.findListByUserId(userId);
	}

	/**
	 * 通过文章id获取文章信息，并校验文章的userId是否与传入的userId一致
	 * @param userId
	 * @param articleId 
	 */
	@Override
	public Article getAndCheckByUserId(String userId, String articleId) {
		// TODO 修改判断
		logger.info("查询文章信息,用户id为:{},文章id为:{}", userId, articleId);
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(articleId)) {
			logger.info("查询文章详细信息参数有误,用户id为:{},文章id为:{}", userId, articleId);
		}
		Article article = getAndCheckByArticleId(articleId);
		if(!StringUtils.equals(userId, article.getUserId())){
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
		article.setUserId(loginUser.getUserId());
		article.setUsername(loginUser.getUsername());
		// 校验前台传来的数据
		checkArticle(article);
		String articleId = article.getArticleId();
		Article articleInDb = getAndCheckByUserId(loginUser.getUserId(),articleId);
		if (ArticleTypeEnum.TYPE_SYSTEM_NOTICE.getCode().equals(articleInDb.getType())) {
			logger.error("系统公告不可修改", articleInDb);
			throw new BusinessException("系统公告不可修改");
		}
		if (ArticleStatusEnum.IN_CHECK.getCode().equals(articleInDb.getStatus())) {
			logger.error("当前文章处于审核中,不可修改", articleInDb);
			throw new BusinessException("当前文章处于审核中,不可修改");
		}
		article.setUpdateTime(new Date());
		articleMapper.updateArticleByArticleId(article);
		//文章处于审核状态，新增审核记录
		if(ArticleStatusEnum.IN_CHECK.getCode().equals(article.getStatus())){
			//新增审核记录
			ArticleCheck articleCheck = new ArticleCheck();
			articleCheck.setUserId(loginUser.getUserId());
			articleCheck.setUsername(loginUser.getUsername());
			articleCheck.setArticleId(article.getArticleId());
			articleCheck.setTitle(article.getTitle());
			articleCheck.setContent(article.getContent());
			articleCheck.setCheckType(CheckTypeEnum.UPDATE.getCode());
			articleCheckService.addArticleCheck(articleCheck);
		}
	}

	@Override
	public Article getByArticleId(String articleId) {
		logger.info("查询文章信息,文章id为:{}", articleId);
		if (StringUtils.isBlank(articleId)) {
			throw new BusinessException("文章id不能为空");
		}
		return articleMapper.findByArticleId(articleId);
	}
	
	@Override
	public Article getAndCheckByArticleId(String articleId) {
		Article article = getByArticleId(articleId);
		if(article==null){
			logger.info("根据文章id{}未查询到文章信息", articleId);
			throw new BusinessException("文章不存在");
		}
		return article;
	}

	@Override
	public void deleteArticle(String articleId, String userId) {
		if (StringUtils.isBlank(articleId)) {
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
	public Article findByArticleIdAndStatus(String articleId, Integer status) {
		QueryArticle queryArticle=new QueryArticle();
		queryArticle.setArticleId(articleId);
		queryArticle.setStatus(status);
		List<Article> list = articleMapper.findPageable(queryArticle);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int updateArticleCommentCount(String articleId, Integer commentCount) {
		Article article = new Article();
		article.setArticleId(articleId);
		article.setCommentCount(commentCount);
		return articleMapper.updateArticleByArticleId(article);
	}

	@Override
	public int updateArticleViewCount(String articleId, Integer viewCount) {
		Article article = new Article();
		article.setArticleId(articleId);
		article.setViewCount(viewCount);
		return articleMapper.updateArticleByArticleId(article);
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
		article.setArticleId(IdUtils.generateArticleId());
		//系统消息文章
		article.setType(ArticleTypeEnum.TYPE_SYSTEM_NOTICE.getCode());
		Message message = new Message();
		message.setMessageType(MessageTypeEnum.NOTICE.getCode());
		message.setContentName(article.getTitle());
		message.setCommentContent(article.getContent());
		String loginUserId = TokenUtils.getLoginUserId();
		Set<String> set = new HashSet<String>();
		set.add(loginUserId);
		MessageUtils.sendMessageToAllExceptionUser(JsonUtils.toJson(message), set);
		articleMapper.insertSelective(article);
	}

	/**
	 * 根据条件查询es中的文章信息
	 */
	@Override
	public List<ArticleDTO> queryFromEsByCondition(QueryArticle queryArticle) {
		return esClientService.queryByParamsWithHightLight("article", "doc", queryArticle, true, ArticleDTO.class);
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

//	@Override
//	public List<Article> findByCondition(QueryArticle queryArticle) {
//		return articleMapper.findPageable(queryArticle);
//	}

	@Override
	public int getMaxStick() {
		return articleMapper.getMaxStick();
	}
		
	/**
	 * 文章置顶/加精
	 */
	@Override
	public void setArticle(String articleId, Integer stick, Integer highQuality) {
		Article articleInDb = getByArticleId(articleId);
		if(articleInDb==null){
			throw new BusinessException("文章不存在");
		}
		boolean flag=false;
		Article article=new Article();
		article.setArticleId(articleInDb.getArticleId());
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
		articleMapper.updateArticleByArticleId(article);
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
		Article article=null;
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
					if(!ArticleStatusEnum.PUBLIC_SUCCESS.getCode().equals(article.getStatus())&&!StringUtils.equals(queryArticleVO.getUserId(),article.getUserId())){
						logger.error("文章不属于当前用户",queryArticleVO.getArticleId());
						throw new BusinessException("文章不存在");
					}
				}
			}else{//不存在缓存
				article=articleMapper.findByArticleId(queryArticleVO.getArticleId());
				logger.debug("从数据库读取文章数据,{}",article);
				if(article==null){
					jedis.setex(MyConstant.ARTICLE_PREFIX+queryArticleVO.getArticleId(),CACHE_SECONDS,MyConstant.REDIS_NULL);
					throw new BusinessException("文章不存在");
				}
				if(!ArticleStatusEnum.PUBLIC_SUCCESS.getCode().equals(article.getStatus())&&!StringUtils.equals(queryArticleVO.getUserId(),article.getUserId())){
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

}
