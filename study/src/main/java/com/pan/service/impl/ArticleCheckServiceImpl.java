package com.pan.service.impl;

import java.util.*;
import com.pan.common.constant.MyConstant;
import com.pan.common.enums.ApproveFlagEnum;
import com.pan.common.enums.ArticleStatusEnum;
import com.pan.common.enums.RedisChannelEnum;
import com.pan.common.enums.CompleteFlagEnum;
import com.pan.common.enums.MessageTypeEnum;
import com.pan.common.enums.ScoreTypeEnum;
import com.pan.query.QueryArticleCheck;
import com.pan.service.*;
import com.pan.util.BeanUtils;
import com.pan.util.JedisUtils;
import com.pan.util.Publisher;
import com.pan.vo.ArticleCheckVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.exception.BusinessException;
import com.pan.entity.Article;
import com.pan.entity.ArticleCheck;
import com.pan.entity.Message;
import com.pan.entity.ScoreHistory;
import com.pan.entity.UserExtension;
import com.pan.entity.User;
import com.pan.mapper.ArticleCheckMapper;
import com.pan.mapper.ArticleMapper;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;

/**
 * @author panzhigao
 */
@Service
public class ArticleCheckServiceImpl  extends AbstractBaseService<ArticleCheck,ArticleCheckMapper> implements ArticleCheckService{
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
		
	@Autowired
	private ArticleService articleService;
	
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
	protected ArticleCheckMapper getBaseMapper() {
		return articleCheckMapper;
	}

	/**
	 * 新增文章审核记录
	 * @param articleCheck
	 */
	@Override
	public void addArticleCheck(ArticleCheck articleCheck) {
		ValidationUtils.validateEntity(articleCheck);
		articleCheck.setCreateTime(new Date());
		articleCheck.setCompleteFlag(CompleteFlagEnum.NOT_COMPLETE.getCode());
		insertSelective(articleCheck);
	}

	/**
	 * 根据id获取文章审核信息并校验
	 * @param id
	 * @return
	 */
	private ArticleCheck getAndCheck(Long id){
		if(id==null){
			throw new BusinessException("id为空");
		}
		ArticleCheck articleCheckInDb = articleCheckMapper.selectByPrimaryKey(id);
		if(articleCheckInDb==null){
			logger.error("根据id{}未查询到审核信息",id);
			throw new BusinessException("审核信息不存在");
		}
		if(CompleteFlagEnum.COMPLETE.getCode().equals(articleCheckInDb.getCompleteFlag())){
			throw new BusinessException("文章已审核完成，不能重复审核");
		}
		return articleCheckInDb;
	}
	
	/**
	 * 文章审核通过
	 * 1.将该审核记录置为已审核，审核通过
	 * 2.将文章状态修改为已发布，更新发布时间
	 * 3.增加一条积分历史
	 * 4.修改用户文章数和积分数
	 * 5.发送消息
	 * 6.发送消息，更新es
	 * @param id 文章审核记录id
	 */
	@Override
	public void passArticleCheck(Long id) {
		ArticleCheck articleCheckInDb = getAndCheck(id);
		User loginUser = TokenUtils.getLoginUser();
		//1.将该审核记录置为已审核，审核通过
		articleCheckInDb.setCheckTime(new Date());
		//审核完成
		articleCheckInDb.setCompleteFlag(CompleteFlagEnum.COMPLETE.getCode());
		articleCheckInDb.setCheckUserId(loginUser.getId());
		articleCheckInDb.setCheckUsername(loginUser.getUsername());
		articleCheckInDb.setApproveFlag(ApproveFlagEnum.APPROVED.getCode());
		articleCheckMapper.updateByPrimaryKeySelective(articleCheckInDb);
		
		//2.审核通过文章信息
		Article article = articleService.getAndCheckByArticleId(articleCheckInDb.getArticleId());
		if(article==null){
			throw new BusinessException("文章不存在");
		}
		if(!ArticleStatusEnum.IN_CHECK.getCode().equals(article.getStatus())){
			throw new BusinessException("文章状态不为审核中");
		}
		article.setStatus(ArticleStatusEnum.PUBLIC_SUCCESS.getCode());
		article.setPublishTime(new Date());
		article.setUpdateTime(new Date());
		articleMapper.updateByPrimaryKeySelective(article);
		
		//3.文章数加1，发表文章加5分
		ScoreHistory addScoreHistory = scoreHistoryService.addScoreHistory(article.getUserId(), ScoreTypeEnum.PUBLISH_ARTICLE);
		
		//4.用户拓展表增加积分和文章数
		UserExtension userExtension=new UserExtension();
		userExtension.setId(addScoreHistory.getUserId());
		userExtension.setUpdateTime(new Date());
		userExtension.setScore(addScoreHistory.getScore());
		userExtension.setArticleCounts(1);
		userExtensionService.increaseCounts(userExtension);
		//5.发送消息
		Message message=new Message();
		message.setArticleId(article.getId());
		message.setContentName(article.getTitle());
		message.setReceiverUserId(article.getUserId());
		messageService.sendMessageToUser(message,MessageTypeEnum.ARTICLE_CHECK_PASS);
		//将文章id写入redis队列
		JedisUtils.rpush(MyConstant.ARTICLE_ES_REDIS_LIST, article.getId().toString());
		//通知redis消费
		Publisher.sendMessage(RedisChannelEnum.ARTICLE_ES_CREATE_OR_UPDATE.getName(),"");
	}

