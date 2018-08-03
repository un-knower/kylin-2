package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.List;

/**
 * 送货签收单返回信息
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
 */
public class DeliverySignResult extends BaseEntity{

	private static final long serialVersionUID = -318761825464879001L;

	private TransportCarOut transportCarOut;
	
	private List<TransportCarOutGoodsDetail> transportCarOutGoodsDetailList;
	
	private List<TransportCarOutVehicleDetail> transportCarOutVehicleDetailList;
	
	/** 是否做过派车签收单（0.未生成 1.已生成）*/
	private Integer isDelivery;
	
	/** 是否做过派车签收核算（0.未生成 1.已生成）*/
	private Integer isAccounting;

	public TransportCarOut getTransportCarOut() {
		return transportCarOut;
	}

	public void setTransportCarOut(TransportCarOut transportCarOut) {
		this.transportCarOut = transportCarOut;
	}

	public List<TransportCarOutGoodsDetail> getTransportCarOutGoodsDetailList() {
		return transportCarOutGoodsDetailList;
	}

	public void setTransportCarOutGoodsDetailList(List<TransportCarOutGoodsDetail> transportCarOutGoodsDetailList) {
		this.transportCarOutGoodsDetailList = transportCarOutGoodsDetailList;
	}

	public List<TransportCarOutVehicleDetail> getTransportCarOutVehicleDetailList() {
		return transportCarOutVehicleDetailList;
	}

	public void setTransportCarOutVehicleDetailList(List<TransportCarOutVehicleDetail> transportCarOutVehicleDetailList) {
		this.transportCarOutVehicleDetailList = transportCarOutVehicleDetailList;
	}

	public Integer getIsDelivery() {
		return isDelivery;
	}

	public void setIsDelivery(Integer isDelivery) {
		this.isDelivery = isDelivery;
	}

	public Integer getIsAccounting() {
		return isAccounting;
	}

	public void setIsAccounting(Integer isAccounting) {
		this.isAccounting = isAccounting;
	}
	
}
