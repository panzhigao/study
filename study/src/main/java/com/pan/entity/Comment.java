package com.pan.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pan.common.annotation.LoginGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;
import com.pan.common.annotation.UnescapeHtml;

/**
 * 评论
 * @author panzhigao-wb
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Comment extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 854160848698390694L;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 文章id
	 */
	@NotNull(message="文章id不能为空",groups = {LoginGroup.class})
	private Long articleId;
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
