package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;
import java.util.Date;

public class DispatchCarPickGoodsDetailFive implements Serializable{
	private String gsid;
	private Integer id;
	private Integer detailid;
	private String clxz;
	private String ch;
	private String yfbz;
	private Double yfjs;
	private Double tc;
	private Date qsdfhtime;
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
	public Integer getDetailid() {
		return detailid;
	}
	public void setDetailid(Integer detailid) {
		this.detailid = detailid;
	}
	public String getClxz() {
		return clxz;
	}
	public void setClxz(String clxz) {
		this.clxz = clxz;
	}
	public String getCh() {
		return ch;
	}
	public void setCh(String ch) {
		this.ch = ch;
	}
	public String getYfbz() {
		return yfbz;
	}
	public void setYfbz(String yfbz) {
		this.yfbz = yfbz;
	}
	public Double getYfjs() {
		return yfjs;
	}
	public void setYfjs(Double yfjs) {
		this.yfjs = yfjs;
	}
	public Double getTc() {
		return tc;
	}
	public void setTc(Double tc) {
		this.tc = tc;
	}
	public Date getQsdfhtime() {
		return qsdfhtime;
	}
	public void setQsdfhtime(Date qsdfhtime) {
		this.qsdfhtime = qsdfhtime;
	}
}
