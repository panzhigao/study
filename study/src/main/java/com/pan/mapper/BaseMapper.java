package com.pan.mapper;

import com.pan.entity.BaseEntity;
import com.pan.query.QueryBase;

import java.util.List;

/**
 * @author panzhigao
 */
public interface BaseMapper<T extends BaseEntity> {
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    T selectByPrimaryKey(Long id);
    /**
     * 根据主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);
    /**
     * 新增记录
     * @param t
     * @return
     */
    int insertSelective(T t);
    /**
     * 更新非空字段
     * @param t
     * @return
     */
    int updateByPrimaryKeySelective(T t);
    /**
     * 分页查询
     * @param queryBase
     * @return
     */
    List<T> findPagable(QueryBase queryBase);
}
