package com.pan.common.vo;

import com.pan.common.enums.ResultCodeEnum;


/**
 * 返回消息
 * @author Administrator
 *
 */
public class ResultMsg {
	
	private String code;
	private Object msg;
	private Object data;
	
	/**
	 * 自定义返回信息
	 * @param resultCodeEnum
	 * @param msg
	 * @return
	 */
	public static ResultMsg build(ResultCodeEnum resultCodeEnum, Object msg){
		return new ResultMsg(resultCodeEnum.getCode(), msg);
	}
	
	/**
	 * 成功信息
	 * @return
	 */
	public static ResultMsg ok(){
		return ResultMsg.ok(ResultCodeEnum.OK.getMsg());
	}
	
	/**
	 * 成功信息
	 * @return
	 */
	public static ResultMsg ok(Object msg){
		return new ResultMsg(ResultCodeEnum.OK.getCode(), msg);
	}
	
	/**
	 * 成功信息
	 * @return
	 */
	public static ResultMsg build(ResultCodeEnum resultCodeEnum, Object msg, Object data){
		return new ResultMsg(resultCodeEnum.getCode(),msg,data);
	}
	
	/**
	 * 成功信息
	 * @return
	 */
	public static ResultMsg ok(Object msg,Object data){
		return new ResultMsg(ResultCodeEnum.OK.getCode(), msg,data);
	}
	
	/**
	 * 失败信息
	 * @return
	 */
	public static ResultMsg fail(){
		return ResultMsg.ok(ResultCodeEnum.FAIL.getMsg());
	}
	
	/**
	 * 失败信息
	 * @return
	 */
	public static ResultMsg fail(Object msg){
		return new ResultMsg(ResultCodeEnum.FAIL.getCode(), msg);
	}
	
	private ResultMsg(String code, Object msg) {
		this.code = code;
		this.msg = msg;
	}

	private ResultMsg(String code, Object msg, Object data) {
		this(code,msg);
		this.data=data;
	}

	public String getCode() {
		return code;
	}

	public Object getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}
}
