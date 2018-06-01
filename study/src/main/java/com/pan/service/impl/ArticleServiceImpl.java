package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.dto.ArticleDTO;
import com.pan.entity.Article;
import com.pan.entity.ArticleCheck;
import com.pan.entity.Message;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.mapper.ArticleMapper;
import com.pan.service.ArticleCheckService;
import com.pan.service.ArticleService;
import com.pan.service.EsClientService;
import com.pan.service.UserExtensionService;
import com.pan.util.IdUtils;
import com.pan.util.JsonUtils;
import com.pan.util.MessageUtils;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;
import com.pan.vo.QueryArticleVO;

/**
 * 
 * @author Administrator
 * 
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Autowired
	private EsClientService esClientService;

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private ArticleCheckService articleCheckService;
	
	@Autowired
	private UserExtensionService userExtensionService;
	
	/**
	 * 校验当前操作码状态是否正常 1-草稿，2-审核中
	 * 
	 * @param status
	 * @return
	 */
	private boolean checkOpearteStatus(String status) {
		if (Article.STATUS_SKETCH.equals(status) || Article.STATUS_IN_REVIEW.equals(status)) {
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
	 * 新增文章
	 * 1.文章表新增一条数据
	 * 2.文章审核表里新增一条数据
	 */
	@Override
	public void saveArticle(Article article) {
		User loingUser = TokenUtils.getLoginUser();
		article.setUserId(loingUser.getUserId());
		checkArticle(article);
		// 默认草稿状态
		if (StringUtils.isBlank(article.getStatus())) {
			logger.info("文章无状态，默认设置为草稿状态");
			article.setStatus(Article.STATUS_SKETCH);
		}
		article.setCreateTime(new Date());
		article.setUpdateTime(new Date());
		article.setArticleId(IdUtils.generateArticleId());
		article.setType("1");
		articleMapper.saveArticle(article);
		ArticleCheck articleCheck = new ArticleCheck();
		articleCheck.setArticleId(article.getArticleId());
		articleCheck.setTitle(article.getTitle());
		articleCheck.setContent(article.getContent());
		articleCheck.setCheckType(ArticleCheck.CheckTypeEnum.CREATE.getCode());
		articleCheckService.addArticleCheck(articleCheck);
		//修改文章数
		//修改当前人的评论数
		String loingUserId = TokenUtils.getLoingUserId();
		UserExtension userExtensionInDb=new UserExtension();
		userExtensionInDb.setUserId(loingUserId);
		userExtensionInDb.setCommentCounts(1);
		userExtensionInDb.setScore(2);
		userExtensionService.updateById(userExtensionInDb);
	}

	@Override
	public List<Article> findListByUserId(String userId) {
		logger.info("用户id为:{}", userId);
		return articleMapper.findListByUserId(userId);
	}

	@Override
	public Map<String, Object> findByParams(QueryArticleVO queryArticleVO) {
		Map<String, Object> pageData = new HashMap<String, Object>(2);
		List<Article> list = new ArrayList<Article>();
		try {
			logger.info("分页查询文章参数为:{}", JsonUtils.toJson(queryArticleVO));
			int total = articleMapper.getCountByParams(queryArticleVO);
			// 当查询记录大于0时，查询数据库记录，否则直接返回空集合
			if (total > 0) {
				list = articleMapper.findByParams(queryArticleVO);
			}
			pageData.put("data", list);
			pageData.put("total", total);
			pageData.put("code", "200");
			pageData.put("msg", "");
		} catch (Exception e) {
			logger.error("分页查询文章异常", e);
		}
		return pageData;
	}

	@Override
	public Article getByUserIdAndArticleId(String userId, String articleId) {
		// TODO 修改判断
		logger.info("查询文章信息,用户id为:{},文章id为:{}", userId, articleId);
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(articleId)) {
			logger.info("查询文章详细信息参数有误,用户id为:{},文章id为:{}", userId, articleId);
		}
		return getArticleByUserIdAndArticleId(userId, articleId);
	}

	private Article getArticleByUserIdAndArticleId(String userId, String articleId) {
		Article article = new Article();
		article.setUserId(userId);
		article.setArticleId(articleId);
		List<Article> list = this.articleMapper.findByCondition(article);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 更新文章信息 要校验当前文章是否是当前登录用户下的文章
	 */
	@Override
	public void updateArticle(Article article) {
		logger.info("前台传来的文章信息,{}", article);
		String userId = TokenUtils.getLoingUserId();
		article.setUserId(userId);
		// 校验前台传来的数据
		checkArticle(article);
		String articleId = article.getArticleId();
		Article articleInDb = articleMapper.findByArticleId(articleId);
		if (articleInDb == null) {
			logger.error("根据文章id未查询到数据,articleId:{}", articleId);
			throw new BusinessException("修改文章信息有误，文章已不存在");
		}
//		if (Article.TYPE_SYSTEM_MESSAGE.equals(articleInDb.getType())) {
//			logger.error("当前文章处于发布状态,不可修改", articleInDb);
//			throw new BusinessException("当前文章处于发布状态,不可修改");
//		}
		if (Article.STATUS_IN_REVIEW.equals(articleInDb.getStatus())) {
			logger.error("系统消息不可修改", articleInDb);
			throw new BusinessException("系统消息不可修改");
		}
		String userIdInDb = articleInDb.getUserId();
		// 判断当前文章是当前登录用户下的文章
		if (!StringUtils.equals(article.getUserId(), userIdInDb)) {
			logger.error("文章用户id有误,不是当前用户的文章,登录用户id:{},数据库中用户id:{}", article.getUserId(), articleInDb.getUserId());
			throw new BusinessException("修改文章信息失败，文章已不存在，请返回文章列表页");
		}
		article.setUpdateTime(new Date());
		articleMapper.updateArticle(article);
		//新增审核记录
		ArticleCheck articleCheck = new ArticleCheck();
		articleCheck.setArticleId(article.getArticleId());
		articleCheck.setTitle(article.getTitle());
		articleCheck.setContent(article.getContent());
		articleCheck.setCheckType(ArticleCheck.CheckTypeEnum.UPDATE.getCode());
		articleCheckService.addArticleCheck(articleCheck);
	}

	@Override
	public Article getByArticleId(String articleId) {
		logger.info("查询文章信息,文章id为:{}", articleId);
		if (StringUtils.isBlank(articleId)) {
			throw new BusinessException("文章id为空");
		}
		Article article = new Article();
		article.setArticleId(articleId);
		List<Article> list = this.articleMapper.findByCondition(article);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void deleteArticle(String articleId, String userId) {
		if (StringUtils.isBlank(articleId)) {
			throw new BusinessException("文章id不能为空");
		}
		Article article = getArticleByUserIdAndArticleId(userId, articleId);
		if (article == null) {
			logger.info("根据文章id{}未查询到文章信息", articleId);
			throw new BusinessException("文章不存在");
		}
		if (Article.STATUS_IN_REVIEW.equals(article.getStatus())) {
			logger.info("审核中文章不能删除");
			throw new BusinessException("审核中文章不能删除");
		}
		if (Article.STATUS_PUBLISHED.equals(article.getStatus())) {
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
	public int getCount(QueryArticleVO queryArticleVO) {
		logger.info("查询文章条数条件：{}", JsonUtils.toJson(queryArticleVO));
		return articleMapper.getCountByParams(queryArticleVO);
	}

	@Override
	public Article findByArticleIdAndStatus(String articleId, String status) {
		Article article = new Article();
		article.setArticleId(articleId);
		article.setStatus(status);
		List<Article> list = articleMapper.findByCondition(article);
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
		article.setUpdateTime(new Date());
		return articleMapper.updateArticle(article);
	}

	@Override
	public int updateArticleViewCount(String articleId, Integer viewCount) {
		Article article = new Article();
		article.setArticleId(articleId);
		article.setViewCount(viewCount);
		article.setUpdateTime(new Date());
		return articleMapper.updateArticle(article);
	}
	
	/**
	 * 保存系统信息，同时向用户发送通知，除了自己
	 */
	@Override
	public void saveSystemMessage(Article article) {
		ValidationUtils.validateEntity(article);
		article.setStatus(Article.STATUS_PUBLISHED);
		article.setCreateTime(new Date());
		article.setPublishTime(new Date());
		article.setArticleId(IdUtils.generateArticleId());
		// 系统消息文章
		article.setType(Article.TYPE_SYSTEM_MESSAGE);
		Message message = new Message();
		message.setMessageType(MyConstant.MESSAGE_TYPE_NOTICE);
		message.setContentName(article.getTitle());
		message.setCommentContent(article.getContent());
		String loginUserId = TokenUtils.getLoingUserId();
		Set<String> set = new HashSet<String>();
		set.add(loginUserId);
		MessageUtils.sendMessageToAllExceptionUser(JsonUtils.toJson(message), set);
		articleMapper.saveArticle(article);
	}

	/**
	 * 根据条件查询es中的文章信息
	 */
	@Override
	public List<ArticleDTO> queryFromEsByCondition(QueryArticleVO queryArticleVO) {
		return esClientService.queryByParamsWithHightLight("article", "doc", queryArticleVO, true, ArticleDTO.class);
	}

	/**
	 * 通过文章标题查询文章信息
	 */
	@Override
	public List<ArticleDTO> searchArticleByTitle(String title) {
		QueryArticleVO queryArticleVO = new QueryArticleVO();
		queryArticleVO.setTitle(title);
		return queryFromEsByCondition(queryArticleVO);
	}

	@Override
	public List<Article> findByCondition(QueryArticleVO queryArticleVO) {
		return articleMapper.findByParams(queryArticleVO);
	}

	@Override
	public int getMaxStick() {
		return articleMapper.getMaxStick();
	}

	@Override
	public void setArticle(String articleId, String stick, String highQuality) {
		Article articleInDb = getByArticleId(articleId);
		if(articleInDb==null){
			throw new BusinessException("文章不存在");
		}
		boolean flag=false;
		Article article=new Article();
		article.setArticleId(articleInDb.getArticleId());
		//取消置顶
		if("0".equals(stick)){
			article.setStick(0);
			flag=true;
		}else if("1".equals(stick)){//置顶
			int maxStick = getMaxStick();
			maxStick++;
			article.setStick(maxStick);
			flag=true;
		}
		//加精或取消加精
		if("0".equals(highQuality)||"1".equals(highQuality)){
			flag=true;
			article.setHighQuality(highQuality);
		}
		if(!flag){
			throw new BusinessException("参数有误");
		}
		article.setUpdateTime(new Date());
		articleMapper.updateArticle(article);
	}
}
