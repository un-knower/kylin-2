/**
 * kylin-common
 * com.ycgwl.kylin.exception
 */
package com.ycgwl.kylin.exception;

/**
 * 业务逻辑异常，任何业务逻辑处理错误时，均可抛出此异常
 * <p>
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:51:11
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1763729725812293662L;
	
	/**
	 * 逻辑异常代码
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:51:44
	 */
	private Integer code;
	/**
	 * 异常提示消息
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:52:17
	 */
	private String tipMessage;

	/**
	 * 创建一个新的 BusinessException实例. 
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:51:56
	 * @param code  异常代码
	 * @param message 异常提示消息
	 * @param cause 异常链
	 */
	public BusinessException(Integer code, String message, Throwable cause) {
		super(String.format("业务异常:code = %d, %s", code, message), cause);
		this.code = code;
		this.tipMessage = message;
	}

	/**
	 * 创建一个新的 BusinessException实例. 
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:53:22
	 * @param message  异常消息
	 * @param cause 异常链
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.tipMessage = message;
	}

	/**
	 * 创建一个新的 BusinessException实例. 
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:54:39
	 * @param message 异常提示消息
	 */
	public BusinessException(String message) {
		super(message);
		this.tipMessage = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getTipMessage() {
		return tipMessage;
	}

	public void setTipMessage(String tipMessage) {
		this.tipMessage = tipMessage;
	}
}
