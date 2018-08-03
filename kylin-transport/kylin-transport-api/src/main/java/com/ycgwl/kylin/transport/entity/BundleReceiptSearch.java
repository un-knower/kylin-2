package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.transport.entity.adjunct.PageAble;


/**
 * TODO Add description
 * 装载清单查询实体类
 * <p>
 * @developer Create by <a href="mailto:110686@ycgwl.com">wanyj</a> at 2017年6月2日
 */
public class BundleReceiptSearch extends PageAble  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ydbhid;			//单号
	private String fazhan;			//发站
	private String daozhan;			//到站
	private String wxName;			//外线名称
	private String chxh;			//车牌号
//	private String driverName;		//司机姓名
	private String fchstarttime;	//发车时间范围1
	private String fchendtime;		//发车时间范围2
	private String yjstarttime;		//预计到达时间范围1
	private String yjendtime;		//预计到达时间范围2
	private String lrsjstarttime;	//录入时间范围1
	private String lrsjendtime;		//录入时间范围2
	private String companyName;		//公司名称
	private String clientName;      //客户名称
	
	
	public String getClientName() {
		return clientName;
	}
	public BundleReceiptSearch setClientName(String clientName) {
		this.clientName = clientName;
		return this;
	}
	public String getCompanyName() {
		return companyName;
	}
	public BundleReceiptSearch setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}
	public String getYdbhid() {
		return ydbhid;
	}
	public BundleReceiptSearch setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
		return this;
	}
	public String getFazhan() {
		return fazhan;
	}
	public BundleReceiptSearch setFazhan(String fazhan) {
		this.fazhan = fazhan;
		return this;
	}
	public String getDaozhan() {
		return daozhan;
	}
	public BundleReceiptSearch setDaozhan(String daozhan) {
		this.daozhan = daozhan;
		return this;
	}
	public String getWxName() {
		return wxName;
	}
	public BundleReceiptSearch setWxName(String wxName) {
		this.wxName = wxName;
		return this;
	}
	public String getChxh() {
		return chxh;
	}
	public BundleReceiptSearch setChxh(String chxh) {
		this.chxh = chxh;
		return this;
	}
	public String getFchstarttime() {
		return fchstarttime;
	}
	public BundleReceiptSearch setFchstarttime(String fchstarttime) {
		this.fchstarttime = fchstarttime;
		return this;
	}
	public String getFchendtime() {
		return fchendtime;
	}
	public BundleReceiptSearch setFchendtime(String fchendtime) {
		this.fchendtime = fchendtime;
		return this;
	}
	public String getYjstarttime() {
		return yjstarttime;
	}
	public BundleReceiptSearch setYjstarttime(String yjstarttime) {
		this.yjstarttime = yjstarttime;
		return this;
	}
	public String getYjendtime() {
		return yjendtime;
	}
	public BundleReceiptSearch setYjendtime(String yjendtime) {
		this.yjendtime = yjendtime;
		return this;
	}
	public String getLrsjstarttime() {
		return lrsjstarttime;
	}
	public BundleReceiptSearch setLrsjstarttime(String lrsjstarttime) {
		this.lrsjstarttime = lrsjstarttime;
		return this;
	}
	public String getLrsjendtime() {
		return lrsjendtime;
	}
	public BundleReceiptSearch setLrsjendtime(String lrsjendtime) {
		this.lrsjendtime = lrsjendtime;
		return this;
	}
	
}
