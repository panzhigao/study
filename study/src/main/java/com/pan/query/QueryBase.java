package com.pan.query;

import com.pan.common.constant.PageConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 作者
 * @version 创建时间：2018年3月28日 下午6:28:30
 * 类说明
 */
public class QueryBase {
    /**
     * 分页
     */
    protected Integer pageSize;
    protected Integer pageNo;
    /**
     * 是否限制查询条数
     */
    private boolean limitPage=true;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date beginDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date endDateTime;

    protected java.sql.Date beginDate;

    protected java.sql.Date endDate;

    public Date getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(Date beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public java.sql.Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(java.sql.Date beginDate) {
        this.beginDate = beginDate;
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
    }

    public void toggleLimit(boolean limitPage){
        this.limitPage=limitPage;
    }

    public void setOrderCondition(String orderCondition) {
        this.orderCondition = orderCondition;
        if(StringUtils.isNotBlank(orderCondition)){
            this.orderCondition= com.pan.util.StringUtils.camelToUnderline(orderCondition);
        }
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getOrderCondition() {
        return orderCondition;
    }

    public String getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(String whereCondition) {
        this.whereCondition = whereCondition;
    }

    public Integer getPageNo() {
        if (pageNo == null) {
            return PageConstant.DEFAULT_PAGE_NO;
        }else if(pageNo< PageConstant.DEFAULT_PAGE_NO){
            return PageConstant.DEFAULT_PAGE_NO;
        }else{
            return pageNo;
        }
    }

    public Integer getPageSize() {
        if (pageSize == null) {
            return PageConstant.PAGE_SIZE_20;
        }
        if(pageSize<PageConstant.PAGE_SIZE_0){
            return PageConstant.PAGE_SIZE_20;
        }
        if(limitPage && pageSize>PageConstant.PAGE_SIZE_100){
            return PageConstant.PAGE_SIZE_100;
        }
        return pageSize;
    }

    public Integer getOffset() {
        return (this.getPageNo() - 1) * this.getPageSize();
    }

    public Integer getRow() {
        return getPageSize();
    }

    @Override
    public String toString() {
        return "QueryBase{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", limitPage=" + limitPage +
                ", orderCondition='" + orderCondition + '\'' +
                ", whereCondition='" + whereCondition + '\'' +
                ", beginDateTime=" + beginDateTime +
                ", endDateTime=" + endDateTime +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                '}';
    }
}
