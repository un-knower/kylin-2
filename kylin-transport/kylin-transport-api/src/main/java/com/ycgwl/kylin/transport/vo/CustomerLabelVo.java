package com.ycgwl.kylin.transport.vo;

import java.io.Serializable;

public class CustomerLabelVo implements Serializable {

	private static final long serialVersionUID = 1340468337984421714L;
	
	/** 细则序号 */
	public String  ydxzh;
	
	/** 品名 */
	public String pinming;
	
	/** 件数 */
	public int jianshu;
	
	/** 型号 */
	public String xh;
	
	/** 始发城市 */
	public String beginPlacename;
	
	/** 目的城市 */
	public String endPlacename;
	
	/** 始发站点 */
	public String fazhan;
	
	/** 目的站点 */
	public String dzshhd;
	
	/** 运单号 */
	public String ydbhid;
	
	/** 配送地址 */
	public String fhdwdzh;
	
	

	public String getYdxzh() {
		return ydxzh;
	}

	public void setYdxzh(String ydxzh) {
		this.ydxzh = ydxzh;
	}

	public String getPinming() {
		return pinming;
	}

	public void setPinming(String pinming) {
		this.pinming = pinming;
	}

	public int getJianshu() {
		return jianshu;
	}

	public void setJianshu(int jianshu) {
		this.jianshu = jianshu;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getBeginPlacename() {
		return beginPlacename;
	}

	public void setBeginPlacename(String beginPlacename) {
		this.beginPlacename = beginPlacename;
	}

	public String getEndPlacename() {
		return endPlacename;
	}

	public void setEndPlacename(String endPlacename) {
		this.endPlacename = endPlacename;
	}

	public String getFazhan() {
		return fazhan;
	}

	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}

	public String getDzshhd() {
		return dzshhd;
	}

	public void setDzshhd(String dzshhd) {
		this.dzshhd = dzshhd;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public String getFhdwdzh() {
		return fhdwdzh;
	}

	public void setFhdwdzh(String fhdwdzh) {
		this.fhdwdzh = fhdwdzh;
	}

}
