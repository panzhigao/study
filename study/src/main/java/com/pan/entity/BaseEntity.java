package com.pan.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类基类
 * @author Administrator
 *
 */
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7856823675357570385L;
	
	/**
	 * 自增主键
	 */
	private Integer id;
	/**
	 * 创建时间
	 */
	private Date createTime;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "BaseEntity [id=" + id + ", createTime=" + createTime + "]";
	}
	
}
