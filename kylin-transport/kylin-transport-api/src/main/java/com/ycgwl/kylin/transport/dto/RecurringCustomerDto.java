package com.ycgwl.kylin.transport.dto;

import java.io.Serializable;

public class RecurringCustomerDto implements Serializable {

	private static final long serialVersionUID = 4406867745083552888L;
	
	public String id;
	
	/**
	 * 结款方式  
	 * 周期-月结付款  C1  周期-周结付款  C2   周期-天结付款  C3   周期-返单月结  C4  周期-半月付款  C5
	 * 非周期-现金结款  N1    非周期-货到付款   N2    非周期-预付款  N3     一次性结款   Y1
	 */
	public String uclearingcode;
	
	/**
	 * 数量
	 */
	public int quantity;
	
	/**
	 * 是否预付款
	 */
	public String isyfk;
	
	public String basicaccount;
	public String bankaccountno;
	public String businessregistrationno;
	public String switchboardno;
	public String companyurl;
	public String shipper;
	public String checker;
	public String shippertel;
	public String checkertel;
	public String shipperemail;
	public String checkeremail;
	public String clearingcode;
	public String ufandan;
	public String isyuejie;
	public String contractstartdate;
	public String firststatementdate;
	public String contractenddate;
	public String laststatementdate;
	public String recordstatus;
	public String warningday;
	public String waringdaycont;
	/**
	 * 是否启用客户资料
	 */
	public String rec_flag;
	
	public String company;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUclearingcode() {
		return uclearingcode;
	}

	public void setUclearingcode(String uclearingcode) {
		this.uclearingcode = uclearingcode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getIsyfk() {
		return isyfk;
	}

	public void setIsyfk(String isyfk) {
		this.isyfk = isyfk;
	}

	public String getBasicaccount() {
		return basicaccount;
	}

	public void setBasicaccount(String basicaccount) {
		this.basicaccount = basicaccount;
	}

	public String getBankaccountno() {
		return bankaccountno;
	}

	public void setBankaccountno(String bankaccountno) {
		this.bankaccountno = bankaccountno;
	}

	public String getBusinessregistrationno() {
		return businessregistrationno;
	}

	public void setBusinessregistrationno(String businessregistrationno) {
		this.businessregistrationno = businessregistrationno;
	}

	public String getSwitchboardno() {
		return switchboardno;
	}

	public void setSwitchboardno(String switchboardno) {
		this.switchboardno = switchboardno;
	}

	public String getCompanyurl() {
		return companyurl;
	}

	public void setCompanyurl(String companyurl) {
		this.companyurl = companyurl;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getShippertel() {
		return shippertel;
	}

	public void setShippertel(String shippertel) {
		this.shippertel = shippertel;
	}

	public String getCheckertel() {
		return checkertel;
	}

	public void setCheckertel(String checkertel) {
		this.checkertel = checkertel;
	}

	public String getShipperemail() {
		return shipperemail;
	}

	public void setShipperemail(String shipperemail) {
		this.shipperemail = shipperemail;
	}

	public String getCheckeremail() {
		return checkeremail;
	}

	public void setCheckeremail(String checkeremail) {
		this.checkeremail = checkeremail;
	}

	public String getClearingcode() {
		return clearingcode;
	}

	public void setClearingcode(String clearingcode) {
		this.clearingcode = clearingcode;
	}

	public String getUfandan() {
		return ufandan;
	}

	public void setUfandan(String ufandan) {
		this.ufandan = ufandan;
	}

	public String getIsyuejie() {
		return isyuejie;
	}

	public void setIsyuejie(String isyuejie) {
		this.isyuejie = isyuejie;
	}

	public String getContractstartdate() {
		return contractstartdate;
	}

	public void setContractstartdate(String contractstartdate) {
		this.contractstartdate = contractstartdate;
	}

	public String getFirststatementdate() {
		return firststatementdate;
	}

	public void setFirststatementdate(String firststatementdate) {
		this.firststatementdate = firststatementdate;
	}

	public String getContractenddate() {
		return contractenddate;
	}

	public void setContractenddate(String contractenddate) {
		this.contractenddate = contractenddate;
	}

	public String getLaststatementdate() {
		return laststatementdate;
	}

	public void setLaststatementdate(String laststatementdate) {
		this.laststatementdate = laststatementdate;
	}

	public String getRecordstatus() {
		return recordstatus;
	}

	public void setRecordstatus(String recordstatus) {
		this.recordstatus = recordstatus;
	}

	public String getWarningday() {
		return warningday;
	}

	public void setWarningday(String warningday) {
		this.warningday = warningday;
	}

	public String getWaringdaycont() {
		return waringdaycont;
	}

	public void setWaringdaycont(String waringdaycont) {
		this.waringdaycont = waringdaycont;
	}

	public String getRec_flag() {
		return rec_flag;
	}

	public void setRec_flag(String rec_flag) {
		this.rec_flag = rec_flag;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	
	
}
