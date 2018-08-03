package com.ycgwl.kylin.transport.dto;

import java.io.Serializable;
/**
 * 
 * 客户基本信Dto<br> 
 * 传入参数
 * @author zdl
 * @version [V1.0, 2018年5月15日]
 */
public class CustomerInfoDto implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private String Account;//账号
	
	private String GKH;//用户名
	
	private String id;
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
	private String status;// 合作状态 khzt
	private String area;// 客户区域khchsh
	private String industry;// 运输方式 ysfs
	
	//20180611新增
	private String ywky ;//铁路快件  (1.是,0.否)
	private String ywxb;//行包(1.是,0.否)
	private String ywwd;//五定(1.是,0.否)
	private String ywxy;//行邮   (1.是,0.否)
	private String ywhk;//航空 (1.是,0.否)
	private String ywjzx;//集装箱(1.是,0.否)
	private String ywcj;//城际配送(1.是,0.否)
	private String ywsy;//水运(1.是,0.否)
	private String ywfl;//分理  (1.是,0.否)
	private String ywgjys;//国际运输(1.是,0.否)
	private String ywzz;//中转    (1.是,0.否)
	private String ywcc;//仓储  (1.是,0.否)
	private String ywwx;//外线 (1.是,0.否)
	
	
	private Integer greenChannel;// 是否是绿色通道 YesNoGreenChannel
	private String mobile;// 客户手机号 khsj
	private String guestType;// 客户类别 guesttype
	private String sellCode;// 销售编码 xiaoshoucode
	// 2018年5月14号新增字段
	private String contractPostion; // 职务lxrzw
	private String goodsName; // 货物名称pinming
	private String contractPhone;// 客户手机khsj
	private String clearingcode;// 结款方式clearingcode
	private String quantity; // 数量quantity
	private String isContract; // 有无合同ywht
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
	private String isneedReturnedDocument;// 是否返单结算isneedreturneddocument
	private String contractStartDate;// 合同开始时间contractstartdate
	private String contractEndDate;// 合同截止时间contractenddate
	private String firststatementDate;// 初次对账日期firststatementdate
	private String lastStatementDate;// 下次对账日期lastStatementdate
	private String corporateRepresentative; // 法人代表frdb
	private String consterType;// 客户类型khlxbh
	private String zyzm;// 准运证明（1.是 0.否）
	private String fzr;// 负责人
	private String fzrzw;// 负责人职位
	private String fzrdh;// 手机
	private String fzrsr;// 生日
	private String cgydh;//电话
	private String hwliux;// 主营流向
	private String khjj;// 客户简介
	private String djrq;// 登记日期
	private String ksrq;// 开始合作日期
	private String zxTime;// 执行日期zx_time
	private String ywjdId;// 业务接单号ywjdid
	private String recordstatus;//客户状态(1.有效  0.无效)
	
	
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
	public String getContractPhone() {
		return contractPhone;
	}
	public void setContractPhone(String contractPhone) {
		this.contractPhone = contractPhone;
	}
	public String getClearingcode() {
		return clearingcode;
	}
	public void setClearingcode(String clearingcode) {
		this.clearingcode = clearingcode;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getIsContract() {
		return isContract;
	}
	public void setIsContract(String isContract) {
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
	public String getIsneedReturnedDocument() {
		return isneedReturnedDocument;
	}
	public void setIsneedReturnedDocument(String isneedReturnedDocument) {
		this.isneedReturnedDocument = isneedReturnedDocument;
	}
	public String getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getFirststatementDate() {
		return firststatementDate;
	}
	public void setFirststatementDate(String firststatementDate) {
		this.firststatementDate = firststatementDate;
	}
	public String getLastStatementDate() {
		return lastStatementDate;
	}
	public void setLastStatementDate(String lastStatementDate) {
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
	public String getDjrq() {
		return djrq;
	}
	public void setDjrq(String djrq) {
		this.djrq = djrq;
	}
	public String getKsrq() {
		return ksrq;
	}
	public void setKsrq(String ksrq) {
		this.ksrq = ksrq;
	}
	public String getZxTime() {
		return zxTime;
	}
	public void setZxTime(String zxTime) {
		this.zxTime = zxTime;
	}
	public String getYwjdId() {
		return ywjdId;
	}
	public void setYwjdId(String ywjdId) {
		this.ywjdId = ywjdId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return Account;
	}
	public void setAccount(String account) {
		Account = account;
	}
	public String getYwky() {
		return ywky;
	}
	public void setYwky(String ywky) {
		this.ywky = ywky;
	}
	public String getYwxb() {
		return ywxb;
	}
	public void setYwxb(String ywxb) {
		this.ywxb = ywxb;
	}
	public String getYwwd() {
		return ywwd;
	}
	public void setYwwd(String ywwd) {
		this.ywwd = ywwd;
	}
	public String getYwxy() {
		return ywxy;
	}
	public void setYwxy(String ywxy) {
		this.ywxy = ywxy;
	}
	public String getYwhk() {
		return ywhk;
	}
	public void setYwhk(String ywhk) {
		this.ywhk = ywhk;
	}
	public String getYwjzx() {
		return ywjzx;
	}
	public void setYwjzx(String ywjzx) {
		this.ywjzx = ywjzx;
	}
	public String getYwcj() {
		return ywcj;
	}
	public void setYwcj(String ywcj) {
		this.ywcj = ywcj;
	}
	public String getYwsy() {
		return ywsy;
	}
	public void setYwsy(String ywsy) {
		this.ywsy = ywsy;
	}
	public String getYwfl() {
		return ywfl;
	}
	public void setYwfl(String ywfl) {
		this.ywfl = ywfl;
	}
	public String getYwgjys() {
		return ywgjys;
	}
	public void setYwgjys(String ywgjys) {
		this.ywgjys = ywgjys;
	}
	public String getYwzz() {
		return ywzz;
	}
	public void setYwzz(String ywzz) {
		this.ywzz = ywzz;
	}
	public String getYwcc() {
		return ywcc;
	}
	public void setYwcc(String ywcc) {
		this.ywcc = ywcc;
	}
	public String getYwwx() {
		return ywwx;
	}
	public void setYwwx(String ywwx) {
		this.ywwx = ywwx;
	}
	public String getRecordstatus() {
		return recordstatus;
	}
	public void setRecordstatus(String recordstatus) {
		this.recordstatus = recordstatus;
	}
	public String getCgydh() {
		return cgydh;
	}
	public void setCgydh(String cgydh) {
		this.cgydh = cgydh;
	}
	public String getGKH() {
		return GKH;
	}
	public void setGKH(String gKH) {
		GKH = gKH;
	}
	
	
	
	
	
    
    
	
	

}
