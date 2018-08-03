package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;

public class BundleReceiptHomePageDetail extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private String ydbhid;
	private String fhdwmch;
	private String pinming;
	private Integer jianshu;
	private BigDecimal zhl;
	private BigDecimal tiji;
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public String getFhdwmch() {
		return fhdwmch;
	}
	public void setFhdwmch(String fhdwmch) {
		this.fhdwmch = fhdwmch;
	}
	public String getPinming() {
		return pinming;
	}
	public void setPinming(String pinming) {
		this.pinming = pinming;
	}
	public Integer getJianshu() {
		return jianshu;
	}
	public void setJianshu(Integer jianshu) {
		this.jianshu = jianshu;
	}
	public BigDecimal getZhl() {
		return zhl;
	}
	public void setZhl(BigDecimal zhl) {
		this.zhl = zhl;
	}
	public BigDecimal getTiji() {
		return tiji;
	}
	public void setTiji(BigDecimal tiji) {
		this.tiji = tiji;
	}
	
}
