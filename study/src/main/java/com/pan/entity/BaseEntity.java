package com.pan.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类基类
 * @author Administrator
 *
 */
@Data
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7856823675357570385L;
	
	/**
	 * 自增主键
	 */
	protected Long id;
	/**
	 * 创建时间
	 */
	protected Date createTime;
	/**
	 * 创建人
	 */
	protected String createUser;
	/**
	 * 更新时间
	 */
	protected Date updateTime;
	/**
	 * 修改人
	 */
	protected String updateUser;
}
