/**
 * kylin-common
 * com.ycgwl.kylin.exception
 */
package com.ycgwl.kylin.exception;


/**
 * 系统参数异常,任何参数有不合理之处都可抛出此异常
 * <p>
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:47:30
 */
public class ParameterException extends RuntimeException {

	private static final long serialVersionUID = -5898218861108473496L;

	/**
	 * 参数名称
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:49:07
	 */
	private String parameterName;
	/**
	 * 当前参数的值
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:49:26
	 */
	private Object parameterValue;
	/**
	 * 提示消息
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:49:29
	 */
	private String tipMessage;
	
	/**
	 * 创建一个新的 ParameterException实例. 
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:48:23
	 * @param parameterName   参数名称
	 * @param parameterValue  参数当前值
	 * @param message         参数异常提示消息
	 */
	public ParameterException(String parameterName, Object parameterValue, String message) {
		super(String.format("参数异常:%s = %s, %s", parameterName, String.valueOf(parameterValue), message));
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
		this.tipMessage = message;
	}
	/**
	 * 创建一个新的 ParameterException实例. 
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 12:48:52
	 * @param message  参数异常提示消息
	 */
	public ParameterException(String message) {
		super(message);
		this.tipMessage = message;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public Object getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(Object parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getTipMessage() {
		return tipMessage;
	}
	public void setTipMessage(String tipMessage) {
		this.tipMessage = tipMessage;
	}
}
