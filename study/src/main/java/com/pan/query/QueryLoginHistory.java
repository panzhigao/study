package com.pan.query;

/**
 * @author panzhigao
 */
public class QueryLoginHistory extends QueryBase{
    /**
     * 用户id
     */
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "QueryLoginHistory{" +
                "userId='" + userId + '\'' +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", orderCondition='" + orderCondition + '\'' +
                ", whereCondition='" + whereCondition + '\'' +
                '}';
    }
}
