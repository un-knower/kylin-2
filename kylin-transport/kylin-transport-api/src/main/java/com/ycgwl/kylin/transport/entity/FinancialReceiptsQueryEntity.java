package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 生成财务收据参数实体
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月11日
 */
public class FinancialReceiptsQueryEntity extends BaseEntity{

	private static final long serialVersionUID = -5935046291126802906L;

	/** 运单编号 */
	private String ydbhid;
	
	/** 分理收据单号 */
	private String sjid;
	
	/** 是否到货 */
	private Integer ydzh;
	
	/** 运单细则号 */
	private Integer ydxzh;
	
	/** 货到付款 */
	private BigDecimal hdfk;
	
	/** 代收款 */
	private BigDecimal dsk;
	
	/** 装载序号 */
	private String xuhao;
	
	private String account;//账号
	
	private String userName;//名称
	
	private String password;//密码
	
	private Date createTime;//创建时间
	
	private Boolean enable = true;	//状态
	
	private String company;//公司名称
	
	private String companyCode;//公司编码
	
	private String subCompany;//分公司名称

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public Integer getYdzh() {
		return ydzh;
	}

	public void setYdzh(Integer ydzh) {
		this.ydzh = ydzh;
	}

	public Integer getYdxzh() {
		return ydxzh;
	}

	public void setYdxzh(Integer ydxzh) {
		this.ydxzh = ydxzh;
	}

	public BigDecimal getHdfk() {
		return hdfk;
	}

	public void setHdfk(BigDecimal hdfk) {
		this.hdfk = hdfk;
	}

	public BigDecimal getDsk() {
		return dsk;
	}

	public void setDsk(BigDecimal dsk) {
		this.dsk = dsk;
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

	
	public String getSjid() {
		return sjid;
	}

	public void setSjid(String sjid) {
		this.sjid = sjid;
	}

	public String getXuhao() {
		return xuhao;
	}

	public void setXuhao(String xuhao) {
		this.xuhao = xuhao;
	}
	
}
