package com.pan.query;

import com.pan.common.constant.PageConstant;
import lombok.Data;

/**
 * @author 作者
 * @version 创建时间：2018年3月28日 下午6:28:30
 * 类说明
 */
@Data
public class QueryBase {
    /**
     * 分页
     */
    protected Integer pageSize;
    protected Integer pageNo;
    /**
     * 排序条件
     *
     * @return
     */
    protected String orderCondition;
    /**
     * 查询条件
     */
    protected String whereCondition;

    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 限制pageSize最多30
     *
     * @param pageSize
     */
    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize >= PageConstant.MAX_PAGE_SIZE) {
            pageSize = PageConstant.MAX_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        if (pageNo == null || pageSize == null) {
            return null;
        }
        return (this.pageNo - 1) * this.pageSize;
    }

    public Integer getRow() {
        return this.pageSize;
    }
}
