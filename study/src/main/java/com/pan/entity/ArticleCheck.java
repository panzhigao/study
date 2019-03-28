package com.pan.entity;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.pan.common.annotation.UnescapeHtml;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 文章审核
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ArticleCheck extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4213601853452299514L;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 文章id
	 */
	@NotNull(message="文章id不能为空")
	private Long articleId;
	/**
	 * 文章标题
	 */
	@NotEmpty(message="文章标题不能为空")
	private String title;
	/**
	 * 是否审核完成，0-否，1-是
	 */
	private Integer completeFlag;
	/**
	 * 审核类型，0-创建，1-修改
	 */
	private Integer checkType;
	/**
	 * 审核人id
	 */
	private Long checkUserId;
	/**
	 * 审核人姓名
	 */
	private String checkUsername;
	/**
	 * 审核时间
	 */
	private Date checkTime;
	/**
	 * 通过标志，是否通过，0-否，1-是
	 */
	private Integer approveFlag;
	/**
	 * 文章内容
	 */
	@UnescapeHtml
	private String content;
	/**
	 * 审核备注
	 */
	private String remark;

}
