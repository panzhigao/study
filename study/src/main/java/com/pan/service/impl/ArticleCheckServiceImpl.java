package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.pan.common.enums.ScoreTypeEnum;
import com.pan.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.entity.Article;
import com.pan.entity.ArticleCheck;
import com.pan.entity.Message;
import com.pan.entity.ScoreHistory;
import com.pan.entity.UserExtension;
import com.pan.entity.User;
import com.pan.mapper.ArticleCheckMapper;
import com.pan.mapper.ArticleMapper;
import com.pan.query.QueryArticleCheck;
import com.pan.util.IdUtils;
import com.pan.util.JsonUtils;
import com.pan.util.MessageUtils;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;
import com.pan.vo.ArticleCheckVO;

/**
 * @author panzhigao
 */
@Service
public class ArticleCheckServiceImpl  extends AbstractBaseService<ArticleCheck,ArticleCheckMapper> implements ArticleCheckService{
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	private static final String COMPLETE_CHECK="1";
	
	@Autowired
	private ArticleCheckMapper articleCheckMapper;
	
	@Autowired
	private ArticleMapper articleMapper;
	
	@Autowired
	private MessageService messageService;
		
	@Autowired
	private ScoreHistoryService scoreHistoryService;
	
	@Autowired
	private UserExtensionService userExtensionService;



