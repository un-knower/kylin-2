package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;

/**
 * 公司表
 */
public class CompanyInfo implements Serializable{
	private String bh;
	private Integer id;
	private String name;
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
