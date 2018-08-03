package com.ycgwl.kylin.transport.entity;

import java.util.Date;

public class SendMessageLogEntity {
	/**
	 * 内容
	 */
	public String context;
	/**
	 * 手机号
	 */
	public String phone;
	/**
	 * 发送标识   0 未发送   1：发送成功   2：发送失败    
	 */
	public int flag;
	
	private String ydbhid;
	private Date sendTime;
	private String sendphoneNb;
	private String sendContent;
	private String sendFlag;
	private int sendStatus;
	private int jianshu;
	private String confirmphone;
	public String getConfirmphone() {
		return confirmphone;
	}
	public void setConfirmphone(String confirmphone) {
		this.confirmphone = confirmphone;
	}
	public int getJianshu() {
		return jianshu;
	}
	public void setJianshu(int jianshu) {
		this.jianshu = jianshu;
	}
	public int getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendphoneNb() {
		return sendphoneNb;
	}
	public void setSendphoneNb(String sendphoneNb) {
		this.sendphoneNb = sendphoneNb;
	}
	public String getSendContent() {
		return sendContent;
	}
	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}
