package com.ycgwl.kylin.transport.dto;

import java.io.Serializable;

public class FreightRecordDetailSerachDto implements Serializable {

	private static final long serialVersionUID = 50433190054282083L;
	
	public String waybillNum;
	public String id;
	public String train;
	public String getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTrain() {
		return train;
	}
	public void setTrain(String train) {
		this.train = train;
	}

}
