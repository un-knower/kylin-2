package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 派车签收单明细五
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
 */
public class TransportCarOutDetailFifth extends BaseEntity{

	private static final long serialVersionUID = 5527333136678960487L;

	private Integer detailid;
	
	private String gsid;
	
	private Integer id;
	
	private String clxz;
	
	private String ch;
	
	private String yfbz;
	
	private Double yfjs;
	
	private Double tc;
	
	private Date qdfhtime;
	
	private String rowguid;

	public Integer getDetailid() {
		return detailid;
	}

	public void setDetailid(Integer detailid) {
		this.detailid = detailid;
	}

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

	public Date getQdfhtime() {
		return qdfhtime;
	}

	public void setQdfhtime(Date qdfhtime) {
		this.qdfhtime = qdfhtime;
	}

	public String getRowguid() {
		return rowguid;
	}

	public void setRowguid(String rowguid) {
		this.rowguid = rowguid;
	}
}
