package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

public class TransportSignRecordResult extends BaseEntity{

	private static final long serialVersionUID = -6336209717478825672L;

	/** 运单编号 */
	private String ydbhid; 
	
	/** 发站*/
	private String fazhan;
	
	/** /到站 */
	private String daozhan;
	
	/** 发货人（客户名称） */
	private String fhdwmch;
	
	/** 客户座机电话（发货人电话） */
	private String fhdwlxdh;
	
	/** 收货人名称 */
	private String shhrmch;
	
	/** 收货人座机电话 */
	private String shhrlxdh;
	
	/** 送货里程 */
	private Integer shlc;
	
	/** 公里数 */
	private String lc;
	
	private Integer max;
	
	private Integer min;
	
	private Boolean isGreenWay;
	
	/** 录入人 */
	private String lrr;
	
	/** 录入时间 */
	private String lrTime;
	
	/** 是否已签收（true :已签收，不能再次签收，可以查看   false:未签收，正常签收） */
	private Boolean isSign;
	
	/**
	 * 签收状态(默认是0)
	 */
	private Integer qszt;
	
	private String qsr;
	
	private Double psjianshu;
	
	private Double dsjianshu;
	
	private String qsTime;
	
	private String comm;
	
	private String qsrphone;
	
	/** 是否撤销过签收（true :撤销过签收 ,签收时间默认第一次签收时间，不可编辑  false:未撤销过签收） */
	private Boolean isUndo;

	/** 撤销次数 */
	private Integer cxNumber;
	
	/** 签收类型 0或null:系统 1:手动 */
	public Integer signType=0;	
	
	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
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

	public String getFhdwmch() {
		return fhdwmch;
	}

	public void setFhdwmch(String fhdwmch) {
		this.fhdwmch = fhdwmch;
	}

	public String getFhdwlxdh() {
		return fhdwlxdh;
	}

	public void setFhdwlxdh(String fhdwlxdh) {
		this.fhdwlxdh = fhdwlxdh;
	}

	public String getShhrmch() {
		return shhrmch;
	}

	public void setShhrmch(String shhrmch) {
		this.shhrmch = shhrmch;
	}

	public String getShhrlxdh() {
		return shhrlxdh;
	}

	public void setShhrlxdh(String shhrlxdh) {
		this.shhrlxdh = shhrlxdh;
	}

	public Integer getShlc() {
		return shlc;
	}

	public void setShlc(Integer shlc) {
		this.shlc = shlc;
	}

	public String getLc() {
		return lc;
	}

	public void setLc(String lc) {
		this.lc = lc;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Boolean getIsGreenWay() {
		return isGreenWay;
	}

	public void setIsGreenWay(Boolean isGreenWay) {
		this.isGreenWay = isGreenWay;
	}

	public String getLrr() {
		return lrr;
	}

	public void setLrr(String lrr) {
		this.lrr = lrr;
	}

	public String getLrTime() {
		return lrTime;
	}

	public void setLrTime(String lrTime) {
		this.lrTime = lrTime;
	}

	public Boolean getIsSign() {
		return isSign;
	}

	public void setIsSign(Boolean isSign) {
		this.isSign = isSign;
	}

	public Integer getQszt() {
		return qszt;
	}

	public void setQszt(Integer qszt) {
		this.qszt = qszt;
	}

	public String getQsr() {
		return qsr;
	}

	public void setQsr(String qsr) {
		this.qsr = qsr;
	}

	public Double getPsjianshu() {
		return psjianshu;
	}

	public void setPsjianshu(Double psjianshu) {
		this.psjianshu = psjianshu;
	}

	public Double getDsjianshu() {
		return dsjianshu;
	}

	public void setDsjianshu(Double dsjianshu) {
		this.dsjianshu = dsjianshu;
	}

	public String getQsTime() {
		return qsTime;
	}

	public void setQsTime(String qsTime) {
		this.qsTime = qsTime;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public String getQsrphone() {
		return qsrphone;
	}

	public void setQsrphone(String qsrphone) {
		this.qsrphone = qsrphone;
	}

	public Boolean getIsUndo() {
		return isUndo;
	}

	public void setIsUndo(Boolean isUndo) {
		this.isUndo = isUndo;
	}

	public Integer getCxNumber() {
		return cxNumber;
	}

	public void setCxNumber(Integer cxNumber) {
		this.cxNumber = cxNumber;
	}
	
}
