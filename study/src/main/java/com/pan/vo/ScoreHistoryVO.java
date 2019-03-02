package com.pan.vo;

import com.pan.entity.ScoreHistory;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户积分
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ScoreHistoryVO extends ScoreHistory{

	/**
	 * 
	 */
	private static final long serialVersionUID = -431889301381801069L;
	/**
	 * 用户头像
	 */
	private String userPortrait;
	/**
	 * 用户昵称
	 */
	private String nickname;
}
