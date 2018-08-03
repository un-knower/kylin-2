package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 财务收据返回实体
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月11日
 */
public class FinancialReceiptsResult extends BaseEntity{

	private static final long serialVersionUID = -2973351071654070446L;
	
	private FinancialReceiptsMaster financialReceiptsMaster;
	
	private List<FinancialReceiptsDetail> financialReceiptsDetailList;
	
	/** 是否生成过分理收据（0.未生成 1.已生成）*/
	private Integer isGenerate;
	
	/** 是否交钱（0.未交钱 1.已交钱）*/
	private Integer isPayMoney;
	
	private String ydbhid;
	
	private String account;//账号
	
	private String userName;//名称
	
	private String password;//密码
	
	private Date createTime;//创建时间
	
	private Boolean enable = true;	//状态
	
	private String company;//公司名称
	
	private String companyCode;//公司编码
	
	private String subCompany;//分公司名称

	public List<FinancialReceiptsDetail> getFinancialReceiptsDetailList() {
		return financialReceiptsDetailList;
	}

	public void setFinancialReceiptsDetailList(List<FinancialReceiptsDetail> financialReceiptsDetailList) {
		this.financialReceiptsDetailList = financialReceiptsDetailList;
	}

	public Integer getIsGenerate() {
		return isGenerate;
	}

	public void setIsGenerate(Integer isGenerate) {
		this.isGenerate = isGenerate;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getSubCompany() {
		return subCompany;
	}

	public void setSubCompany(String subCompany) {
		this.subCompany = subCompany;
	}

	public Integer getIsPayMoney() {
		return isPayMoney;
	}

	public void setIsPayMoney(Integer isPayMoney) {
		this.isPayMoney = isPayMoney;
	}

	public FinancialReceiptsMaster getFinancialReceiptsMaster() {
		return financialReceiptsMaster;
	}

	public void setFinancialReceiptsMaster(FinancialReceiptsMaster financialReceiptsMaster) {
		this.financialReceiptsMaster = financialReceiptsMaster;
	}
	
}
