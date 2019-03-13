package com.pan.mapper;

import org.apache.ibatis.annotations.Param;
import com.pan.entity.Collection;

/**
 * 
 * @author Administrator
 *
 */
public interface CollectionMapper extends BaseMapper<Collection>{
	/**
	 * 删除收藏
	 * @param userId
	 * @param articleId
	 */
	void deleteByUserIdAndArticleId(@Param("userId")String userId,@Param("articleId")String articleId);
}
