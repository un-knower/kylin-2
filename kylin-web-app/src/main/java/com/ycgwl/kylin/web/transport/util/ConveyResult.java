package com.ycgwl.kylin.web.transport.util;

import java.util.HashMap;

/**
 * 运单批量返回的json数据,
 * 		备注:2017年10月24日 13:23:58common包下有JsonResult的json返回数据格式,不要使用这个类了
 * <p>
 */
public class ConveyResult extends HashMap<String, Object> {

	private static final long serialVersionUID = 4939149293296228160L;

	public static final ConveyResult SUCCESS = new ConveyResult();
	
	private static final String SUCCESS_KEY = "reason";
	private static final String SUCCESS_MESSAGE = "操作成功";
	private static final String CODE_KEY = "resultCode";
	private static final Integer SUCCESS_CODE = 200;
	private static final String SUCCESS_LIST = "successList";
	private static final String FALSE_LIST = "falseList";
	
	public ConveyResult() {
		super();
		setCode(SUCCESS_CODE);
		setSuccess(SUCCESS_MESSAGE);
	}
	public ConveyResult(Integer code,String successMessage){
		super();
		setCode(code);
		setSuccess(successMessage);
	}
	
	public String getSuccess(){
		return String.valueOf(get(SUCCESS_KEY));
	}
	
	public void setSuccess(String success) {
		put(SUCCESS_KEY, success);
	}
	public String getSuccessList() {
		return String.valueOf(get(SUCCESS_LIST));
	}
	public void setSuccessList(Object successList) {
		put(SUCCESS_LIST, successList);
	}
	
	public String getFalseList() {
		return String.valueOf(get(FALSE_LIST));
	}
	public void setFalseList(Object falseList) {
		put(FALSE_LIST, falseList);
	}
	
	public String getCode() {
		return String.valueOf(get(CODE_KEY));
	}
	public void setCode(Integer code) {
		put(CODE_KEY, code);
	}
}
