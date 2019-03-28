package com.pan.query;

import com.pan.common.annotation.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午3:49:39
 * 类说明
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryArticle extends QueryBase {
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 文章状态 0-审核未通过,1-草稿,2-发布中,3-发布成功
     */
    private Integer status;
    /**
     * 文章标题
     */
    @QueryParam(highLightFlag = true)
    private String title;
    /**
     * 文章类型
     * 1-文章 2-系统消息
     */
    private Integer type;
    /**
     * 是否热门
     */
    private String isHot;
    /**
     * 置顶系数
     */
    private Integer stick;
    /**
     * 是否是精品贴
     */
    private Integer highQuality;
}
