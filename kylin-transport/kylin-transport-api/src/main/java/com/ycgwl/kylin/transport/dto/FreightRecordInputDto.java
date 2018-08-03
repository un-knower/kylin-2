package com.ycgwl.kylin.transport.dto;

import java.io.Serializable;
import java.util.LinkedList;

public class FreightRecordInputDto implements Serializable {
	
	private static final long serialVersionUID = 7748607026388977022L;
	
	public int id;
	
	/** 公司 */
	public String company;
	
	/** 当前登录人账号 */
	public String currAccount;
	
	/** 当前登录人姓名 */
	public String currUserName;
	
	public String abnormalType;
	
	public String happenedAdress;
	
	public String arrivalTime;
	
	public String happenedTime;
	
	public String responsibility;
	
	public String isHandle;
	
	public String estimatedLoss;
	
	// 车牌号
	public String train;
	
	/**
	 * 到站单位负责人意见
	 */
	public String dzdwOpinion;
	
	/**
	 * 到站单位负责人名称
	 */
	public String dzdwName;
	
	/**
	 * 到站单位负责人处理时间
	 */
	public String dzdwTime;
	
	/**
	 * 发站单位负责人意见
	 */
	public String fzdwOpinion;
	
	/**
	 * 发站单位负责人名称
	 */
	public String fzdwName;
	
	/**
	 * 发站单位负责人处理时间
	 */
	public String fzdwTime;
	
	/**
	 * 发站
	 */
	public String fazhan;
	
	/**
	 * 到站
	 */
	public String daozhan;
	
	/**
	 * 通知公司
	 */
	public String noticeCompany;
	
	/**
	 * 通知公司意见
	 */
	public String noticeCompanyOpinion;
	
	/**
	 * 装车日期
	 */
	public String fchrq;
	
	/**
	 * 供应商名称
	 */
	public String wxName;
	
	public LinkedList<FreightRecordDetailDto> freightRecordDetailDto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCurrAccount() {
		return currAccount;
	}

	public void setCurrAccount(String currAccount) {
		this.currAccount = currAccount;
	}

	public String getCurrUserName() {
		return currUserName;
	}

	public void setCurrUserName(String currUserName) {
		this.currUserName = currUserName;
	}

	public String getAbnormalType() {
		return abnormalType;
	}

	public void setAbnormalType(String abnormalType) {
		this.abnormalType = abnormalType;
	}

	public String getHappenedAdress() {
		return happenedAdress;
	}

	public void setHappenedAdress(String happenedAdress) {
		this.happenedAdress = happenedAdress;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getHappenedTime() {
		return happenedTime;
	}

	public void setHappenedTime(String happenedTime) {
		this.happenedTime = happenedTime;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}

	public String getEstimatedLoss() {
		return estimatedLoss;
	}

	public void setEstimatedLoss(String estimatedLoss) {
		this.estimatedLoss = estimatedLoss;
	}

	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
		this.train = train;
	}

	public String getDzdwOpinion() {
		return dzdwOpinion;
	}

	public void setDzdwOpinion(String dzdwOpinion) {
		this.dzdwOpinion = dzdwOpinion;
	}

	public String getDzdwName() {
		return dzdwName;
	}

	public void setDzdwName(String dzdwName) {
		this.dzdwName = dzdwName;
	}

	public String getDzdwTime() {
		return dzdwTime;
	}

	public void setDzdwTime(String dzdwTime) {
		this.dzdwTime = dzdwTime;
	}

	public String getFzdwOpinion() {
		return fzdwOpinion;
	}

	public void setFzdwOpinion(String fzdwOpinion) {
		this.fzdwOpinion = fzdwOpinion;
	}

	public String getFzdwName() {
		return fzdwName;
	}

	public void setFzdwName(String fzdwName) {
		this.fzdwName = fzdwName;
	}

	public String getFzdwTime() {
		return fzdwTime;
	}

	public void setFzdwTime(String fzdwTime) {
		this.fzdwTime = fzdwTime;
	}

	public String getFazhan() {
		return fazhan;
	}

	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}

	public String getDaozhan() {
		return daozhan;
	}

	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}

	public LinkedList<FreightRecordDetailDto> getFreightRecordDetailDto() {
		return freightRecordDetailDto;
	}

	public void setFreightRecordDetailDto(LinkedList<FreightRecordDetailDto> freightRecordDetailDto) {
		this.freightRecordDetailDto = freightRecordDetailDto;
	}

	public String getNoticeCompany() {
		return noticeCompany;
	}

	public void setNoticeCompany(String noticeCompany) {
		this.noticeCompany = noticeCompany;
	}

	public String getNoticeCompanyOpinion() {
		return noticeCompanyOpinion;
	}

	public void setNoticeCompanyOpinion(String noticeCompanyOpinion) {
		this.noticeCompanyOpinion = noticeCompanyOpinion;
	}

	public String getFchrq() {
		return fchrq;
	}

	public void setFchrq(String fchrq) {
		this.fchrq = fchrq;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}
	
	

}
