/**
 * kylin-admin-webapp
 * com.ycgwl.kylin.admin.security.entity
 */
package com.ycgwl.kylin.web.security.entity;

import com.ycgwl.kylin.entity.RequestEntity;
import com.ycgwl.kylin.security.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-04-28 14:49:12
 */
public class UserModel extends RequestEntity {

	private static final long serialVersionUID = 2885611099789748034L;
	
	private User user;
	private Collection<Integer> roleKeys;
	
	public UserModel() {
		super();
		user = new User();
		roleKeys = new ArrayList<Integer>();
	}
	public int getEnable() {
		if(user.getEnable() == null){
			return 0;
		}
		return user.getEnable() ? 1 : 2;
	}
	public void setEnable(int enable) {
		if(enable == 0){
			user.setEnable(null);
		} else if(enable == 1){
			user.setEnable(true);
		}else {
			user.setEnable(false);
		}
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Collection<Integer> getRoleKeys() {
		return roleKeys;
	}
	public void setRoleKeys(Collection<Integer> roleKeys) {
		this.roleKeys = roleKeys;
	}
	public String getAccount() {
		return user.getAccount();
	}
	public String getUserName() {
		return user.getUserName();
	}
	public Date getCreateTime() {
		return user.getCreateTime();
	}
	public String getCompany() {
		return user.getCompany();
	}
	public String getSubCompany() {
		return user.getSubCompany();
	}
}
