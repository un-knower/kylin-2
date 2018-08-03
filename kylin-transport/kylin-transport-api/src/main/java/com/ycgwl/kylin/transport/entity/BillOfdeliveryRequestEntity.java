package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;
/**
 * 提货装载清单查询实体
 * TODO Add description
 * <p>
 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-01-24 10:34:37
 */
public class BillOfdeliveryRequestEntity extends BaseEntity{

	private static final long serialVersionUID = -1118379044747564402L;
	
	private Integer pageNums;
	private Integer pageSizes;
	/**运单号*/
	private String ydbhid;
	/**提货单号*/
	private String thqshdid;
	/**公司*/
	private String companyName;
	/**提货人名称*/
	private String thrmch;
	/**收货人名称*/
	private String shhrmch;
	
	public Integer getPageNums() {
		return pageNums;
	}
	public Integer getPageSizes() {
		return pageSizes;
	}
	public void setPageNums(Integer pageNums) {
		this.pageNums = pageNums;
	}
	public void setPageSizes(Integer pageSizes) {
		this.pageSizes = pageSizes;
	}
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public String getThqshdid() {
		return thqshdid;
	}
	public void setThqshdid(String thqshdid) {
		this.thqshdid = thqshdid;
	}
	public String getGs() {
		return companyName;
	}
	public void setGs(String gs) {
		this.companyName = gs;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getThrmch() {
		return thrmch;
	}
	public void setThrmch(String thrmch) {
		this.thrmch = thrmch;
	}
	public String getShhrmch() {
		return shhrmch;
	}
	public void setShhrmch(String shhrmch) {
		this.shhrmch = shhrmch;
	}
}
