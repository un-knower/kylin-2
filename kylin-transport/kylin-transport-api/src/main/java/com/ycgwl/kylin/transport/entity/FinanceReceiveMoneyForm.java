package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;

/**
 * @Description: 收钱界面表单参数条件
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @date 2017年10月25日
 */
public class FinanceReceiveMoneyForm implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * @Description:年份
	 */
	private String year;
	
	/**
	 * @Description:财凭号（受理单编号）
	 */
	private Long wealthNo;
	
	/**
	 * @Description:公司编码
	 */
	private String companyCode;
	
	/**
	 * @Description:客户余额
	 */
	private Double customerBalance;
	
	/**
	 * @Description:公司名称
	 */
	private String companyName;
	
	/**
	 * @Description:登录帐号的公司名称
	 */
	private String loginCompanyName;
	
	/**
	 * @Description:款未付
	 */
	private Double yshzhk;
	
	/**
	 * @Description:现金收入
	 */
	private Double xianjin;
	
	/**
	 * @Description:货到付款
	 */
	private Double hdfk;
	
	/**
	 * @Description:银行收入
	 */
	private Double yhshr;
	
	/**
	 * @Description:月结
	 */
	private Double yshk;
	
	/**
	 * @Description:代收款
	 */
	private Double dsk;
	
	/**
	 * @Description:出纳
	 */
	private String chuna;
	
	/**
	 * @Description:运单编号
	 */
	private String transportCode;
	
	/**
	 * @Description:工号
	 */
	private String grid;
	
	private String fazhan;
	
	private String userName;
	
	private String description;
	
	private Boolean isNewDoc = false;
	
	/**
	 * 冲红状态
	 */
	private int offsetStatus;
	
	/**
	 * 收据号
	 */
	private int receiptNo;

	public Boolean getIsNewDoc() {
		return isNewDoc;
	}

	public void setIsNewDoc(Boolean isNewDoc) {
		this.isNewDoc = isNewDoc;
	}

	public int getOffsetStatus() {
		return offsetStatus;
	}

	public void setOffsetStatus(int offsetStatus) {
		this.offsetStatus = offsetStatus;
	}

	public int getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(int receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFazhan() {
		return fazhan;
	}

	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getTransportCode() {
		return transportCode;
	}

	public void setTransportCode(String transportCode) {
		this.transportCode = transportCode;
	}

	public String getChuna() {
		return chuna;
	}

	public void setChuna(String chuna) {
		this.chuna = chuna;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getYshzhk() {
		return yshzhk;
	}

	public void setYshzhk(Double yshzhk) {
		this.yshzhk = yshzhk;
	}

	public Double getXianjin() {
		return xianjin;
	}

	public void setXianjin(Double xianjin) {
		this.xianjin = xianjin;
	}

	public Double getHdfk() {
		return hdfk;
	}

	public void setHdfk(Double hdfk) {
		this.hdfk = hdfk;
	}

	public Double getYhshr() {
		return yhshr;
	}

	public void setYhshr(Double yhshr) {
		this.yhshr = yhshr;
	}

	public Double getYshk() {
		return yshk;
	}

	public void setYshk(Double yshk) {
		this.yshk = yshk;
	}

	public Double getDsk() {
		return dsk;
	}

	public void setDsk(Double dsk) {
		this.dsk = dsk;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Long getWealthNo() {
		return wealthNo;
	}

	public void setWealthNo(Long wealthNo) {
		this.wealthNo = wealthNo;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Double getCustomerBalance() {
		return customerBalance;
	}

	public void setCustomerBalance(Double customerBalance) {
		this.customerBalance = customerBalance;
	}

	public String getLoginCompanyName() {
		return loginCompanyName;
	}

	public void setLoginCompanyName(String loginCompanyName) {
		this.loginCompanyName = loginCompanyName;
	}
}
