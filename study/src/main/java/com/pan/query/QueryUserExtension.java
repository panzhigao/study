package com.pan.query;

/**
 * 查询用户拓展表VO
 */
public class QueryUserExtension extends QueryBase{
	/**
	 * 用户id
	 */
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "QueryUserExtensionVO [userId=" + userId + ", pageSize="
				+ pageSize + ", pageNo=" + pageNo + "]";
	}
}
