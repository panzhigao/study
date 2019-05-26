package com.pan.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 层级树
 * @author panzhigao-wb
 */
@Data
public class Tree implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 744786613483339466L;
    private String title;
    private String value;
    /**
     * 节点是否选中
     */
    private boolean checked;
    private List<Tree> data = new ArrayList<Tree>();
    private String id;
    /**
     * 父节点
     */
    private String pId;
    private String url;
    /**
     * icon图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;

    public static List<Tree> buildTree(List<Tree> nodes, boolean sortFlag) {
        List<Tree> list = new ArrayList<Tree>();
        if (sortFlag) {
            Collections.sort(nodes, new TreeComparator());
        }
        for (Tree treeNode : nodes) {
            if (treeNode.getPId() == null || "0".equals(treeNode.getPId())) {
                list.add(treeNode);
            } else {
                for (Tree temp : nodes) {
                    if (StringUtils.equals(temp.getId(), treeNode.getPId())) {
                        temp.data.add(treeNode);
                        break;
                    }
                }
            }
        }
        return list;
    }

    static class TreeComparator implements Comparator<Tree> {
        @Override
        public int compare(Tree o1, Tree o2) {
            return o1.getSort() - o2.getSort();
        }

    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
            return true;
        }
		if (obj == null){
            return false;
        }
		if (getClass() != obj.getClass()){
            return false;
        }
		Tree other = (Tree) obj;
		if (id == null) {
			if (other.id != null){
                return false;
            }
		} else if (!id.equals(other.id)){
            return false;
        }
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (checked ? 1231 : 1237);
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pId == null) ? 0 : pId.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
    
}
