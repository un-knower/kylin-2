package com.ycgwl.kylin.transport.entity;

/**
 * @Description 到货装载清单查询参数对象
 * <p>
 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2018年01月15日
 */
public class BundleArriveForm {
	
	/**
	 * 运单编号
	 */
	private String transportCode;
	
	/**
	 * 装车日期
	 */
	private String loadingDate;
	
	/**
	 * 车牌号
	 */
	private String plateNumber;
	
	/**
	 * 提货/送货状态
	 */
	private int pickupStatus;
	
	/**
	 * 自提或送货上门
	 */
	private int serviceType;

	public String getTransportCode() {
		return transportCode;
	}

	public void setTransportCode(String transportCode) {
		this.transportCode = transportCode;
	}

	public String getLoadingDate() {
		return loadingDate;
	}

	public void setLoadingDate(String loadingDate) {
		this.loadingDate = loadingDate;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public int getPickupStatus() {
		return pickupStatus;
	}

	public void setPickupStatus(int pickupStatus) {
		this.pickupStatus = pickupStatus;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}
}
