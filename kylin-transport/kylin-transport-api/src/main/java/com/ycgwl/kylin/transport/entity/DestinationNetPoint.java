package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;

public class DestinationNetPoint implements Serializable{
	private String shhd;
	private String name;
	private String companyCode;
	public String getShhd() {
		return shhd;
	}
	public void setShhd(String shhd) {
		this.shhd = shhd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
