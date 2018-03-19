package com.pan.entity;

import org.hibernate.validator.constraints.NotEmpty;

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
	@NotEmpty(message="权限名不能为空")
	private String permissionName;
	/**
	 * 权限路径
	 */
	@NotEmpty(message="权限路径url不能为空")
	private String url;
	/**
	 * 父级pid
	 */
	private String pId="0";
	/**
	 * 标识，0-未选中
	 */
	private String marker;
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
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getMarker() {
		return marker;
	}
	public void setMarker(String marker) {
		this.marker = marker;
	}
	@Override
	public String toString() {
		return "Permission [permissionId=" + permissionId + ", permissionName="
				+ permissionName + ", url=" + url + ", pId=" + pId
				+ ", marker=" + marker + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()="
				+ getUpdateTime() + "]";
	}
}
