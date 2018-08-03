/**
 * kylin-transport-api
 */
package com.ycgwl.kylin.transport.entity.adjunct;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 员工
 * ygzl
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-05-03 17:30:43
 */
public class Employee extends BaseEntity {

	private static final long serialVersionUID = -5054640043972117338L;
	private String employeeKey;//员工编号grid
	private String department;//部门bm
	private String emplyeeName;//员工姓名 xm
	private String office;//职务zw;
	private String companyName;//公司名称 gs
	private String mobile;//手机号mobile
	private String phone;//联系电话lxdh
	private Date officialTime;//转正时间 zzsj
	private Date entryTime;//入职时间rzsj
	private String idCardAddress;//身份证地址 sfzdz
	private String idCardNumber;//身份证号码sfzhm
	private String nativePlace;//籍贯jg
	private String famousFamily;//名族mz
	private Integer bodyHeight;//身高 sg
	private Float bodyWeight;//体重 tz
	private String sex;//性别xb;
	private String education;//学历xl;
	private String entryAddress;//入职地点 rzdd
	
	public String getEmployeeKey() {
		return employeeKey;
	}
	public void setEmployeeKey(String employeeKey) {
		this.employeeKey = employeeKey;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmplyeeName() {
		return emplyeeName;
	}
	public void setEmplyeeName(String emplyeeName) {
		this.emplyeeName = emplyeeName;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getOfficialTime() {
		return officialTime;
	}
	public void setOfficialTime(Date officialTime) {
		this.officialTime = officialTime;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public String getIdCardAddress() {
		return idCardAddress;
	}
	public void setIdCardAddress(String idCardAddress) {
		this.idCardAddress = idCardAddress;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getFamousFamily() {
		return famousFamily;
	}
	public void setFamousFamily(String famousFamily) {
		this.famousFamily = famousFamily;
	}
	public int getBodyHeight() {
		return bodyHeight;
	}
	public void setBodyHeight(int bodyHeight) {
		this.bodyHeight = bodyHeight;
	}
	public float getBodyWeight() {
		return bodyWeight;
	}
	public void setBodyWeight(float bodyWeight) {
		this.bodyWeight = bodyWeight;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getEntryAddress() {
		return entryAddress;
	}
	public void setEntryAddress(String entryAddress) {
		this.entryAddress = entryAddress;
	}
}
