package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 送货派车单车辆明细
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月27日
 */
public class TransportCarOutVehicleDetail extends BaseEntity{

	private static final long serialVersionUID = -7513236416067953882L;
	
	private Integer detailid;
	
	private String gsid;
	
	private Integer id;
	
	private String clxz;
	
	private String ch;
	
	private String cx;
	
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

	public String getRowguid() {
		return rowguid;
	}

	public void setRowguid(String rowguid) {
		this.rowguid = rowguid;
	}

}
