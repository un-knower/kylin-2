package com.ycgwl.kylin.web.transport.entity;

import com.ycgwl.kylin.entity.RequestEntity;

/**
 * 异常日志模糊查询实体（分页）
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
 */
public class ExceptionLogSearchModel extends RequestEntity {

	private static final long serialVersionUID = -250941795981066557L;

	private String operatingMenu;
	
	private String ydbhid;
	
	private String cwpzhbh;
	
	private String operatingTimeBegin;
	
	private String operatingTimeEnd;

	public String getOperatingMenu() {
		return operatingMenu;
	}

	public void setOperatingMenu(String operatingMenu) {
		this.operatingMenu = operatingMenu;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public String getCwpzhbh() {
		return cwpzhbh;
	}

	public void setCwpzhbh(String cwpzhbh) {
		this.cwpzhbh = cwpzhbh;
	}

	public String getOperatingTimeBegin() {
		return operatingTimeBegin;
	}

	public void setOperatingTimeBegin(String operatingTimeBegin) {
		this.operatingTimeBegin = operatingTimeBegin;
	}

	public String getOperatingTimeEnd() {
		return operatingTimeEnd;
	}

	public void setOperatingTimeEnd(String operatingTimeEnd) {
		this.operatingTimeEnd = operatingTimeEnd;
	}
	
}
