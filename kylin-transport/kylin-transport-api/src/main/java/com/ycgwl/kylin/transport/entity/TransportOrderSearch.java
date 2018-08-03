package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.transport.entity.adjunct.PageAble;

public class TransportOrderSearch extends PageAble{

	private static final long serialVersionUID = 2366596344792333142L;

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
	
	/** 运输号码 */
	private String yshhm;
	
	/** 承运人 */
	private String hyy;
	
	/** 托运日期开始*/
	private String fhshjStart;
	
	/** 托运日期开始*/
	private String fhshjEnd;
	
	/** 发站 */
	private String fazhan;
	
	/**装载状态*/
	private int status;

	/** 单证（制票）人 */
	private String zhipiao;
	
	/** 客户单号 */
	private String khdh;
	
	/** 收货人名称 */
	private String shhrmch;
	
	/** 是否 做财凭 */
	private String isFinanceCertify;
	
	private String hasReceipt;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getFhshjStart() {
		return fhshjStart;
	}

	public void setFhshjStart(String fhshjStart) {
		this.fhshjStart = fhshjStart;
	}

	public String getFhshjEnd() {
		return fhshjEnd;
	}

	public void setFhshjEnd(String fhshjEnd) {
		this.fhshjEnd = fhshjEnd;
	}

	public String getFazhan() {
		return fazhan;
	}

	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
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

	public String getShhrmch() {
		return shhrmch;
	}

	public void setShhrmch(String shhrmch) {
		this.shhrmch = shhrmch;
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
