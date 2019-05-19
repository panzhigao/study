package com.pan.service;

import com.pan.common.vo.PageDataMsg;
import com.pan.entity.BaseEntity;
import com.pan.mapper.BaseMapper;
import com.pan.query.QueryBase;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象
 * @author panzhigao
 */
public abstract class AbstractBaseService<T extends BaseEntity,M extends BaseMapper<T>> implements BaseService<T>{
    /**
     * 获取mapper
     * @param
     * @return
     */
	protected abstract M getBaseMapper();
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
     * 根据主键更新非空字段
     * @param t
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(T t){
        return getBaseMapper().updateByPrimaryKeySelective(t);
    }
    /**
     * 分页查询
     * @param queryBase
     * @return
     */
    @Override
    public List<T> findPageable(QueryBase queryBase) {
        return getBaseMapper().findPageable(queryBase);
    }

    /**
     * 分页查询
     * @param queryBase
     * @return
     */
    @Override
    public PageDataMsg findPageableMap(QueryBase queryBase) {
        List<T> list = new ArrayList<>();
        int total=countByParams(queryBase);
        if(total>0){
            list=findPageable(queryBase);
        }
        return new PageDataMsg(total,list);
    }
    /**
     * 查询数据条数
     * @param queryBase
     * @return
     */
	@Override
	public int countByParams(QueryBase queryBase) {
		return getBaseMapper().countByParams(queryBase);
	}
    
}
