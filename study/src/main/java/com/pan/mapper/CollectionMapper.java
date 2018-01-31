package com.pan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pan.entity.Collection;

/**
 * 
 * @author Administrator
 *
 */
public interface CollectionMapper {
	/**
	 * 分页查询收藏文章
	 * @param params
	 * @return
	 */
	public List<Collection> findByParams(Map<String,Object> params);
	/**
	 * 查询分页
	 * @param params
	 * @return
	 */
	public int getCountByParams(Map<String,Object> params);
	/**
	 * 新增收藏
	 * @param collection
	 */
	public void addCollection(Collection collection);
	/**
	 * 删除收藏
	 * @param userId
	 * @param articleId
	 */
	public void deleteByUserIdAndArticleId(@Param("userId")String userId,@Param("articleId")String articleId);
	/**
	 * 查询用户收藏
	 * @param userId
	 * @param articleId
	 * @return
	 */
	public Collection findUserCollection(@Param("userId")String userId,@Param("articleId")String articleId);
}
