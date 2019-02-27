package com.pan.service;

import com.pan.entity.BaseEntity;

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
}
