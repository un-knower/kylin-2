package com.ycgwl.kylin.transport.dto;

import java.io.Serializable;

public class DaoZhanDto implements Serializable {

	private static final long serialVersionUID = -7199910850404939041L;
	
	public String daoZhan;
	
	public String company;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDaoZhan() {
		return daoZhan;
	}

	public void setDaoZhan(String daoZhan) {
		this.daoZhan = daoZhan;
	}
	

}
