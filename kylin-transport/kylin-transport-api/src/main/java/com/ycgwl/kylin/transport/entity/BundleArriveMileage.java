package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 到货装载里程
 */
public class BundleArriveMileage extends BaseEntity{
	
	private static final long serialVersionUID = -7792567427032582315L;

	private String mdd;
	
	private Integer shlc;
	
	private Integer min;
	
	private Integer max;
	
	private String lc;

	public String getMdd() {
		return mdd;
	}

	public void setMdd(String mdd) {
		this.mdd = mdd;
	}

	public Integer getShlc() {
		return shlc;
	}

	public void setShlc(Integer shlc) {
		this.shlc = shlc;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public String getLc() {
		return lc;
	}

	public void setLc(String lc) {
		this.lc = lc;
	}
}
