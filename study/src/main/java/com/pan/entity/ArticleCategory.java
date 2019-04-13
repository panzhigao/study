package com.pan.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.pan.common.annotation.LogMeta;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章分类实体
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ArticleCategory extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1342085367851236822L;
	/**
	 * 分类id
	 */
	private Long id;
	/**
	 * 分类名称
	 */
	@NotEmpty(message="分类名称不能为空")
	@Size(max=10,message="分类名称不能超过10个字")
	@LogMeta(fieldDesc = "分类名称")
    private String categoryName;
    /**
     * 排序
     */
	@LogMeta(fieldDesc = "排序")
    private Integer sort;
    /**
     * 状态，0-未启用，1-启用
     */
    private Integer status;
}