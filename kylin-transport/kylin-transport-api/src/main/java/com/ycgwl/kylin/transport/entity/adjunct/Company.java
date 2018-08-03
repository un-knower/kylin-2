package com.ycgwl.kylin.transport.entity.adjunct;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 公司 
 * 表  gongsi
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-05-03 17:30:43
 */
public class Company extends BaseEntity {

	private static final long serialVersionUID = -8698329720472025754L;
	
	private String companyKey;//编号id
	private String companyName;//公司名称 name
	private String companyShort;//公司简称shx;
	private String companyCode;//公司编码 bh
	private String companyShortCode;//拼音简码 letterFirst;
	private Integer choose;
	private Integer wx;//是否是外线
	private String dkh;
	private Integer station;//是否是到站 isDaoZhan
	private String bigArea;//所在大区 qy
	private Integer type;//类型（1:物流，2:快运）
	public String getCompanyKey() {
		return companyKey;
	}
	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyShort() {
		return companyShort;
	}
	public void setCompanyShort(String companyShort) {
		this.companyShort = companyShort;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyShortCode() {
		return companyShortCode;
	}
	public void setCompanyShortCode(String companyShortCode) {
		this.companyShortCode = companyShortCode;
	}
	public Integer getChoose() {
		return choose;
	}
	public void setChoose(Integer choose) {
		this.choose = choose;
	}
	public Integer getWx() {
		return wx;
	}
	public void setWx(Integer wx) {
		this.wx = wx;
	}
	public String getDkh() {
		return dkh;
	}
	public void setDkh(String dkh) {
		this.dkh = dkh;
	}
	public Integer getStation() {
		return station;
	}
	public void setStation(Integer station) {
		this.station = station;
	}
	public String getBigArea() {
		return bigArea;
	}
	public void setBigArea(String bigArea) {
		this.bigArea = bigArea;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
