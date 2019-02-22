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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
