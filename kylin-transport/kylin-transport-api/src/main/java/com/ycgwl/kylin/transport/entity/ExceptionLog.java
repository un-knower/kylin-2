package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 异常日志实体
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
 */
public class ExceptionLog extends BaseEntity{

	private static final long serialVersionUID = -6319851655758319490L;

	private Integer id;
	
	/** 操作人名称 */
	private String operatorName;
	
	/** 操作人账号 */
	private String operatorAccount;
	
	/** 操作人公司 */
	private String operatorCompany;
	
	/** ip地址 */
	private String ipAddress;
	
	/** 运单号 */
	private String ydbhid;
	
	/** 财凭号 */
	private BigDecimal cwpzhbh;
	
	/** 操作模块 */
	private String operatingMenu;
	
	/** 操作内容 */
	private String operatingContent;
	
	/** 操作时间 */
	private Date operatingTime;
	
	/** 创建人名称 */
	private String createName;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 更新人名称 */
	private String updateName;
	
	/** 更新时间 */
	private Date updateTime;

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

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
