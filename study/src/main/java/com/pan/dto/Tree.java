package com.pan.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
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

    private boolean isStu;

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
}
