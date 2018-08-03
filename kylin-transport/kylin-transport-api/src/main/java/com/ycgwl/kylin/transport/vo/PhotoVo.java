package com.ycgwl.kylin.transport.vo;

import java.io.Serializable;

public class PhotoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3794895280727103133L;
	public String src;
	public String photoName;
	public String waybillNum;
	public String description;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
