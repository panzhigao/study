package com.pan.dto;

import com.pan.entity.Article;
import lombok.Data;

/**
 * @author panzhigao
 */
@Data
public class ArticleDTO extends Article{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1969627060058259340L;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String userPortrait;
}
