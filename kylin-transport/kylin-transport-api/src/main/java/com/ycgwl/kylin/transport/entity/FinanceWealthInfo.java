package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.transport.entity.adjunct.PrintData;

import java.io.Serializable;
import java.util.List;

/**
 * FinanceWealthInfo
 * 
 * @Description: 财凭总的金额合计明细内容
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @date 2017年12月08日
 */
public class FinanceWealthInfo implements Serializable{
	
	private static final long serialVersionUID = -3327407452020044757L;

	/**
	 * 财凭编号
	 */
	private Long wealthCode;
	
	/**
	 * 红票财凭编号
	 */
	private String redWealthCode;
	
	/**
	 * 运单号
	 */
	private String transportCode;
	
	/**
	 * 录入时间
	 */
	private String createTime;
	
	/**
	 * 办单费
	 */
	private Double handleBillFee;
	
	/**
	 * 上门取货费
	 */
	private Double pickupFee;
	
	/**
	 * 上门送货费
	 */
	private Double deliveryFee;
	
	/**
	 * 代收货款（代收款）
	 */
	private Double agencyCharge;
	
	/**
	 * 其他费用
	 */
	private Double otherFee;
	
	/**
	 * 合计费用
	 */
	private Double totalFee;
	
	/**
	 * 能否冲红
	 */
	private boolean canModify;
	
	/**
	 * 收据号
	 */
	private int receiptNo;
	
	/**
	 * 冲红状态
	 */
	private int offsetStatus;
	
	/**
	 * 保险费
	 */
	private Double insuranceExpense;
	
	/**
	 * 叉车费
	 */
	private Double forkliftFee;
	
	/**
	 * 装卸费（重）
	 */
	private Double heavyLoadingFee;
	
	/**
	 * 装卸费（轻）
	 */
	private Double lightLoadingFee;
	
	/**
	 * 运杂费合计
	 */
	private Double miscellaneousFee;
	
	/**
	 * 运费合计
	 */
	private Double conveyFee;
	
	/**
	 * 件数 
	 */
	private Integer jianShu;
	
	/**
	 * 重量  
	 */
	private Double zhongLiang;
	
	/**
	 * 体积 
	 */	
	private Double tiJi;
	
	public List<PrintData> getPrintData() {
		return printData;
	}

	public void setPrintData(List<PrintData> printData) {
		this.printData = printData;
	}

	private List<PrintData> printData;
	
	
	public Integer getJianShu() {
		return jianShu != null?this.jianShu:0;
	}

	public void setJianShu(Integer jianShu) {
		this.jianShu = jianShu;
	}

	public Double getZhongLiang() {
		return zhongLiang != null?this.zhongLiang:0.0;
	}

	public void setZhongLiang(Double zhongLiang) {
		this.zhongLiang = zhongLiang;
	}

	public Double getTiJi() {
		return tiJi != null?this.tiJi:0.0;
	}

	public void setTiJi(Double tiJi) {
		this.tiJi = tiJi;
	}

	/**
	 * 打印信息
	 */
	private FinanceReceiveMoneyPrint print;
	
	
	public FinanceReceiveMoneyPrint getPrint() {
		return print;
	}

	public void setPrint(FinanceReceiveMoneyPrint print) {
		this.print = print;
	}

	public Double getInsuranceExpense() {
		return insuranceExpense != null?this.insuranceExpense:0.0;
	}

	public void setInsuranceExpense(Double insuranceExpense) {
		this.insuranceExpense = insuranceExpense;
	}

	public Double getForkliftFee() {
		return forkliftFee != null?this.forkliftFee:0.0;
	}

	public void setForkliftFee(Double forkliftFee) {
		this.forkliftFee = forkliftFee;
	}

	public Double getHeavyLoadingFee() {
		return heavyLoadingFee != null?this.heavyLoadingFee:0.0;
	}

	public void setHeavyLoadingFee(Double heavyLoadingFee) {
		this.heavyLoadingFee = heavyLoadingFee;
	}

	public Double getLightLoadingFee() {
		return lightLoadingFee != null?this.lightLoadingFee:0.0;
	}

	public void setLightLoadingFee(Double lightLoadingFee) {
		this.lightLoadingFee = lightLoadingFee;
	}

	public Double getMiscellaneousFee() {
		return miscellaneousFee != null?this.miscellaneousFee:0.0;
	}

	public void setMiscellaneousFee(Double miscellaneousFee) {
		this.miscellaneousFee = miscellaneousFee;
	}

	public Double getConveyFee() {
		return conveyFee != null?this.conveyFee:0.0;
	}

	public void setConveyFee(Double conveyFee) {
		this.conveyFee = conveyFee;
	}

	public boolean isCanModify() {
		return canModify;
	}

	public void setCanModify(boolean canModify) {
		this.canModify = canModify;
	}

	public int getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(int receiptNo) {
		this.receiptNo = receiptNo;
	}

	public int getOffsetStatus() {
		return offsetStatus;
	}

	public void setOffsetStatus(int offsetStatus) {
		this.offsetStatus = offsetStatus;
	}

	public Long getWealthCode() {
		return wealthCode;
	}

	public void setWealthCode(Long wealthCode) {
		this.wealthCode = wealthCode;
	}

	public String getRedWealthCode() {
		return redWealthCode;
	}

	public void setRedWealthCode(String redWealthCode) {
		this.redWealthCode = redWealthCode;
	}

	public String getTransportCode() {
		return transportCode;
	}

	public void setTransportCode(String transportCode) {
		this.transportCode = transportCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Double getHandleBillFee() {
		return handleBillFee != null?this.handleBillFee:0.0;
	}

	public void setHandleBillFee(Double handleBillFee) {
		this.handleBillFee = handleBillFee;
	}

	public Double getPickupFee() {
		return pickupFee != null?this.pickupFee:0.0;
	}

	public void setPickupFee(Double pickupFee) {
		this.pickupFee = pickupFee;
	}

	public Double getDeliveryFee() {
		return deliveryFee != null?this.deliveryFee:0.0;
	}

	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Double getAgencyCharge() {
		return agencyCharge  != null?this.agencyCharge:0.0;
	}

	public void setAgencyCharge(Double agencyCharge) {
		this.agencyCharge = agencyCharge;
	}

	public Double getOtherFee() {
		return otherFee  != null?this.otherFee:0.0;
	}

	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}

	public Double getTotalFee() {
		return totalFee != null?this.totalFee:0.0;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
}
