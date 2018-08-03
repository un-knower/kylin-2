package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 撤销日志实体
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月28日
 */
public class UndoSignLog extends BaseEntity{

	private static final long serialVersionUID = -6998448656091493212L;

	private Integer id;
	
	private String ydbhid;
	
	private String opeGrid;
	
	private String opeXm;
	
	private Date opeDate;
	
	private String opeType;
	
	private Date recgenDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public String getOpeGrid() {
		return opeGrid;
	}

	public void setOpeGrid(String opeGrid) {
		this.opeGrid = opeGrid;
	}

	public String getOpeXm() {
		return opeXm;
	}

	public void setOpeXm(String opeXm) {
		this.opeXm = opeXm;
	}

	public Date getOpeDate() {
		return opeDate;
	}

	public void setOpeDate(Date opeDate) {
		this.opeDate = opeDate;
	}

	public String getOpeType() {
		return opeType;
	}

	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}

	public Date getRecgenDate() {
		return recgenDate;
	}

	public void setRecgenDate(Date recgenDate) {
		this.recgenDate = recgenDate;
	}
	
}
