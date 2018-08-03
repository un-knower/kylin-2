package com.ycgwl.kylin.transport.dto;

import java.io.Serializable;

public class FreightRecordSerachDto implements Serializable{

	private static final long serialVersionUID = -2167650077363244720L;
	
	public String type = "1";  //1:多票    0：单票
	
	public String train;
	
	public String startTime;
	
	public String endTime;
	
	public String waybillNum;
	
	public int exceptionType;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
		this.train = train;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	public int getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(int exceptionType) {
		this.exceptionType = exceptionType;
	}

	
}
