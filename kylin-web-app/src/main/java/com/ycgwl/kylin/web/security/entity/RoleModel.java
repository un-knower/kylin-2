package com.ycgwl.kylin.web.security.entity;

import com.ycgwl.kylin.entity.RequestEntity;
import com.ycgwl.kylin.security.entity.Role;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-04-28 14:49:12
 */

public class RoleModel extends RequestEntity {

	private static final long serialVersionUID = 6701847059781041037L;
	
	private Role role;
	private Collection<Integer> resKeys;
	
	public RoleModel() {
		super();
		role = new Role();
		resKeys = new ArrayList<Integer>();
	}
	public int getEnable() {
		if(role.getEnable() == null){
			return 0;
		}
		return role.getEnable() ? 1 : 2;
	}
	public void setEnable(int enable) {
		if(enable == 0){
			role.setEnable(null);
		} else if(enable == 1){
			role.setEnable(true);
		}else {
			role.setEnable(false);
		}
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Collection<Integer> getResKeys() {
		return resKeys;
	}
	public void setResKeys(Collection<Integer> resKeys) {
		this.resKeys = resKeys;
	}
	
	public Integer getRoleId() {
		return role.getRoleId();
	}
	public String getRoleName() {
		return role.getRoleName();
	}
	public String getDescription() {
		return role.getDescription();
	}
}
