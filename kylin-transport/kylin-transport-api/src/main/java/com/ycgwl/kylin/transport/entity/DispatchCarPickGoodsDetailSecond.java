package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;

public class DispatchCarPickGoodsDetailSecond implements Serializable{
	private String gsid;
	private Integer id;
	private Integer detailid;
	private String clxz;
	private String ch;
	private String cx;
	private String sj;
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
	public String getCx() {
		return cx;
	}
	public void setCx(String cx) {
		this.cx = cx;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
}
