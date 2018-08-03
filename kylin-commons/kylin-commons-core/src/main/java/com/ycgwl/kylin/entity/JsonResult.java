package com.ycgwl.kylin.entity;

import com.ycgwl.kylin.ResultCode;

import java.util.HashMap;

public class JsonResult extends HashMap<String, Object> {

	private static final long serialVersionUID = 4939149293296228160L;

	public JsonResult() {
		super();
		setCode(ResultCode.SUCCESS);
		setSuccess(true);
		setMessage("操作成功");
	}
	
	public JsonResult(String code, boolean success, String message) {
		super();
		setCode(code);
		setSuccess(success);
		setMessage(message);
	}
	
	public JsonResult(String code, String message) {
		this(code, false, message);
	}

	public boolean isSuccess() {
		Object success = get("success");
		if(success != null){
			return new Boolean(String.valueOf(success));
		}
		return false;
	}
	
	public void setSuccess(boolean success) {
		put("success", success);
	}
	public String getMessage() {
		return String.valueOf(get("message"));
	}
	public void setMessage(String message) {
		put("message", message);
	}
	
	public String getCode() {
		return String.valueOf(get("code"));
	}
	public void setCode(String code) {
		put("code", code);
	}
	public static JsonResult getConveyResult(String resultCode,String reason) {
		JsonResult result = new JsonResult();
		result.clear();
		result.put("resultCode", resultCode);
		result.put("reason", reason);
		return result;
	}
	
	public static JsonResult getConveyResult(String resultCode,String reason, Object data) {
		JsonResult result = new JsonResult();
		result.clear();
		result.put("resultCode", resultCode);
		result.put("reason", reason);
		result.put("data", data);
		return result;
	}
}
