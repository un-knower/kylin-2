package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;
import java.util.Date;

public class DispatchCarPickGoodsDetailThree implements Serializable{
	/**
	 * 公司编码
	 */
	private String gsid;
	
	/**
	 * 派车单号
	 */
	private Integer id;
	
	/**
	 * 车号
	 */
	private String ch;
	
	/**
	 * 出发时间
	 */
	private Date cftime;
	
	/**
	 * 细则号
	 */
	private Integer detailid;
	
	/**
	 * 路码表
	 */
	private Integer cflmbs;
	
	/**
	 * 到达时间
	 */
	private Date ddtime;
	
	/**
	 * 路码表
	 */
	private Integer ddlmbs;
	
	/**
	 * 路桥费（途中发生费用）
	 */
	private Double tzlqf;
	
	/**
	 * 发生罚款（途中发生费用）
	 */
	private Double tzfk;
	
	/**
	 * 维修费（途中发生费用）
	 */
	private Double tzwxf;
	
	/**
	 * 路码表（途中加油）
	 */
	private Integer tzlmbs;
	
	/**
	 * 升（途中加油）
	 */
	private Float tzjyss;
	
	/**
	 * 金额（途中加油）
	 */
	private Double tzjyje;
	
	/**
	 * 司机
	 */
	private String sj;
	
	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
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

	public Integer getDetailid() {
		return detailid;
	}

	public void setDetailid(Integer detailid) {
		this.detailid = detailid;
	}

	public Integer getCflmbs() {
		return cflmbs;
	}

	public void setCflmbs(Integer cflmbs) {
		this.cflmbs = cflmbs;
	}

	public Date getDdtime() {
		return ddtime;
	}

	public void setDdtime(Date ddtime) {
		this.ddtime = ddtime;
	}

	public Integer getDdlmbs() {
		return ddlmbs;
	}

	public void setDdlmbs(Integer ddlmbs) {
		this.ddlmbs = ddlmbs;
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

	public Integer getTzlmbs() {
		return tzlmbs;
	}

	public void setTzlmbs(Integer tzlmbs) {
		this.tzlmbs = tzlmbs;
	}

	public Float getTzjyss() {
		return tzjyss;
	}

	public void setTzjyss(Float tzjyss) {
		this.tzjyss = tzjyss;
	}

	public Double getTzjyje() {
		return tzjyje;
	}

	public void setTzjyje(Double tzjyje) {
		this.tzjyje = tzjyje;
	}
}
