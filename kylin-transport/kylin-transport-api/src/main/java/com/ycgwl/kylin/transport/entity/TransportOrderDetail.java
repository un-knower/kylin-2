package com.ycgwl.kylin.transport.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 运单明细
 * The persistent class for the HWYDXZ database table.
 * 
 */
@JsonInclude(Include.NON_NULL) 
public class TransportOrderDetail extends BaseEntity {
	
	private static final long serialVersionUID = -5380176497221415928L;

	private String ydbhid;	//运单编码
	
	private Integer ydxzh;	//序号

	private String beizhu;	//备注

	private String bzh;		//包装

	private String cangwei;	//仓位

	private String chxh;	//车厢号

	private Double decprice;	//声明价值

	private Short isKaiyun;	//快运标识

	private Integer jffs;		//计费方式

	private Integer jianshu;	//件数	

	private String pinming;		//品名

	private Double qchl;	//轻货叉车量

	private Double qzxl;	//轻货装卸量

	private String rowguid;		//行标识

	private Double tbje;	//保价金额

	private Double tiji;	//体积

	private String xh;			//型号

	private Boolean yfh;		//

	private Double yunjia;	//运价

	private Double zchl; 	//重货叉车量
		
	private Double zhl;		//重量

	private Double zzxl;	//重货装卸量

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public Integer getYdxzh() {
		return ydxzh;
	}

	public void setYdxzh(Integer ydxzh) {
		this.ydxzh = ydxzh;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getBzh() {
		return bzh;
	}

	public void setBzh(String bzh) {
		this.bzh = bzh;
	}

	public String getCangwei() {
		return cangwei;
	}

	public void setCangwei(String cangwei) {
		this.cangwei = cangwei;
	}

	public String getChxh() {
		return chxh;
	}

	public void setChxh(String chxh) {
		this.chxh = chxh;
	}

	public Short getIsKaiyun() {
		return isKaiyun;
	}

	public void setIsKaiyun(Short isKaiyun) {
		this.isKaiyun = isKaiyun;
	}

	public Integer getJffs() {
		return jffs;
	}

	public void setJffs(Integer jffs) {
		this.jffs = jffs;
	}

	public Integer getJianshu() {
		return jianshu;
	}

	public void setJianshu(Integer jianshu) {
		this.jianshu = jianshu;
	}

	public String getPinming() {
		return pinming;
	}

	public void setPinming(String pinming) {
		this.pinming = pinming;
	}

	public String getRowguid() {
		return rowguid;
	}

	public void setRowguid(String rowguid) {
		this.rowguid = rowguid;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public Boolean getYfh() {
		return yfh;
	}

	public void setYfh(Boolean yfh) {
		this.yfh = yfh;
	}

	public Double getDecprice() {
		return decprice;
	}

	public void setDecprice(Double decprice) {
		this.decprice = decprice;
	}

	public Double getQchl() {
		return qchl;
	}

	public void setQchl(Double qchl) {
		this.qchl = qchl;
	}

	public Double getQzxl() {
		return qzxl;
	}

	public void setQzxl(Double qzxl) {
		this.qzxl = qzxl;
	}

	public Double getTbje() {
		return tbje;
	}

	public void setTbje(Double tbje) {
		this.tbje = tbje;
	}

	public Double getTiji() {
		return tiji;
	}

	public void setTiji(Double tiji) {
		this.tiji = tiji;
	}

	public Double getYunjia() {
		return yunjia;
	}

	public void setYunjia(Double yunjia) {
		this.yunjia = yunjia;
	}

	public Double getZchl() {
		return zchl;
	}

	public void setZchl(Double zchl) {
		this.zchl = zchl;
	}

	public Double getZhl() {
		return zhl;
	}

	public void setZhl(Double zhl) {
		this.zhl = zhl;
	}

	public Double getZzxl() {
		return zzxl;
	}

	public void setZzxl(Double zzxl) {
		this.zzxl = zzxl;
	}
}