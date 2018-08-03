package com.ycgwl.kylin.transport.entity.adjunct;

import com.ycgwl.kylin.entity.BaseEntity;


/**
 * 车辆司机信息
 * The persistent class for the t_waichexinxi_head database table.
 */
public class VehicleInfo extends BaseEntity  {
	private static final long serialVersionUID = 1L;

	private String baseCphm;//base_cphm
	private String personSj1tel;//person_sj1tel
	private String personSj1xm;//person_sj1xm
	public String getBaseCphm() {
		return baseCphm;
	}
	public void setBaseCphm(String baseCphm) {
		this.baseCphm = baseCphm;
	}
	public String getPersonSj1tel() {
		return personSj1tel;
	}
	public void setPersonSj1tel(String personSj1tel) {
		this.personSj1tel = personSj1tel;
	}
	public String getPersonSj1xm() {
		return personSj1xm;
	}
	public void setPersonSj1xm(String personSj1xm) {
		this.personSj1xm = personSj1xm;
	}
}