package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.List;

/**
 * s
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
 */
public class DeliveryAccountingResult extends BaseEntity{

	private static final long serialVersionUID = 683429500597481507L;
	
	private TransportCarOut transportCarOut;
	
	private List<TransportCarOutDetailSecond> transportCarOutDetailSecondList;
	
	private List<TransportCarOutDetailFourth> transportCarOutDetailFourthList;
	
	private List<TransportCarOutDetailFifth> transportCarOutDetailFifthlList;
	
	/** 是否做过派车签收核算（0.未生成 1.已生成）*/
	private Integer isAccounting;

	public TransportCarOut getTransportCarOut() {
		return transportCarOut;
	}

	public void setTransportCarOut(TransportCarOut transportCarOut) {
		this.transportCarOut = transportCarOut;
	}

	public List<TransportCarOutDetailSecond> getTransportCarOutDetailSecondList() {
		return transportCarOutDetailSecondList;
	}

	public void setTransportCarOutDetailSecondList(List<TransportCarOutDetailSecond> transportCarOutDetailSecondList) {
		this.transportCarOutDetailSecondList = transportCarOutDetailSecondList;
	}

	public List<TransportCarOutDetailFourth> getTransportCarOutDetailFourthList() {
		return transportCarOutDetailFourthList;
	}

	public void setTransportCarOutDetailFourthList(List<TransportCarOutDetailFourth> transportCarOutDetailFourthList) {
		this.transportCarOutDetailFourthList = transportCarOutDetailFourthList;
	}

	public List<TransportCarOutDetailFifth> getTransportCarOutDetailFifthlList() {
		return transportCarOutDetailFifthlList;
	}

	public void setTransportCarOutDetailFifthlList(List<TransportCarOutDetailFifth> transportCarOutDetailFifthlList) {
		this.transportCarOutDetailFifthlList = transportCarOutDetailFifthlList;
	}

	public Integer getIsAccounting() {
		return isAccounting;
	}

	public void setIsAccounting(Integer isAccounting) {
		this.isAccounting = isAccounting;
	}
}
