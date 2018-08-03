package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 在途情况实体
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年2月27日
 */
public class TransportOrderTrace extends BaseEntity{

	private static final long serialVersionUID = -6281678766063597016L;

	private Integer id;
	
	private String createUser;
	
	private String createGsbh;
	
	private String createGs;
	
	private Date createDate;
	
	private String updateUser;
	
	private String updateGsbh;
	
	private String updateGs;
	
	private Date updateDate;
	
	private Integer recstatus;
	
	private String orderNo;
	
	private String ydbhid;
	
	private String location;
	
	private Integer status;
	
	private Date otDtime;
	
	private String remark;
	
	private String fxNo;
	
	private String fxCompany;
	
	private String fxCode;
	
	private String customerCode;
	
	private String customerName;
	
	private String customerOrderCode;
	
	private String orderType;
	
	private String carryonPrefigureDateSup;
	
	private String carryonReceiveDateSup;
	
	private String carryonZcdjIsContactSup;
	
	private String carryonPrefigureDate;
	
	private String carryonReceiveDate;
	
	private String carryonZcdjIsContact;
	
	private Integer receiveFlagSup;
	
	private Date receiveTimeSup;
	
	private Integer receiveFlag;
	
	private Date receiveTime;
	
	private String statusConfirm;
	
	private String ver;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateGsbh() {
		return createGsbh;
	}

	public void setCreateGsbh(String createGsbh) {
		this.createGsbh = createGsbh;
	}

	public String getCreateGs() {
		return createGs;
	}

	public void setCreateGs(String createGs) {
		this.createGs = createGs;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateGsbh() {
		return updateGsbh;
	}

	public void setUpdateGsbh(String updateGsbh) {
		this.updateGsbh = updateGsbh;
	}

	public String getUpdateGs() {
		return updateGs;
	}

	public void setUpdateGs(String updateGs) {
		this.updateGs = updateGs;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getRecstatus() {
		return recstatus;
	}

	public void setRecstatus(Integer recstatus) {
		this.recstatus = recstatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getOtDtime() {
		return otDtime;
	}

	public void setOtDtime(Date otDtime) {
		this.otDtime = otDtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFxNo() {
		return fxNo;
	}

	public void setFxNo(String fxNo) {
		this.fxNo = fxNo;
	}

	public String getFxCompany() {
		return fxCompany;
	}

	public void setFxCompany(String fxCompany) {
		this.fxCompany = fxCompany;
	}

	public String getFxCode() {
		return fxCode;
	}

	public void setFxCode(String fxCode) {
		this.fxCode = fxCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerOrderCode() {
		return customerOrderCode;
	}

	public void setCustomerOrderCode(String customerOrderCode) {
		this.customerOrderCode = customerOrderCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCarryonPrefigureDateSup() {
		return carryonPrefigureDateSup;
	}

	public void setCarryonPrefigureDateSup(String carryonPrefigureDateSup) {
		this.carryonPrefigureDateSup = carryonPrefigureDateSup;
	}

	public String getCarryonReceiveDateSup() {
		return carryonReceiveDateSup;
	}

	public void setCarryonReceiveDateSup(String carryonReceiveDateSup) {
		this.carryonReceiveDateSup = carryonReceiveDateSup;
	}

	public String getCarryonZcdjIsContactSup() {
		return carryonZcdjIsContactSup;
	}

	public void setCarryonZcdjIsContactSup(String carryonZcdjIsContactSup) {
		this.carryonZcdjIsContactSup = carryonZcdjIsContactSup;
	}

	public String getCarryonPrefigureDate() {
		return carryonPrefigureDate;
	}

	public void setCarryonPrefigureDate(String carryonPrefigureDate) {
		this.carryonPrefigureDate = carryonPrefigureDate;
	}

	public String getCarryonReceiveDate() {
		return carryonReceiveDate;
	}

	public void setCarryonReceiveDate(String carryonReceiveDate) {
		this.carryonReceiveDate = carryonReceiveDate;
	}

	public String getCarryonZcdjIsContact() {
		return carryonZcdjIsContact;
	}

	public void setCarryonZcdjIsContact(String carryonZcdjIsContact) {
		this.carryonZcdjIsContact = carryonZcdjIsContact;
	}

	public Integer getReceiveFlagSup() {
		return receiveFlagSup;
	}

	public void setReceiveFlagSup(Integer receiveFlagSup) {
		this.receiveFlagSup = receiveFlagSup;
	}

	public Date getReceiveTimeSup() {
		return receiveTimeSup;
	}

	public void setReceiveTimeSup(Date receiveTimeSup) {
		this.receiveTimeSup = receiveTimeSup;
	}

	public String getStatusConfirm() {
		return statusConfirm;
	}

	public void setStatusConfirm(String statusConfirm) {
		this.statusConfirm = statusConfirm;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public Integer getReceiveFlag() {
		return receiveFlag;
	}

	public void setReceiveFlag(Integer receiveFlag) {
		this.receiveFlag = receiveFlag;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

}
