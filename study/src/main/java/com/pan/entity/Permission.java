package com.pan.entity;

/**
 * 权限实体类
 * @author Administrator
 *
 */
public class Permission extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -338202844637487403L;
	
	/**
	 * 权限id
	 */
	private String permissionId;
	/**
	 * 权限名
	 */
	private String permissionName;
	/**
	 * 权限路径
	 */
	private String url;
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Permission [permissionId=" + permissionId + ", permissionName="
				+ permissionName + ", url=" + url + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()="
				+ getUpdateTime() + "]";
	}
}
