package com.pan.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TreeNode {
	private String id;
	private String pid;
	private String name;
	private Object data;
	private List<TreeNode> children=new ArrayList<TreeNode>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
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
	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", pid=" + pid + ", name=" + name
				+ ", data=" + data + ", children=" + children + "]";
	}
	
	public static List<TreeNode> buildTree(List<TreeNode> nodes){
		List<TreeNode> list=new ArrayList<TreeNode>();
		for (TreeNode treeNode : nodes) {
			if(treeNode.getPid()==null){
				list.add(treeNode);
			}else{
				for (TreeNode temp : nodes) {
					if(StringUtils.equals(temp.getId(), treeNode.getPid())){
						temp.children.add(treeNode);
					}
				}
			}
		}
		return list;
	}
}
