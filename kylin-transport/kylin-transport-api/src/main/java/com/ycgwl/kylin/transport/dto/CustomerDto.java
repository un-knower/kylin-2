package com.ycgwl.kylin.transport.dto;

import java.io.Serializable;

public class CustomerDto implements Serializable {

	private static final long serialVersionUID = 5305921034595167028L;
	
	/**
	 * 客户编码
	 */
	public String khbm;
	
	/**
	 * 客户名称
	 */
	public String khmc;
	
	public String company;

	public String getKhbm() {
		return khbm;
	}

	public void setKhbm(String khbm) {
		this.khbm = khbm;
	}

	public String getKhmc() {
		return khmc;
	}

	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	

}
