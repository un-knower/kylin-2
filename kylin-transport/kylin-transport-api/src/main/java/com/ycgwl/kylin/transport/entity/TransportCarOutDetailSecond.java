package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 送货派车单明细二
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
 */
public class TransportCarOutDetailSecond extends BaseEntity{

	private static final long serialVersionUID = 8977803780978472505L;

	private Integer detailid;
	
	private String gsid;
	
	private Integer id;
	
	private String ydhm;
	
	private String hwmc;
	
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

	public String getYdhm() {
		return ydhm;
	}

	public void setYdhm(String ydhm) {
		this.ydhm = ydhm;
	}

	public String getHwmc() {
		return hwmc;
	}

	public void setHwmc(String hwmc) {
		this.hwmc = hwmc;
	}

	public String getRowguid() {
		return rowguid;
	}

	public void setRowguid(String rowguid) {
		this.rowguid = rowguid;
	}
	
}
