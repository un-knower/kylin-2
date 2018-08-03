package com.ycgwl.kylin.transport.dto;


import com.ycgwl.kylin.entity.RequestEntity;

public class FreightRecordDto extends RequestEntity{
	
	private static final long serialVersionUID = -4881200213885878583L;
	
	public int id;

	/** 
	 * 是否处理  1：已处理   0：未处理
	 */
	public int isHandle = 0;
	
	/** 
	 * 运单编号
	 */
	public String wayBillNum;
	
	/**
	 * 车次
	 */
	public String train;
	
	/**
	 * 装车到站
	 */
	public String arriveStation;
	
	/**
	 * 装车发站
	 */
	public String station;
	
	/**
	 * 公司
	 */
	public String company;
	
	/**
	 * 录入时间开始
	 */
	public String startTime;

	/**
	 * 录入时间结束
	 */
	public String endTime;
	
	/**
	 * 审核  0发站审核   1 到站审核  默认到站审核  2:通知公司处理
	 */
	public String examine;
	
	/**
	 * 是否处理   1已处理  0：未处理   默认发站审核
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public int getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(int isHandle) {
		this.isHandle = isHandle;
	}

	public String getWayBillNum() {
		return wayBillNum;
	}

	public void setWayBillNum(String wayBillNum) {
		this.wayBillNum = wayBillNum;
	}

	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
		this.train = train;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		if (startTime.length() > 0) {
			this.startTime = startTime + " 00:00:00.000";
		} else {
			this.startTime = startTime;
		}
		
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		if (endTime.length() > 0) {
			this.endTime = endTime + " 23:59:59.999";
		} else {
			this.endTime = endTime;
		}
		
	}

	public String getArriveStation() {
		return arriveStation;
	}

	public void setArriveStation(String arriveStation) {
		this.arriveStation = arriveStation;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getExamine() {
		return examine;
	}

	public void setExamine(String examine) {
		this.examine = examine;
	}

	
	
}
