package com.ycgwl.kylin.web.transport.entity;

import com.ycgwl.kylin.entity.RequestEntity;

public class TransportOrderSearchModel extends RequestEntity {

	private static final long serialVersionUID = -4242647195741528045L;

	/** 运单编号 */
	private String ydbhid;
	
	/** 始发地 */
	private Object beginPlacename;
	
	/** 到站 */
	private String daozhan;
	
	/** 客户编码*/
	private String khbm;
	
	/** 客户名称 */
	private String fhdwmch;
	
	/** 收货人名称*/
	private String shhrmch;
	
	/** 运输号码 */
	private String yshhm;
	
	/** 承运人 */
	private String hyy;
	
	/** 托运日期 */
	private String searchdate;

	/**装载状态 */
	private int status;
	
	/** 单证（制票）人 */
	private String zhipiao;
	
	/** 用户判断装载页面的返回具体的页面 */
	private String pageType;
	
	/** 客户单号 */
	private String khdh;
	
	/** 是否 做财凭 */
	private String isFinanceCertify;
	
	private String hasReceipt;
	
	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getShhrmch() {
		return shhrmch;
	}

	public void setShhrmch(String shhrmch) {
		this.shhrmch = shhrmch;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public Object getBeginPlacename() {
		return beginPlacename;
	}

	public void setBeginPlacename(Object beginPlacename) {
		this.beginPlacename = beginPlacename;
	}

	public String getDaozhan() {
		return daozhan;
	}

	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}

	public String getKhbm() {
		return khbm;
	}

	public void setKhbm(String khbm) {
		this.khbm = khbm;
	}

	public String getFhdwmch() {
		return fhdwmch;
	}

	public void setFhdwmch(String fhdwmch) {
		this.fhdwmch = fhdwmch;
	}

	public String getYshhm() {
		return yshhm;
	}

	public void setYshhm(String yshhm) {
		this.yshhm = yshhm;
	}

	public String getHyy() {
		return hyy;
	}

	public void setHyy(String hyy) {
		this.hyy = hyy;
	}

	public String getSearchdate() {
		return searchdate;
	}

	public void setSearchdate(String searchdate) {
		this.searchdate = searchdate;
	}

	public String getZhipiao() {
		return zhipiao;
	}

	public void setZhipiao(String zhipiao) {
		this.zhipiao = zhipiao;
	}

	public String getKhdh() {
		return khdh;
	}

	public void setKhdh(String khdh) {
		this.khdh = khdh;
	}

	public String getIsFinanceCertify() {
		return isFinanceCertify;
	}

	public void setIsFinanceCertify(String isFinanceCertify) {
		this.isFinanceCertify = isFinanceCertify;
	}

	public String getHasReceipt() {
		return hasReceipt;
	}

	public void setHasReceipt(String hasReceipt) {
		this.hasReceipt = hasReceipt;
	}
	
}
