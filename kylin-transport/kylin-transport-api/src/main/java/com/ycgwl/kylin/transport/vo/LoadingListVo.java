package com.ycgwl.kylin.transport.vo;

import java.io.Serializable;

public class LoadingListVo implements Serializable {

	private static final long serialVersionUID = -8384548087107774023L;
	
	public String fazhan;
	public String daozhan;
	public String wxName;
	public String zhchrq;
	public String chxh;
	public String iType;
	
	public String getFazhan() {
		return fazhan;
	}
	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}
	public String getDaozhan() {
		return daozhan;
	}
	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}
	public String getWxName() {
		return wxName;
	}
	public void setWxName(String wxName) {
		this.wxName = wxName;
	}
	public String getZhchrq() {
		return zhchrq;
	}
	public void setZhchrq(String zhchrq) {
		this.zhchrq = zhchrq;
	}
	public String getChxh() {
		return chxh;
	}
	public void setChxh(String chxh) {
		this.chxh = chxh;
	}
	public String getiType() {
		return iType;
	}
	public void setiType(String iType) {
		this.iType = iType;
	}
	

}
