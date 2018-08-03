package com.ycgwl.kylin.security.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <p>Title: KylinSysMenuEntity.java</p> 
 *@date 2018年5月16日 
 *@author ltao 112656
 *菜单实体
 *@version 1.0
 */
public class KylinSysMenuEntity implements Serializable {

	private static final long serialVersionUID = 1930348914449059103L;
	/**
	 *菜单id 
	 */
	private Integer menuId;
	/**
	 *菜单的名称 
	 */
	private String name;
	/**
	 *父级id 
	 */
	private Integer parentId;
	/**
	 *父菜单名称 
	 */
	private String parentName;
	/**
	 *路径 
	 */
	private String url;
	/**
	 *授权(多个用逗号分隔，如：user:list,user:create) 
	 */
	private String perms;
	/**
	 *类型(0：目录   1：菜单   2：按钮) 
	 */
	private Integer type;
	/**
	 *排序 
	 */
	private Integer orderNum;
	/**
	 *ztree属性 
	 */
	private Boolean open;
	/**
	 *菜单编码 
	 */
	private String menuCode;
	private List<?> list;
	
	
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPerms() {
		return perms;
	}
	public void setPerms(String perms) {
		this.perms = perms;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	
	
	
}
