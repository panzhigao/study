package com.pan.query;



public class QueryPicture extends QueryBase{
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 图片id
	 */
	private String pictureId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	@Override
	public String toString() {
		return "QueryPicture [userId=" + userId + ", pictureId=" + pictureId
				+ ", pageSize=" + pageSize + ", pageNo=" + pageNo
				+ ", orderCondition=" + orderCondition + ", whereCondition="
				+ whereCondition + "]";
	}
}
