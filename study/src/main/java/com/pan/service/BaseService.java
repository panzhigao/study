package com.pan.service;

import com.pan.common.vo.PageDataMsg;
import com.pan.entity.BaseEntity;
import com.pan.query.QueryBase;
import java.util.List;

/**
 * @author panzhigao
 */
public interface BaseService<T extends BaseEntity> {
    /**
     * 根据id获取唯一一条数据
     *
     * @param id
     * @return
     */
    T selectByPrimaryKey(Long id);

    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增记录
     *
     * @param t
     * @return
     */
    int insertSelective(T t);

    /**
     * 更新非空字段
     *
     * @param t
     * @return
     */
    int updateByPrimaryKeySelective(T t);
    /**
     * 分页查询
     * @param queryBase
     * @return
     */
    List<T> findPageable(QueryBase queryBase);
    /**
     * 查询分页数据，包括总条数
     * @param queryBase
     * @return
     */
    PageDataMsg findPageableMap(QueryBase queryBase);
    /**
     * 查询记录数
     * @param queryBase
     * @return
     */
    int countByParams(QueryBase queryBase);
}
