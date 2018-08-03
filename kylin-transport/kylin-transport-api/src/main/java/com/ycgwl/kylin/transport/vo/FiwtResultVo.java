package com.ycgwl.kylin.transport.vo;

import java.io.Serializable;

public class FiwtResultVo implements Serializable {

	private static final long serialVersionUID = 7884362620919641622L;
	
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