	/**
	 * 审核文章拒绝
	 * @param id
	 * @param reason
	 */
	@Override
	public void notPassArticle(Long id,String reason) {
		if(StringUtils.isBlank(reason)){
			throw new BusinessException("原因不能为空");
		}
		ArticleCheck articleCheckInDb = getAndCheck(id);
		//修改审核信息
		User loginUser = TokenUtils.getLoginUser();
		ArticleCheck updateArticleCheck=new ArticleCheck();
		updateArticleCheck.setId(id);
		updateArticleCheck.setCheckTime(new Date());
		updateArticleCheck.setCompleteFlag(CompleteFlagEnum.COMPLETE.getCode());
		updateArticleCheck.setCheckUserId(loginUser.getId());
		updateArticleCheck.setCheckUsername(loginUser.getUsername());
		updateArticleCheck.setApproveFlag(ApproveFlagEnum.NOT_APPROVED.getCode());
		updateArticleCheck.setRemark(reason);
		articleCheckMapper.updateByPrimaryKeySelective(updateArticleCheck);
		//获取文章信息
		Article article = articleService.getAndCheckByArticleId(articleCheckInDb.getArticleId());
		if(!ArticleStatusEnum.IN_CHECK.getCode().equals(article.getStatus())){
			throw new BusinessException("文章状态不为审核中");
		}
		Article updateArticle=new Article();
		updateArticle.setId(articleCheckInDb.getArticleId());
		//审核未通过
		updateArticle.setStatus(ArticleStatusEnum.FAIL_CHECKED.getCode());
		articleMapper.updateByPrimaryKeySelective(updateArticle);
		//发送消息
		Message message=new Message();
		message.setArticleId(article.getId());
		message.setContentName(article.getTitle());
		message.setReceiverUserId(article.getUserId());
		message.setCommentContent(reason);
		messageService.sendMessageToUser(message,MessageTypeEnum.ARTICLE_CHECK_NOT_PASS);
	}

	@Override
	public Map<String, Object> findVOPageableMap(QueryArticleCheck queryArticleCheck) {
		Map<String, Object> pageData = new HashMap<>(4);
		List<ArticleCheckVO> list = new ArrayList<>();
		int total=countByParams(queryArticleCheck);
		if(total>0){
			List<ArticleCheck> pageable = findPageable(queryArticleCheck);
			for(ArticleCheck articleCheck:pageable){
				ArticleCheckVO articleCheckVO=new ArticleCheckVO();
				BeanUtils.copyProperties(articleCheck,articleCheckVO);
				articleCheckVO.setCategoryName(ArticleCategoryServiceImpl.getCategoryNameByIdThroughCache(articleCheck.getCategoryId()));
				list.add(articleCheckVO);
			}
		}
		pageData.put("data", list);
		pageData.put("total", total);
		pageData.put("code", "200");
		pageData.put("msg", "");
		return pageData;
	}

}
