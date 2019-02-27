package com.pan.service;

import com.pan.entity.BaseEntity;
import com.pan.mapper.BaseMapper;

/**
 * 抽象
 * @author panzhigao
 */
public abstract class AbstractBaseService<T extends BaseEntity,M extends BaseMapper<T>> implements BaseService<T>{
    /**
     * 获取mapper
     * @param <M>
     * @return
     */
    protected abstract <M>BaseMapper<T> getBaseMapper();

    /**
     * 根据id获取唯一一条数据
     *
     * @param id
     * @return
     */
    @Override
    public T selectByPrimaryKey(Long id){
        return getBaseMapper().selectByPrimaryKey(id);
    }


    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        return getBaseMapper().deleteByPrimaryKey(id);
    }

    /**
     * 新增记录
     *
     * @param t
     * @return
     */
    @Override
    public int insertSelective(T t){
        return getBaseMapper().insertSelective(t);
    }

    /**
     * 更新非空字段
     *
     * @param t
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(T t){
        return getBaseMapper().updateByPrimaryKeySelective(t);
    }
}
