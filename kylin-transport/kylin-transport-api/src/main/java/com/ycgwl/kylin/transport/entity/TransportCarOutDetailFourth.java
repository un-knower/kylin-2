package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 送货派车单明细四
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
 */
public class TransportCarOutDetailFourth extends BaseEntity{

	private static final long serialVersionUID = 7748204235505090987L;
	
	private Integer detailid;
	
	private String gsid;
	
	private Integer id;

	private String ch;
	
	private Date cftime;
	
	private Integer cflmb;
	
	private Date ddtime;
	
	private Integer ddlmb;
	
	private Double tzlqf;
	
	private Double tzfk;
	
	private Double tzwxf;
	
	private Integer jylmb;
	
	private Float jyss;
	
	private Double jyje;
	
	private String sj;
	
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

	public String getCh() {
		return ch;
	}

	public void setCh(String ch) {
		this.ch = ch;
	}

	public Date getCftime() {
		return cftime;
	}

	public void setCftime(Date cftime) {
		this.cftime = cftime;
	}

	public Integer getCflmb() {
		return cflmb;
	}

	public void setCflmb(Integer cflmb) {
		this.cflmb = cflmb;
	}

	public Date getDdtime() {
		return ddtime;
	}

	public void setDdtime(Date ddtime) {
		this.ddtime = ddtime;
	}

	public Integer getDdlmb() {
		return ddlmb;
	}

	public void setDdlmb(Integer ddlmb) {
		this.ddlmb = ddlmb;
	}

	public Double getTzlqf() {
		return tzlqf;
	}

	public void setTzlqf(Double tzlqf) {
		this.tzlqf = tzlqf;
	}

	public Double getTzfk() {
		return tzfk;
	}

	public void setTzfk(Double tzfk) {
		this.tzfk = tzfk;
	}

	public Double getTzwxf() {
		return tzwxf;
	}

	public void setTzwxf(Double tzwxf) {
		this.tzwxf = tzwxf;
	}

	public Integer getJylmb() {
		return jylmb;
	}

	public void setJylmb(Integer jylmb) {
		this.jylmb = jylmb;
	}

	public Float getJyss() {
		return jyss;
	}

	public void setJyss(Float jyss) {
		this.jyss = jyss;
	}

	public Double getJyje() {
		return jyje;
	}

	public void setJyje(Double jyje) {
		this.jyje = jyje;
	}

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getRowguid() {
		return rowguid;
	}

	public void setRowguid(String rowguid) {
		this.rowguid = rowguid;
	}

}
