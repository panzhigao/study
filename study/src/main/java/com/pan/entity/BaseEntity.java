package com.pan.entity;

import java.io.Serializable;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
