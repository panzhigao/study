package com.pan.entity;

import javax.validation.constraints.Size;

import com.pan.common.annotation.LoginGroup;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import com.pan.common.annotation.UnescapeHtml;
import com.pan.util.JsonUtils;

/**
 * 评论
 * @author panzhigao-wb
 *
 */
@Data
public class Comment extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 854160848698390694L;
	/**
	 * 评论id
	 */
	private String commentId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 文章id
	 */
	@NotEmpty(message="文章id不能为空",groups = {LoginGroup.class})
	private String articleId;
	/**
	 * 评论内容
	 */
	@NotEmpty(message="评论内容不能为空")
	@Size(max=300,message="评论内容不能超过300个字")
	@UnescapeHtml
	private String commentContent;
	/**
	 * 接收回复的评论用户
	 */
	private String replyToUserId;
	/**
	 * 赞的数目,默认0
	 */
	private Integer praiseCounts=0;
}
