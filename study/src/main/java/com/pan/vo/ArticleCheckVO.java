package com.pan.vo;

import com.pan.entity.ArticleCheck;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者
 * @version 创建时间：2018年6月22日 下午3:28:21
 * 类说明
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ArticleCheckVO extends ArticleCheck{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2289815052936357272L;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 昵称
	 */
	private String nickname;
}
