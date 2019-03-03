package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 积分历史
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ScoreHistory extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 803308973609346463L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 积分类型，1-登陆，2-发表文章成功，3-回帖，4-签到
     */
    private Integer type;
    /**
     * 积分类型，1-登陆，2-发表文章成功，3-回帖，4-签到
     */
    private String typeName;
    /**
     * 积分
     */
    private Integer score;
    /**
     * 积分日期
     */
    private Date scoreDate;
}
