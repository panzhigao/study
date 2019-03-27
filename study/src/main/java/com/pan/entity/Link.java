package com.pan.entity;

import org.hibernate.validator.constraints.NotEmpty;

import com.pan.common.annotation.LogMeta;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Link extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1120582377127420034L;
	
	@NotEmpty(message="链接名不能为空")
	@LogMeta(fieldDesc="链接名")
	private String linkName;

	@NotEmpty(message="链接地址不能为空")
	@LogMeta(fieldDesc="链接地址")
	private String linkUrl;

	private Integer sort;

	private Integer status;

}