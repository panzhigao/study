package com.pan.entity;

/**
 * 
 * @author Administrator
 *
 */
public class Picture extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7161784937128201170L;
	/**
	 * 图片id
	 */
	private String pictureId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 图片路径
	 */
	private String picUrl;
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	@Override
	public String toString() {
		return "Picture [pictureId=" + pictureId + ", userId=" + userId
				+ ", picUrl=" + picUrl + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()="
				+ getUpdateTime() + "]";
	}
}
