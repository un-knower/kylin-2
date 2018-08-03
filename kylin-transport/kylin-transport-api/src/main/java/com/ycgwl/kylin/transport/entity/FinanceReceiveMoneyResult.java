package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;

public class FinanceReceiveMoneyResult implements Serializable{
	
	private static final long serialVersionUID = 1291924675141706533L;

	private FinanceReceiveMoney sessionMoney;
	
	/**
	 * 是否可以修改输入内容（6个款项输入）
	 */
	private boolean canModify = false;
	
	/**
	 * 不能修改原因
	 */
	private String canotModifyReason;
	
	/**
	 * 报错信息
	 */
	private String resultMsg;
	
	/**
	 * 报错编号
	 */
	private String resultCode;
	
	
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
	 * @Description:合计金额
	 */
	private Double totalMoney;
	
	/**
	 * @Description:小数位
	 */
	private int decimalPlace;
	
	/**
	 * @Description:冲红原因
	 */
	private String description;
	
	/**
	 * @Description:收据号
	 */
	private int receiptNo;
	
	/**
	 * @Description:冲红状态
	 */
	private int offsetWealthStatus;
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 导入人（登录人姓名）
	 */
	private String userName;
	
	/**
	 * 导入人工号
	 */
	private String grid;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public int getOffsetWealthStatus() {
		return offsetWealthStatus;
	}

	public void setOffsetWealthStatus(int offsetWealthStatus) {
		this.offsetWealthStatus = offsetWealthStatus;
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

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	private FinanceReceiveMoney money;
	
	public int getDecimalPlace() {
		return decimalPlace;
	}

	public void setDecimalPlace(int decimalPlace) {
		this.decimalPlace = decimalPlace;
	}

	public FinanceReceiveMoney getMoney() {
		return money;
	}

	public void setMoney(FinanceReceiveMoney money) {
		this.money = money;
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

	public boolean isCanModify() {
		return canModify;
	}

	public void setCanModify(boolean canModify) {
		this.canModify = canModify;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCanotModifyReason() {
		return canotModifyReason;
	}

	public void setCanotModifyReason(String canotModifyReason) {
		this.canotModifyReason = canotModifyReason;
	}

	public FinanceReceiveMoney getSessionMoney() {
		return sessionMoney;
	}

	public void setSessionMoney(FinanceReceiveMoney sessionMoney) {
		this.sessionMoney = sessionMoney;
	}
}
