/**
 * kylin-transport-api
 */
package com.ycgwl.kylin.transport.entity.adjunct;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 客户
 * customer
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-05-03 17:30:43
 */
public class Customer extends BaseEntity {

	private static final long serialVersionUID = -914703835221891123L;
    
	private String id;//id;
	private String company; // 公司名称 gs
	private String customerName;// 客户名称 KHMC
	private String customerKey;// 客户编码 KHBM
	private String address;// 客户通讯地址 khtxdz
	private String contactPerson;// 客户联系人KHLXR
	private String phone;// 客户电话 KHDH
	private String product;// 主营产品 hwmch
	private String invoiceName;// 发票名称 fpmc
	private String taxNumber;// 税号 swzh
	private String salesDeputy;// 销售代表 swdb
	private String status;// 客户状态 khzt
	private String area;// 客户区域khchsh
	private String industry;// 运输方式 ysfs
	private Integer greenChannel;// 是否是绿色通道 YesNoGreenChannel
	private String mobile;// 客户手机号 khsj
	private String guestType;// 客户类别 guesttype
	private String sellCode;// 销售编码 xiaoshoucode
	// 2018年5月14号新增字段
	private String contractPostion; // 职务lxrzw
	private String goodsName; // 货物名称pinming
	private String clearingcode;// 结款方式clearingcode
	private Integer quantity; // 数量quantity
	private Integer isContract; // 有无合同ywht
	private String contractNo;// 合同申请报告号hetonghao
	private String returnListStandrd;// 返单要求fdyq
	private String bankAddress;// 开户行地址basicaccount
	private String bankAccountNo;// 银行账号bankaccountno
	private String businessRegistrationNo;// 营业执照号码businessregistrationno
	private String switchBoardNo;// 总机号switchboardno
	private String companyUrl;// 公司网址companyurl
	private String shipper;// 发货联系人shipper
	private String checker;// 对账联系人checker
	private String shipperTel;// 发货人电话shippertel
	private String checkerTel;// 对账人电话checkertel
	private String shipperEmail;// 发货人邮件shipperemail
	private String checkerEmail;// 对账人邮件checkeremail
	private Integer isneedReturnedDocument;// 是否返单结算isneedreturneddocument
	private Date contractStartDate;// 合同开始时间contractstartdate
	private Date contractEndDate;// 合同截止时间contractenddate
	private Date firststatementDate;// 初次对账日期firststatementdate
	private Date lastStatementDate;// 下次对账日期lastStatementdate
	private String corporateRepresentative; // 法人代表frdb
	private String consterType;// 客户类型khlxbh
	private String zyzm;// 准运证明
	private String fzr;// 负责人
	private String fzrzw;// 负责人职位
	private String fzrdh;// 电话
	private String fzrsr;// 生日
	private String hwliux;// 主营流向
	private String khjj;// 客户简介
	private Date djrq;// 登记日期
	private Date ksrq;// 开始合作日期
	private Date zxTime;// 执行日期zx_time
	private Integer ywjdId;// 业务接单号ywjdid
    
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getSalesDeputy() {
		return salesDeputy;
	}

