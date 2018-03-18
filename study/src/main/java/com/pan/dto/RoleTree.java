package com.pan.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class RoleTree {
	private String title;
	private String value;
	private boolean checked;
	private List<RoleTree> data=new ArrayList<RoleTree>();
	private String id;
	private String pId;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public List<RoleTree> getData() {
		return data;
	}
	public void setData(List<RoleTree> data) {
		this.data = data;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "RoleTree [title=" + title + ", value=" + value + ", checked="
				+ checked + ", data=" + data + ", id=" + id + ", pId=" + pId
				+ "]";
	}
	public static List<RoleTree> buildTree(List<RoleTree> nodes){
		List<RoleTree> list=new ArrayList<RoleTree>();
		for (RoleTree treeNode : nodes) {
			if("0".equals(treeNode.getpId())){
				list.add(treeNode);
			}else{
				for (RoleTree temp : nodes) {
					if(StringUtils.equals(temp.getId(), treeNode.getpId())){
						temp.data.add(treeNode);
					}
				}
			}
		}
		return list;
	}
}
