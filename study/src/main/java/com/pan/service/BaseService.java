package com.pan.service;

/**
 * @author panzhigao
 */
public interface BaseService<T> {
    /**
     * 根据id获取唯一一条数据
     * @param id
     * @return
     */
    T findById(Long id);
}
