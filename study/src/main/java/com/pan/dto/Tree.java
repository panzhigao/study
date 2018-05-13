package com.pan.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author panzhigao-wb
 *
 */
public class Tree implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 744786613483339466L;
	private String title;
	private String value;
	private boolean checked;
	private List<Tree> data=new ArrayList<Tree>();
	private String id;
	private String pId;
	private String url;
	private String icon;
	private Integer sort;
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
		return "Tree [title=" + title + ", value=" + value + ", checked="
				+ checked + ", data=" + data + ", id=" + id + ", pId=" + pId
				+ ", url=" + url + ", icon=" + icon + ", sort=" + sort + "]";
	}
	public static List<Tree> buildTree(List<Tree> nodes,boolean sortFlag){
		List<Tree> list=new ArrayList<Tree>();
		if(sortFlag){			
			Collections.sort(nodes,new TreeComparator());
		}
		for (Tree treeNode : nodes) {
			if(treeNode.getpId()==null||"0".equals(treeNode.getpId())){
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
	
	static class TreeComparator implements Comparator<Tree>{
		@Override
		public int compare(Tree o1, Tree o2) {
			return o1.getSort()-o2.getSort();
		}
		
	}
}
