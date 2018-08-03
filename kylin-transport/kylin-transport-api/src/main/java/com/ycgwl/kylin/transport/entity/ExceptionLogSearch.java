package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.transport.entity.adjunct.PageAble;

import java.math.BigDecimal;

/**
 * 异常日志模糊查询实体（分页）
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
 */
public class ExceptionLogSearch extends PageAble{

	private static final long serialVersionUID = 8646167698046064929L;
	
	private String operatingMenu;
	
	private String ydbhid;
	
	private BigDecimal cwpzhbh;
	
	private String operatingTimeBegin;
	
	private String operatingTimeEnd;
	
	private String operatorCompany;

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

	public BigDecimal getCwpzhbh() {
		return cwpzhbh;
	}

	public void setCwpzhbh(BigDecimal cwpzhbh) {
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

	public String getOperatorCompany() {
		return operatorCompany;
	}

	public void setOperatorCompany(String operatorCompany) {
		this.operatorCompany = operatorCompany;
	}

}
