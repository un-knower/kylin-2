package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

public class TransportSignRecordEntity extends BaseEntity{

	private static final long serialVersionUID = -6336209717478825672L;

	private TransportSignRecord transportSignRecord;
	
	private String account;//账号
	
	private String userName;//名称
	
	private String password;//密码
	
	private Date createTime;//创建时间
	
	private Boolean enable = true;	//状态
	
	private String company;//公司名称
	
	private String subCompany;//分公司名称

	public TransportSignRecord getTransportSignRecord() {
		return transportSignRecord;
	}

	public void setTransportSignRecord(TransportSignRecord transportSignRecord) {
		this.transportSignRecord = transportSignRecord;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSubCompany() {
		return subCompany;
	}

	public void setSubCompany(String subCompany) {
		this.subCompany = subCompany;
	}
	
}
