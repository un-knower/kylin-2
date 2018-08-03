package com.ycgwl.kylin.security.entity;

import java.io.Serializable;

/**
 * 
 * <p>Title: KylinSysGsMenuEntity.java</p> 
 *@date 2018年5月16日 
 *@author ltao 112656
 *用户对应的菜单列表
 *@version 1.0
 */
public class KylinSysUserMenuEntity implements Serializable {

	private static final long serialVersionUID = -3561181601157520328L;

	private Integer id;
	private String grid;//工号
	private String gs;//公司
	private Integer menuId;//菜单的id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
	
	
	
}