	public void setSalesDeputy(String salesDeputy) {
		this.salesDeputy = salesDeputy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
 
	public Integer getGreenChannel() {
		return greenChannel;
	}

	public void setGreenChannel(Integer greenChannel) {
		this.greenChannel = greenChannel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGuestType() {
		return guestType;
	}

	public void setGuestType(String guestType) {
		this.guestType = guestType;
	}

	public String getSellCode() {
		return sellCode;
	}

	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}
	
	//2018年05月14号新增Getter()和Setter();
	public String getContractPostion() {
		return contractPostion;
	}

	public void setContractPostion(String contractPostion) {
		this.contractPostion = contractPostion;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getClearingcode() {
		return clearingcode;
	}

	public void setClearingcode(String clearingcode) {
		this.clearingcode = clearingcode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getIsContract() {
		return isContract;
	}

	public void setIsContract(Integer isContract) {
		this.isContract = isContract;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getReturnListStandrd() {
		return returnListStandrd;
	}

	public void setReturnListStandrd(String returnListStandrd) {
		this.returnListStandrd = returnListStandrd;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBusinessRegistrationNo() {
		return businessRegistrationNo;
	}

	public void setBusinessRegistrationNo(String businessRegistrationNo) {
		this.businessRegistrationNo = businessRegistrationNo;
	}

	public String getSwitchBoardNo() {
		return switchBoardNo;
	}

	public void setSwitchBoardNo(String switchBoardNo) {
		this.switchBoardNo = switchBoardNo;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
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

	public String getShipperTel() {
		return shipperTel;
	}

	public void setShipperTel(String shipperTel) {
		this.shipperTel = shipperTel;
	}

	public String getCheckerTel() {
		return checkerTel;
	}

	public void setCheckerTel(String checkerTel) {
		this.checkerTel = checkerTel;
	}

	public String getShipperEmail() {
		return shipperEmail;
	}

	public void setShipperEmail(String shipperEmail) {
		this.shipperEmail = shipperEmail;
	}

	public String getCheckerEmail() {
		return checkerEmail;
	}

	public void setCheckerEmail(String checkerEmail) {
		this.checkerEmail = checkerEmail;
	}

	public Integer getIsneedReturnedDocument() {
		return isneedReturnedDocument;
	}

	public void setIsneedReturnedDocument(Integer isneedReturnedDocument) {
		this.isneedReturnedDocument = isneedReturnedDocument;
	}

	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public Date getFirststatementDate() {
		return firststatementDate;
	}

	public void setFirststatementDate(Date firststatementDate) {
		this.firststatementDate = firststatementDate;
	}

	public Date getLastStatementDate() {
		return lastStatementDate;
	}

	public void setLastStatementDate(Date lastStatementDate) {
		this.lastStatementDate = lastStatementDate;
	}

	public String getCorporateRepresentative() {
		return corporateRepresentative;
	}

	public void setCorporateRepresentative(String corporateRepresentative) {
		this.corporateRepresentative = corporateRepresentative;
	}

	public String getConsterType() {
		return consterType;
	}

	public void setConsterType(String consterType) {
		this.consterType = consterType;
	}

	public String getZyzm() {
		return zyzm;
	}

	public void setZyzm(String zyzm) {
		this.zyzm = zyzm;
	}

	public String getFzr() {
		return fzr;
	}

	public void setFzr(String fzr) {
		this.fzr = fzr;
	}

	public String getFzrzw() {
		return fzrzw;
	}

	public void setFzrzw(String fzrzw) {
		this.fzrzw = fzrzw;
	}

	public String getFzrdh() {
		return fzrdh;
	}

	public void setFzrdh(String fzrdh) {
		this.fzrdh = fzrdh;
	}

	public String getFzrsr() {
		return fzrsr;
	}

	public void setFzrsr(String fzrsr) {
		this.fzrsr = fzrsr;
	}

	public String getHwliux() {
		return hwliux;
	}

	public void setHwliux(String hwliux) {
		this.hwliux = hwliux;
	}

	public String getKhjj() {
		return khjj;
	}

	public void setKhjj(String khjj) {
		this.khjj = khjj;
	}

	public Date getDjrq() {
		return djrq;
	}

	public void setDjrq(Date djrq) {
		this.djrq = djrq;
	}

	public Date getKsrq() {
		return ksrq;
	}

	public void setKsrq(Date ksrq) {
		this.ksrq = ksrq;
	}

	public Date getZxTime() {
		return zxTime;
	}

	public void setZxTime(Date zxTime) {
		this.zxTime = zxTime;
	}

	public Integer getYwjdId() {
		return ywjdId;
	}

	public void setYwjdId(Integer ywjdId) {
		this.ywjdId = ywjdId;
	}

    
	
	
	
}
