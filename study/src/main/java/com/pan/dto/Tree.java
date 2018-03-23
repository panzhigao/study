package com.pan.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Tree {
	private String title;
	private String value;
	private boolean checked;
	private List<Tree> data=new ArrayList<Tree>();
	private String id;
	private String pId;
	private String url;
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
	
	public List<Tree> getData() {
		return data;
	}
	public void setData(List<Tree> data) {
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
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Tree [title=" + title + ", value=" + value + ", checked="
				+ checked + ", data=" + data + ", id=" + id + ", pId=" + pId
				+ ", url=" + url + "]";
	}
	public static List<Tree> buildTree(List<Tree> nodes){
		List<Tree> list=new ArrayList<Tree>();
		for (Tree treeNode : nodes) {
			if("0".equals(treeNode.getpId())){
				list.add(treeNode);
			}else{
				for (Tree temp : nodes) {
					if(StringUtils.equals(temp.getId(), treeNode.getpId())){
						temp.data.add(treeNode);
					}
				}
			}
		}
		return list;
	}
}
