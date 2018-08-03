package com.ycgwl.kylin.security.entity;

import java.io.Serializable;

/**
 * 
 * <p>Title: KylinGongSi.java</p> 
 *@date 2018年7月11日 
 *@author ltao 112656
 *@version 1.0
 */
public class KylinGongSiEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1683751699697065413L;
	
	private String name;
	private String bh;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	
	

}
