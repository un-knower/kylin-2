package com.ycgwl.kylin.security.entity;

import java.io.Serializable;

/**
 * 
 * <p>Title: TUserGs.java</p> 
 *@date 2018年6月26日 
 *@author ltao 112656
 *@version 1.0
 *@用户与公司对照表(允许一个用户名下有多个公司)
 */
public class KylinTUserGsEntity implements Serializable {

	private static final long serialVersionUID = -4807283935620881101L;
	
	private String grid;
	private String gs;
	public String getGrid() {
		return grid;
	}
	public void setGrid(String grid) {
		this.grid = grid;
	}
	public String getGs() {
		return gs;
	}
	public void setGs(String gs) {
		this.gs = gs;
	}
	
	
}
