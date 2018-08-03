package com.ycgwl.kylin.transport.entity.adjunct;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 运输方式
 * T_BASE_YSFS
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-05-03 17:30:43
 */
public class ConveyWay extends BaseEntity {

	private static final long serialVersionUID = 5141553888464086018L;
	private Integer wayKey;//id
	private String wayName;//名称 ysfs
	private Integer isky;
	private Integer isxc;
	
	public Integer getWayKey() {
		return wayKey;
	}
	public void setWayKey(Integer wayKey) {
		this.wayKey = wayKey;
	}
	public String getWayName() {
		return wayName;
	}
	public void setWayName(String wayName) {
		this.wayName = wayName;
	}
	public Integer getIsky() {
		return isky;
	}
	public void setIsky(Integer isky) {
		this.isky = isky;
	}
	public Integer getIsxc() {
		return isxc;
	}
	public void setIsxc(Integer isxc) {
		this.isxc = isxc;
	}
}
