package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;

public class DispatchCarPickGoodsDetail implements Serializable{
	private String gsid;
	private Integer id;
	private Integer detailId;
	private String hwmc;
	private String bz;
	private Integer jianshu;
	private Double zl;
	private Double tj;
	private Double shenMingJiaZhi;
	private Double baoJiaJinE;
	public String getGsid() {
		return gsid;
	}
	public void setGsid(String gsid) {
		this.gsid = gsid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public String getHwmc() {
		return hwmc;
	}
	public void setHwmc(String hwmc) {
		this.hwmc = hwmc;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public Integer getJianshu() {
		return jianshu;
	}
	public void setJianshu(Integer jianshu) {
		this.jianshu = jianshu;
	}
	public Double getZl() {
		return zl;
	}
	public void setZl(Double zl) {
		this.zl = zl;
	}
	public Double getTj() {
		return tj;
	}
	public void setTj(Double tj) {
		this.tj = tj;
	}
	public Double getShenMingJiaZhi() {
		return shenMingJiaZhi;
	}
	public void setShenMingJiaZhi(Double shenMingJiaZhi) {
		this.shenMingJiaZhi = shenMingJiaZhi;
	}
	public Double getBaoJiaJinE() {
		return baoJiaJinE;
	}
	public void setBaoJiaJinE(Double baoJiaJinE) {
		this.baoJiaJinE = baoJiaJinE;
	}
}
