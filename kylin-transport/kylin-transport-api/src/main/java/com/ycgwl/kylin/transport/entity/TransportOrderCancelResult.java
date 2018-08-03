package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;

/**
 * TransportOrderCancel
 * 
 * @Description: 运单作废表
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @date 2017年11月29日
 */
public class TransportOrderCancelResult implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 废除状态
	 */
	public static final int CANCEL_STATUS_YES = 1;
	
	/**
	 * 取消废除状态（正常）
	 */
	public static final int CANCEL_STATUS_NO = 0;
	
	public static final int CANCEL_STATUS_OTHER = 2;
	
	/**
	 * 运单编号
	 */
	private String transportCode;
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 公司编码
	 */
	private String companyCode;
	
	/**
	 * 始发站
	 */
	private String origStation;
	
	/**
	 * 备注
	 */
	private String description;
	
	/**
	 * 作废状态
	 */
	private int cancelStatus;
	
	/**
	 * 工号
	 */
	private String xgrgonghao;
	
	/**
	 * 机器
	 */
	private String xgrjiqi;
	
	/*
	 * 工号
	 */
	private String czygh;
	
	/**
	 * 作废按钮是否可以点击
	 * @return
	 */
	private boolean cancelButton;
	
	/**
	 * 取消按钮是否可以点击
	 * @return
	 */
	private boolean resumeButton;
	
	/**
	 * 备注是否可以修改
	 * @return
	 */
	private boolean canEditDesc;
	
	public String getCzygh() {
		return czygh;
	}

	public void setCzygh(String czygh) {
		this.czygh = czygh;
	}

	public String getXgrjiqi() {
		return xgrjiqi;
	}

	public void setXgrjiqi(String xgrjiqi) {
		this.xgrjiqi = xgrjiqi;
	}

	public String getXgrgonghao() {
		return xgrgonghao;
	}

	public void setXgrgonghao(String xgrgonghao) {
		this.xgrgonghao = xgrgonghao;
	}

	public boolean isCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(boolean cancelButton) {
		this.cancelButton = cancelButton;
	}

	public boolean isResumeButton() {
		return resumeButton;
	}

	public void setResumeButton(boolean resumeButton) {
		this.resumeButton = resumeButton;
	}

	public boolean isCanEditDesc() {
		return canEditDesc;
	}

	public void setCanEditDesc(boolean canEditDesc) {
		this.canEditDesc = canEditDesc;
	}

	public int getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(int cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public String getTransportCode() {
		return transportCode;
	}

	public void setTransportCode(String transportCode) {
		this.transportCode = transportCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getOrigStation() {
		return origStation;
	}

	public void setOrigStation(String origStation) {
		this.origStation = origStation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
