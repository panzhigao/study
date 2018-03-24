package com.pan.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TreeNode {
	private String id;
	private String pId;
	private String name;
	private Object data;
	private String url;
	private String icon;
	private Integer sort;
	private List<TreeNode> children=new ArrayList<TreeNode>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", pId=" + pId + ", name=" + name
				+ ", data=" + data + ", url=" + url + ", icon=" + icon
				+ ", sort=" + sort + ", children=" + children + "]";
	}
	public static List<TreeNode> buildTree(List<TreeNode> nodes){
		List<TreeNode> list=new ArrayList<TreeNode>();
		for (TreeNode treeNode : nodes) {
			if(treeNode.getpId()==null){
				list.add(treeNode);
			}else{
				for (TreeNode temp : nodes) {
					if(StringUtils.equals(temp.getId(), treeNode.getpId())){
						temp.children.add(treeNode);
					}
				}
			}
		}
		return list;
	}
}
