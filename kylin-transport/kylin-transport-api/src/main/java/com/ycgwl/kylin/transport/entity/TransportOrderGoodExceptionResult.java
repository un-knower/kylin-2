package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

public class TransportOrderGoodExceptionResult extends BaseEntity{

	private static final long serialVersionUID = -6785413029101287146L;

	private String transportCode;//运单号
	
	private String userCompany;
	
	private String faZhan;
	
	private String daoZhan;
	
	private String customerName;
	
	private String customerOrderCode;
	
	private String carNo;
	
	private String driverName;
	
	private String driverTelephone;
	
	private String pinMing;
	
	private Integer piece;
	
	private Double volume;
	
	private Double weight;
    private String handOver;//交接人
    private String packing;//包装
    private String id;//货损货差编号

	public String getHandOver() {
		return handOver;
	}

	public void setHandOver(String handOver) {
		this.handOver = handOver;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFaZhan() {
		return faZhan;
	}

	public void setFaZhan(String faZhan) {
		this.faZhan = faZhan;
	}

	public String getDaoZhan() {
		return daoZhan;
	}

	public void setDaoZhan(String daoZhan) {
		this.daoZhan = daoZhan;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerOrderCode() {
		return customerOrderCode;
	}

	public void setCustomerOrderCode(String customerOrderCode) {
		this.customerOrderCode = customerOrderCode;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverTelephone() {
		return driverTelephone;
	}

	public void setDriverTelephone(String driverTelephone) {
		this.driverTelephone = driverTelephone;
	}

	public String getPinMing() {
		return pinMing;
	}

	public void setPinMing(String pinMing) {
		this.pinMing = pinMing;
	}

	public Integer getPiece() {
		return piece;
	}

	public void setPiece(Integer piece) {
		this.piece = piece;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getTransportCode() {
		return transportCode;
	}

	public void setTransportCode(String transportCode) {
		this.transportCode = transportCode;
	}

	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}
	
}
