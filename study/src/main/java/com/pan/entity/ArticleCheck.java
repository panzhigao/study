package com.pan.entity;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.pan.common.annotation.UnescapeHtml;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
	private String articleId;
	/**
	 * 文章标题
	 */
	@NotNull(message="文章标题不能为空")
	private String title;
	/**
	 * 是否审核完成，0-否，1-是
	 */
	private String completeFlag;
	/**
	 * 审核类型，0-创建，1-修改
	 */
	private String checkType;
	/**
	 * 审核人id
	 */
	private String checkUserId;
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
	private String approveFlag;
	/**
	 * 文章内容
	 */
	@UnescapeHtml
	private String content;
	/**
	 * 审核备注
	 */
	private String remark;
	
	public enum CompleteFlagEnum{
		COMPLETE("已完成","1"),
		NOT_COMPLETE("未完成","0");
		private String name;
		private String code;
		
		CompleteFlagEnum(String name,String code){
			this.name=name;
			this.code=code;
		}
		public String getName() {
			return name;
		}
		public String getCode() {
			return code;
		}	
	}
	
	public enum CheckTypeEnum{
		CREATE("创建","0"),
		UPDATE("修改","1");
		private String name;
		private String code;
		
		CheckTypeEnum(String name,String code){
			this.name=name;
			this.code=code;
		}
		public String getName() {
			return name;
		}
		public String getCode() {
			return code;
		}	
	}
	
	public enum ApproveFlagEnum{
		APPROVED("审核通过","1"),
		NOT_APPROVED("审核未通过","0");
		private String name;
		private String code;
		
		ApproveFlagEnum(String name,String code){
			this.name=name;
			this.code=code;
		}
		public String getName() {
			return name;
		}
		public String getCode() {
			return code;
		}	
	}
}
