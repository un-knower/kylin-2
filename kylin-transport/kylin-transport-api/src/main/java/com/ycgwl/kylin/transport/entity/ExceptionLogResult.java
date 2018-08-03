package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * 异常日志信息查询返回实体
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月8日
 */
public class ExceptionLogResult extends BaseEntity{

	private static final long serialVersionUID = 831600963971385280L;

	private Integer id;
	
	private String operatorName;
	
	private String operatorAccount;
	
	private String operatorCompany;
	
	private String ipAddress;
	
	private String ydbhid;
	
	private BigDecimal cwpzhbh;
	
	private String operatingMenu;
	
	private String operatingContent;
	
	private String operatingTime;
	
	private String createName;
	
	private String createTime;
	
	private String updateName;
	
	private String updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorAccount() {
		return operatorAccount;
	}

	public void setOperatorAccount(String operatorAccount) {
		this.operatorAccount = operatorAccount;
	}

	public String getOperatorCompany() {
		return operatorCompany;
	}

	public void setOperatorCompany(String operatorCompany) {
		this.operatorCompany = operatorCompany;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public BigDecimal getCwpzhbh() {
		return cwpzhbh;
	}

	public void setCwpzhbh(BigDecimal cwpzhbh) {
		this.cwpzhbh = cwpzhbh;
	}

	public String getOperatingMenu() {
		return operatingMenu;
	}

	public void setOperatingMenu(String operatingMenu) {
		this.operatingMenu = operatingMenu;
	}

	public String getOperatingContent() {
		return operatingContent;
	}

	public void setOperatingContent(String operatingContent) {
		this.operatingContent = operatingContent;
	}

	public String getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(String operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
