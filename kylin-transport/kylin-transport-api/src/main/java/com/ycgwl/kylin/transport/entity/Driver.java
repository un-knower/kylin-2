package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 司机信息
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
 */
public class Driver extends BaseEntity{

	private static final long serialVersionUID = -5144545861236103580L;

	private String xm;
	
	private String bm;
	
	private String grid;
	
	private Integer zt;
	
	private String gs;

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getGs() {
		return gs;
	}

	public void setGs(String gs) {
		this.gs = gs;
	}
}
