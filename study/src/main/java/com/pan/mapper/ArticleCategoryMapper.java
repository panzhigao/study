package com.pan.mapper;

import com.pan.entity.ArticleCategory;
import java.util.List;

/**
 * @author panzhigao
 */
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory>{

    List<ArticleCategory> findAll();
}