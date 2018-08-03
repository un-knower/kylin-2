package com.ycgwl.kylin.security.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class User extends BaseEntity {
	
	private static final long serialVersionUID = 6674268322481188883L;

	
	private String account;//账号
	private String userName;//名称
	private String password;//密码
	private String xb;//性别
	private String zw;//职务
	private String bm;//部门
	private List<Integer> menuIdList;//用户下面的菜单集合
	private Date createTime;//创建时间
	private Boolean enable = true;	//状态
	private String company;//公司名称 (总公司 和 分公司名称)
	private String companyCode;//公司编码
	private String subCompany;//分公司名称
	private String netPoint;//营业网点
	private Collection<Role> roles;
	
	
	
	
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getZw() {
		return zw;
	}
	public void setZw(String zw) {
		this.zw = zw;
	}
	public String getBm() {
		return bm;
	}
	public void setBm(String bm) {
		this.bm = bm;
	}
	public List<Integer> getMenuIdList() {
		return menuIdList;
	}
	public void setMenuIdList(List<Integer> menuIdList) {
		this.menuIdList = menuIdList;
	}
	public String getNetPoint() {
		return netPoint;
	}
	public void setNetPoint(String netPoint) {
		this.netPoint = netPoint;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getSubCompany() {
		return subCompany;
	}
	public void setSubCompany(String subCompany) {
		this.subCompany = subCompany;
	}
	
	public String getCompanyString(){
		StringBuilder builder = new StringBuilder();
		if(company != null && !"".equals(company)){
			builder.append(company);
		}
		if(subCompany != null && !"".equals(subCompany)){
			if(builder.length() > 0){
				builder.append("-").append(subCompany);
			}else{
				builder.append(subCompany);
			}
		}
		return builder.toString();
	}
}
