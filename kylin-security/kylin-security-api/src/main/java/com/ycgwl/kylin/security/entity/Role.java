package com.ycgwl.kylin.security.entity;

import com.ycgwl.kylin.entity.BaseEntity;

public class Role extends BaseEntity {

	private static final long serialVersionUID = 4926499587419146696L;

	private Integer roleId;
	private String roleName;
	private String description;
	private Boolean enable;
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
}
