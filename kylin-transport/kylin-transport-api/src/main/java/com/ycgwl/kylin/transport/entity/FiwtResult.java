package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

public class FiwtResult extends BaseEntity{

	private static final long serialVersionUID = -5971846591650873727L;

	private String xianlu;
	
	private Integer cwpzhbh;
	
	private String nf;

	public String getXianlu() {
		return xianlu;
	}

	public void setXianlu(String xianlu) {
		this.xianlu = xianlu;
	}

	public Integer getCwpzhbh() {
		return cwpzhbh;
	}

	public void setCwpzhbh(Integer cwpzhbh) {
		this.cwpzhbh = cwpzhbh;
	}

	public String getNf() {
		return nf;
	}

	public void setNf(String nf) {
		this.nf = nf;
	}
	
}
