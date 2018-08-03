package com.ycgwl.kylin.transport.dto;

import java.io.Serializable;

public class FreightRecordDetailDto implements Serializable {

	private static final long serialVersionUID = 4993219267643428101L;
	
	public int id;
	
	/**
	 * 运单 号
	 */
	public String wayBillNum;
	
	/**
	 * 细则号
	 */
	public int xzh;
	
	/**
	 * 货物名称
	 */
	public String goodsName;
	
	/**
	 * 起票件数
	 */
	public String ticketsNumber;
	
	/**
	 * 短少
	 */
	public String ds;
	
	/**
	 * 部件破损
	 */
	public String bjps;
	
	/**
	 * 湿损
	 */
	public String ss;
	
	/**
	 * 污损
	 */
	public String ws;
	
	/**
	 * 预估损失
	 */
	public String estimatedLoss;
	
	/**
	 * 异常货物状态/配载描述
	 */
	public String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWayBillNum() {
		return wayBillNum;
	}

	public void setWayBillNum(String wayBillNum) {
		this.wayBillNum = wayBillNum;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getTicketsNumber() {
		return ticketsNumber;
	}

	public void setTicketsNumber(String ticketsNumber) {
		this.ticketsNumber = ticketsNumber;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getBjps() {
		return bjps;
	}

	public void setBjps(String bjps) {
		this.bjps = bjps;
	}

	public String getSs() {
		return ss;
	}

	public void setSs(String ss) {
		this.ss = ss;
	}

	public String getWs() {
		return ws;
	}

	public void setWs(String ws) {
		this.ws = ws;
	}

	public String getEstimatedLoss() {
		return estimatedLoss;
	}

	public void setEstimatedLoss(String estimatedLoss) {
		this.estimatedLoss = estimatedLoss;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getXzh() {
		return xzh;
	}

	public void setXzh(int xzh) {
		this.xzh = xzh;
	}
	
	

}
