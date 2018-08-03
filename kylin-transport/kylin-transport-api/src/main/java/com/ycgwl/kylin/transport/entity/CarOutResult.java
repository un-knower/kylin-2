package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

public class CarOutResult extends BaseEntity{

	private static final long serialVersionUID = -8910118209405779905L;

	private Integer id;
	
	private String pcshd;
	
	private Integer pcshlc;
	
	private Integer min;
	
	private Integer max;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPcshd() {
		return pcshd;
	}

	public void setPcshd(String pcshd) {
		this.pcshd = pcshd;
	}

	public Integer getPcshlc() {
		return pcshlc;
	}

	public void setPcshlc(Integer pcshlc) {
		this.pcshlc = pcshlc;
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
	
}
