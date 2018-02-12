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
import com.pan.entity.Article;
import com.pan.entity.Message;
import com.pan.mapper.ArticleMapper;
import com.pan.service.ArticleService;
import com.pan.service.CommentService;
import com.pan.service.MessageService;
import com.pan.util.CookieUtils;
import com.pan.util.IdUtils;
import com.pan.util.JsonUtils;
import com.pan.util.MessageUtils;
import com.pan.util.ValidationUtils;

/**
 * 
 * @author Administrator
 * 
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Autowired
	private ArticleMapper articleMapper;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * 校验当前操作码状态是否正常
	 * 1-草稿，2-审核中
	 * @param status
	 * @return
	 */
	private boolean checkOpearteStatus(String status){
		if(Article.STATUS_SKETCH.equals(status)||Article.STATUS_IN_REVIEW.equals(status)){
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
		//TODO 待清理
		ValidationUtils.validateEntity(article);
//		if (StringUtils.isBlank(article.getUserId())) {
//			logger.error("用户信息有误");
//			throw new BusinessException("用户信息有误,请重新登陆");
//		}
//		if (StringUtils.isBlank(article.getTitle())) {
//			logger.error("文章标题不能为空");
//			throw new BusinessException("文章标题不能为空");
//		}
//		if (StringUtils.isBlank(article.getContent())) {
//			logger.error("文章内容不能为空");
//			throw new BusinessException("文章内容不能为空");
//		}
//		if (StringUtils.isBlank(article.getOutline())) {
//			logger.error("文章概要不能为空");
//			throw new BusinessException("文章内容不能为空");
//		}
		if(!checkOpearteStatus(article.getStatus())){
			logger.error("html页面被修改,方法参数错误");
			throw new BusinessException("文章状态有误,请刷新页面");
		}
	}

	/**
	 * 新增文章
	 */
	public void saveArticle(Article article) {
		checkArticle(article);
		// 默认草稿状态
		if (StringUtils.isBlank(article.getStatus())) {
			logger.info("文章无状态，默认设置为草稿状态");
			article.setStatus(Article.STATUS_SKETCH);
		}
		article.setCreateTime(new Date());
		article.setArticleId(IdUtils.generateArticleId());
		article.setType("1");
		articleMapper.saveArticle(article);
	}

	public List<Article> findListByUserId(String userId) {
		logger.info("用户id为:{}", userId);
		return articleMapper.findListByUserId(userId);
	}

	public Map<String,Object> findByParams(Map<String, Object> params) {
		Map<String,Object> pageData=new HashMap<String, Object>(2);
		List<Article> list = new ArrayList<Article>();
		try {
			logger.info("分页查询文章参数为:{}", JsonUtils.toJson(params));
			int total=articleMapper.getCountByParams(params);
			//当查询记录大于0时，查询数据库记录，否则直接返回空集合
			if(total>0){				
				list = articleMapper.findByParams(params);
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

	public Article getByUserIdAndArticleId(String userId, String articleId) {
		//TODO 修改判断
		logger.info("查询文章信息,用户id为:{},文章id为:{}", userId, articleId);
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(articleId)){
			logger.info("查询文章详细信息参数有误,用户id为:{},文章id为:{}", userId, articleId);
		}
		return getUserArticle(userId, articleId);
	}
	
	private Article getUserArticle(String userId,String articleId){
		Article article=new Article();
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
	public void updateArticle(Article article) {
		logger.info("前台传来的文章信息,{}",article);
		//校验前台传来的数据
		checkArticle(article);
		String articleId = article.getArticleId();
		Article articleInDb = articleMapper.findByArticleId(articleId);
		if (articleInDb == null) {
			logger.error("根据文章id未查询到数据,articleId:{}", articleId);
			throw new BusinessException("修改文章信息有误，文章已不存在");
		}
		if(Article.TYPE_SYSTEM_MESSAGE.equals(articleInDb.getType())){
			logger.error("当前文章处于发布状态,不可修改", articleInDb);
			throw new BusinessException("当前文章处于发布状态,不可修改");
		}
		if(Article.STATUS_IN_REVIEW.equals(articleInDb.getStatus())){
			logger.error("系统消息不可修改", articleInDb);
			throw new BusinessException("系统消息不可修改");
		}
		String userIdInDb = articleInDb.getUserId();
		// 判断当前文章是当前登录用户下的文章
		if (!StringUtils.equals(article.getUserId(), userIdInDb)) {
			logger.error("文章用户id有误,不是当前用户的文章,登录用户id:{},数据库中用户id:{}",article.getUserId(), articleInDb.getUserId());
			throw new BusinessException("修改文章信息失败，文章已不存在，请返回文章列表页");
		}
		try {
			article.setUpdateTime(new Date());
			articleMapper.updateArticle(article);
		} catch (Exception e) {
			logger.error("修改文章失败", e);
			throw new BusinessException("修改文章信息失败,请稍后");
		}
	}

	public Article getByArticleId(String articleId) {
		logger.info("查询文章信息,文章id为:{}",articleId);
		if(StringUtils.isBlank(articleId)){
			throw new BusinessException("文章id为空");
		}
		Article article=new Article();
		article.setArticleId(articleId);
		List<Article> list = this.articleMapper.findByCondition(article);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	public void deleteArticle(String articleId, String userId) {
		if(StringUtils.isBlank(articleId)){
			throw new BusinessException("文章id不能为空");
		}
		Article article = getUserArticle(userId, articleId);
		if(article==null){
			logger.info("根据文章id{}未查询到文章信息",articleId);
			throw new BusinessException("文章不存在");
		}
		if(Article.STATUS_IN_REVIEW.equals(article.getStatus())){
			logger.info("审核中文章不能删除");
			throw new BusinessException("审核中文章不能删除");
		}
		int num=this.articleMapper.deleteByUserIdAndArticleId(userId, articleId);
		if(num!=1){
			logger.info("删除文章失败");
			throw new BusinessException("删除文章失败");
		}
	}

	public int getCount(Map<String, Object> params) {
		logger.info("查询文章条数条件：{}",JsonUtils.toJson(params));
		return articleMapper.getCountByParams(params);
	}

	@Override
	public Article findByArticleIdAndStatus(String articleId, String status) {
	    Article article=new Article();
	    article.setArticleId(articleId);
	    article.setStatus(status);
	    List<Article> list = articleMapper.findByCondition(article);
	    if(list.size()==1){
	    	return list.get(0);
	    }
		return null;
	}

	@Override
	public int updateArticleCommentCount(String articleId,
			Integer commentCount) {
		Article article=new Article();
		article.setArticleId(articleId);
		article.setCommentCount(commentCount);
		return articleMapper.updateArticle(article);
	}

	@Override
	public int updateArticleViewCount(String articleId, Integer viewCount) {
		Article article=new Article();
		article.setArticleId(articleId);
		article.setViewCount(viewCount);
		return articleMapper.updateArticle(article);
	}

	@Override
	public void saveSystemMessage(Article article) {
		ValidationUtils.validateEntity(article);
		article.setStatus(Article.STATUS_PUBLISHED);
		article.setCreateTime(new Date());
		article.setArticleId(IdUtils.generateArticleId());
		article.setType("2");//系统消息文章
		Message message=new Message();
		message.setMessageType(MyConstant.MESSAGE_TYPE_NOTICE);
		message.setContentName(article.getTitle());
		message.setCommentContent(article.getContent());
		String loginUserId = CookieUtils.getLoginUserId();
		Set<String> set=new HashSet<String>();
		set.add(loginUserId);
		MessageUtils.sendMessageToAllUsersWithException(JsonUtils.toJson(message), set);
		articleMapper.saveArticle(article);
	}

	/**
	 * 审核通过文章
	 */
	@Override
	public void passArticle(String articleId) {
		Article article = articleMapper.findByArticleId(articleId);
		if(article==null){
			throw new BusinessException("文章不存在");
		}
		if(!Article.STATUS_IN_REVIEW.equals(article.getStatus())){
			throw new BusinessException("文章状态不为审核中");
		}
		article.setStatus(Article.STATUS_PUBLISHED);
		article.setPublishTime(new Date());
		articleMapper.updateArticle(article);
	}
	
	/**
	 * 文章未通过审核
	 */
	@Override
	public Message notPassArticle(String articleId,String reason) {
		Article article = articleMapper.findByArticleId(articleId);
		if(article==null){
			throw new BusinessException("文章不存在");
		}
		if(!Article.STATUS_IN_REVIEW.equals(article.getStatus())){
			throw new BusinessException("文章状态不为审核中");
		}
		article.setStatus(Article.STATUS_NOT_PASS);
		articleMapper.updateArticle(article);
		// TODO 审核未通过发送消息 记录原因  是否新建记录表未定
		//发送消息
		Message message=new Message();
		message.setMessageId(IdUtils.generateMessageId());
		message.setMessageType(MyConstant.MESSAGE_TYPE_SYSTEM);
		message.setStatus(MyConstant.MESSAGE_NOT_READED);
		message.setContentId(article.getArticleId());
		message.setContentName(article.getTitle());
		message.setReceiverUserId(article.getUserId());
		message.setCreateTime(new Date());
		message.setCommentContent(reason);
		messageService.addMessage(message);
		MessageUtils.sendToUser(article.getUserId(), JsonUtils.toJson(message));
		return message;
	}
}
