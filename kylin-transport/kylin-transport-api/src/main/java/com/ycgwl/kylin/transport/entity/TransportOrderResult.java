package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class TransportOrderResult extends BaseEntity{
	
	private static final long serialVersionUID = -3818671518183316133L;

	/** 运单编号 */
	private String ydbhid; 
	
	/** 客户名称*/
	private String fhdwmch;
	
	/** 发货重量 */
	private BigDecimal fhzhl;
	
	/** 发货体积 */
	private BigDecimal fhtiji;
	
	/** 件数 */
	private Integer jianshu;
	
	/** 始发地 */
	private Object beginPlacename;
	
	/**目的地*/
	private Object endPlacename;
	
	/** 收货人名称 */
	private String shhrmch;
	
	/** 运输方式 */
	private String ysfs;
	
	/** 服务方式*/
	private Integer fwfs;
	
	/** 合计金额 */
	private BigDecimal hjje;
	
	/** 财凭号 */
	private BigDecimal cwpzhbh;
	
	/** 体积 */
	private Double tiji;
	
	/** 重量 */
	private Double zhl;
	
	/** 装载序号 */
	private Integer bundel;

	/** 托运时间 */
	private Date fhshj;
	
	/** 是否返单 */
	private int hasReceipt;
	
	public BigDecimal getCwpzhbh() {
		return cwpzhbh;
	}

	public void setCwpzhbh(BigDecimal cwpzhbh) {
		this.cwpzhbh = cwpzhbh;
	}

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

	public BigDecimal getFhzhl() {
		return fhzhl;
	}

	public void setFhzhl(BigDecimal fhzhl) {
		this.fhzhl = fhzhl;
	}

	public BigDecimal getFhtiji() {
		return fhtiji;
	}

	public void setFhtiji(BigDecimal fhtiji) {
		this.fhtiji = fhtiji;
	}

	public Integer getJianshu() {
		return jianshu;
	}

	public void setJianshu(Integer jianshu) {
		this.jianshu = jianshu;
	}

	public Object getBeginPlacename() {
		return beginPlacename;
	}

	public void setBeginPlacename(Object beginPlacename) {
		this.beginPlacename = beginPlacename;
	}

	public Object getEndPlacename() {
		return endPlacename;
	}

	public void setEndPlacename(Object endPlacename) {
		this.endPlacename = endPlacename;
	}

	public String getShhrmch() {
		return shhrmch;
	}

	public void setShhrmch(String shhrmch) {
		this.shhrmch = shhrmch;
	}

	public String getYsfs() {
		return ysfs;
	}

	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}

	public Integer getFwfs() {
		return fwfs;
	}

	public void setFwfs(Integer fwfs) {
		this.fwfs = fwfs;
	}

	public BigDecimal getHjje() {
		return hjje;
	}

	public void setHjje(BigDecimal hjje) {
		this.hjje = hjje;
	}
	
	public Double getTiji() {
		return tiji;
	}

	public void setTiji(Double tiji) {
		this.tiji = tiji;
	}

	public Double getZhl() {
		return zhl;
	}

	public void setZhl(Double zhl) {
		this.zhl = zhl;
	}

	public Integer getBundel() {
		return bundel;
	}

	public void setBundel(Integer bundel) {
		this.bundel = bundel;
	}

	public Date getFhshj() {
		return fhshj;
	}

	public void setFhshj(Date fhshj) {
		this.fhshj = fhshj;
	}

	public int getHasReceipt() {
		return hasReceipt;
	}

	public void setHasReceipt(int hasReceipt) {
		this.hasReceipt = hasReceipt;
	}
	
}
