package com.pan.vo;
/**
 * @author 作者
 * @version 创建时间：2018年3月28日 下午6:28:30
 * 类说明
 */
public class QueryVO {
	/**
	 * 分页
	 */
	protected Integer pageSize;
	protected Integer pageNo;
	/**
	 * 排序条件
	 * @return
	 */
	protected String orderByCondition;
	/**
	 * 查询条件
	 */
	protected String whereCondition;
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		if(pageSize==null||pageSize>=30){
			pageSize=30;
		}
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getOffset(){
		if(pageNo==null||pageSize==null){
			return null;
		}
		return (this.pageNo-1)*this.pageSize;
	}
	public Integer getRow(){
		return this.pageSize;
	}
	public String getOrderByCondition() {
		return orderByCondition;
	}
	public void setOrderByCondition(String orderByCondition) {
		this.orderByCondition = orderByCondition;
	}
	public String getWhereCondition() {
		return whereCondition;
	}
	public void setWhereCondition(String whereCondition) {
		this.whereCondition = whereCondition;
	}
	
}