	@Override
	public Map<String,Object> findByParams(QueryArticleCheck queryArticleCheck) {
		Map<String,Object> pageData=new HashMap<String, Object>(2);
		List<ArticleCheckVO> list = new ArrayList<ArticleCheckVO>();
		try {
			logger.info("分页查询文章审核信息，参数为:{}", JsonUtils.toJson(queryArticleCheck));
			//审核完成
			if(COMPLETE_CHECK.equals(queryArticleCheck.getCompleteFlag())){
				queryArticleCheck.setOrderCondition("check_time desc");
			}else{
				queryArticleCheck.setOrderCondition("create_time desc");
			}
			int total=articleCheckMapper.getCountByParams(queryArticleCheck);
			//当查询记录大于0时，查询数据库记录，否则直接返回空集合
			if(total>0){				
				list = articleCheckMapper.findVOByParams(queryArticleCheck);
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
	public void addArticleCheck(ArticleCheck articleCheck) {
		ValidationUtils.validateEntity(articleCheck);
		articleCheck.setCreateTime(new Date());
		articleCheck.setCompleteFlag(ArticleCheck.CompleteFlagEnum.NOT_COMPLETE.getCode());
		insertSelective(articleCheck);
	}

	/**
	 * 根据id获取文章并校验
	 * @param id
	 * @return
	 */
	private ArticleCheck getAndCheck(Long id){
		if(id==null){
			throw new BusinessException("id为空");
		}
		ArticleCheck articleCheckInDb = articleCheckMapper.findById(id);
		if(articleCheckInDb==null){
			logger.error("根据id{}未查询到审核信息",id);
			throw new BusinessException("审核信息不存在");
		}
		if(StringUtils.equals(articleCheckInDb.getCompleteFlag(),ArticleCheck.CompleteFlagEnum.COMPLETE.getCode())){
			throw new BusinessException("文章已审核通过，不能重复通过");
		}
		return articleCheckInDb;
	}
	
	
	@Override
	public void passArticleCheck(Long id) {
		ArticleCheck articleCheckInDb = getAndCheck(id);
		User loginUser = TokenUtils.getLoginUser();
		articleCheckInDb.setCheckTime(new Date());
		articleCheckInDb.setCompleteFlag(ArticleCheck.CompleteFlagEnum.COMPLETE.getCode());
		articleCheckInDb.setCheckUserId(loginUser.getUserId());
		articleCheckInDb.setCheckUsername(loginUser.getUsername());
		articleCheckInDb.setApproveFlag(ArticleCheck.ApproveFlagEnum.APPROVED.getCode());
		articleCheckMapper.updateArticleCheck(articleCheckInDb);
		//审核通过文章信息
		
		Article article = articleMapper.findByArticleId(articleCheckInDb.getArticleId());
		if(article==null){
			throw new BusinessException("文章不存在");
		}
		if(!Article.STATUS_IN_REVIEW.equals(article.getStatus())){
			throw new BusinessException("文章状态不为审核中");
		}
		//如果文章为修改，则将内容复制过去
		if(ArticleCheck.CheckTypeEnum.UPDATE.getCode().equals(articleCheckInDb.getCheckType())){
			article.setStatus(Article.STATUS_PUBLISHED);
			article.setTitle(articleCheckInDb.getTitle());
			article.setContent(articleCheckInDb.getContent());
		}
		article.setStatus(Article.STATUS_PUBLISHED);
		article.setPublishTime(new Date());
		article.setUpdateTime(new Date());
		articleMapper.updateArticle(article);
		//文章数加1，发表文章加5分
		ScoreHistory addScoreHistory = scoreHistoryService.addScoreHistory(article.getUserId(), ScoreTypeEnum.PUBLISH_ARTICLE);
		//用户拓展表增加积分和文章数
		UserExtension userExtension=new UserExtension();
		userExtension.setUserId(addScoreHistory.getUserId());
		userExtension.setUpdateTime(new Date());
		userExtension.setScore(addScoreHistory.getScore());
		userExtension.setArticleCounts(1);
		userExtensionService.increaseCounts(userExtension);
	}

	/**
	 * 审核文章拒绝
	 * @param id
	 * @param reason
	 */
	@Override
	public void notPassArticle(Long id,String reason) {
		ArticleCheck articleCheckInDb = getAndCheck(id);
		//修改审核信息
		User loginUser = TokenUtils.getLoginUser();
		articleCheckInDb.setCheckTime(new Date());
		articleCheckInDb.setCompleteFlag(ArticleCheck.CompleteFlagEnum.COMPLETE.getCode());
		articleCheckInDb.setCheckUserId(loginUser.getUserId());
		articleCheckInDb.setCheckUsername(loginUser.getUsername());
		articleCheckInDb.setApproveFlag(ArticleCheck.ApproveFlagEnum.NOT_APPROVED.getCode());
		articleCheckMapper.updateArticleCheck(articleCheckInDb);
		
		if(StringUtils.isBlank(reason)){
			throw new BusinessException("原因不能为空");
		}
		Article article = articleMapper.findByArticleId(articleCheckInDb.getArticleId());
		if(article==null){
			throw new BusinessException("文章不存在");
		}
		if(!Article.STATUS_IN_REVIEW.equals(article.getStatus())){
			throw new BusinessException("文章状态不为审核中");
		}
		//如果审核类型是创建，状态修改为审核未通过，如果是修改，状态修改为发布成功
		if(ArticleCheck.CheckTypeEnum.CREATE.getCode().equals(articleCheckInDb.getCheckType())){
			article.setStatus(Article.STATUS_NOT_PASS);
		}else if(ArticleCheck.CheckTypeEnum.UPDATE.getCode().equals(articleCheckInDb.getCheckType())){
			article.setStatus(Article.STATUS_PUBLISHED);
		}
		articleMapper.updateArticle(article);
		// TODO 审核未通过发送消息 记录原因  是否新建记录表未定
		//发送消息
		User user=TokenUtils.getLoginUser();
		Message message=new Message();
		message.setMessageId(IdUtils.generateMessageId());
		message.setMessageType(MyConstant.MESSAGE_TYPE_SYSTEM);
		message.setStatus(MyConstant.MESSAGE_NOT_READED);
		message.setContentId(article.getArticleId());
		message.setContentName(article.getTitle());
		message.setReceiverUserId(article.getUserId());
		message.setCreateTime(new Date());
		message.setCommentContent(reason);
		message.setSenderName(user.getUsername());
		message.setSenderUserId(user.getUserId());
		messageService.addMessage(message);
		MessageUtils.sendToUser(article.getUserId(), JsonUtils.toJson(message));
	}

	@Override
	protected ArticleCheckMapper getBaseMapper() {
		return articleCheckMapper;
	}
}
