package com.pan.common.vo;

import com.pan.common.enums.ResultCodeEmun;


/**
 * 返回消息
 * @author Administrator
 *
 */
public class ResultMsg {
	
	private String code;
	private Object msg;
	
	/**
	 * 自定义返回信息
	 * @param resultCodeEmun
	 * @param msg
	 * @return
	 */
	public static ResultMsg buil(ResultCodeEmun resultCodeEmun,Object msg){
		return new ResultMsg(resultCodeEmun.getCode(), msg);
	}
	
	/**
	 * 成功信息
	 * @return
	 */
	public static ResultMsg ok(){
		return ResultMsg.ok(ResultCodeEmun.OK.getMsg());
	}
	
	/**
	 * 成功信息
	 * @return
	 */
	public static ResultMsg ok(Object msg){
		return new ResultMsg(ResultCodeEmun.OK.getCode(), msg);
	}
	
	/**
	 * 失败信息
	 * @return
	 */
	public static ResultMsg fail(){
		return ResultMsg.ok(ResultCodeEmun.FAIL.getMsg());
	}
	
	/**
	 * 失败信息
	 * @return
	 */
	public static ResultMsg fail(Object msg){
		return new ResultMsg(ResultCodeEmun.FAIL.getCode(), msg);
	}
	
	private ResultMsg(String code, Object msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public Object getMsg() {
		return msg;
	}
}
