package com.ycgwl.kylin.web.transport.entity;

import com.ycgwl.kylin.entity.RequestEntity;

/**
 * 装载查询实体
 * <p>
 * @developer Create by <a href="mailto:110686@ycgwl.com">wanyj</a> at 2017年6月2日
 */
public class BundleReceiptSearchModel extends RequestEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ydbhid;		//单号
	private String fazhan;		//发站
	private String daozhan;		//到站
	private String wxName;		//外线名称
	private String chxh;		//车牌号
//	private String driverName;	//司机姓名
	private String fcdate;	//发车时间范围
	private String dddate;	//到达时间范围
	private String lrdate;	//录入时间范围
	private String clientName;      //客户名称
	
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public String getFazhan() {
		return fazhan;
	}
	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}
	public String getDaozhan() {
		return daozhan;
	}
	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}
	public String getWxName() {
		return wxName;
	}
	public void setWxName(String wxName) {
		this.wxName = wxName;
	}
	public String getChxh() {
		return chxh;
	}
	public void setChxh(String chxh) {
		this.chxh = chxh;
	}
	public String getFcdate() {
		return fcdate;
	}
	public void setFcdate(String fcdate) {
		this.fcdate = fcdate;
	}
	public String getDddate() {
		return dddate;
	}
	public void setDddate(String dddate) {
		this.dddate = dddate;
	}
	public String getLrdate() {
		return lrdate;
	}
	public void setLrdate(String lrdate) {
		this.lrdate = lrdate;
	}
	
}
