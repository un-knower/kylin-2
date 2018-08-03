package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 财务收据
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月5日
 */
public class FinancialReceiptsMaster extends BaseEntity{

	private static final long serialVersionUID = 6957869038880972320L;

	private Integer id;
	
	private String gs;
	
	private String khbm;
	
	private String jkdw; 
	
	private String jkr;
	
	private Date jksj;
	
	private String sjtType;
	
	private String zhiPiao;
	
	private String chuNa;
	
	private String beiZhu;
	
	private Integer isReport;
	
	private Integer printed;
	
	private Integer isAccount;
	
	private Integer isShq;
	
	private Date sqsj;
	
	private String rowGuid;
	
	private String yshm;
	
	private Date bbsj;
	
	private String dzshhd;
	
	private String fiwtYdbhid;
	
	private Long fiwtCwpzhbh;
	
	private Integer type;
	
	private String sjchWhy;
	
	private Integer ver;
	
	private String exportFlag;
	
	private Integer sourceFlag;
	
	private String sysName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGs() {
		return gs;
	}

	public void setGs(String gs) {
		this.gs = gs;
	}

	public String getKhbm() {
		return khbm;
	}

	public void setKhbm(String khbm) {
		this.khbm = khbm;
	}

	public String getJkdw() {
		return jkdw;
	}

	public void setJkdw(String jkdw) {
		this.jkdw = jkdw;
	}

	public String getJkr() {
		return jkr;
	}

	public void setJkr(String jkr) {
		this.jkr = jkr;
	}

	public Date getJksj() {
		return jksj;
	}

	public void setJksj(Date jksj) {
		this.jksj = jksj;
	}

	public String getSjtType() {
		return sjtType;
	}

	public void setSjtType(String sjtType) {
		this.sjtType = sjtType;
	}

	public String getZhiPiao() {
		return zhiPiao;
	}

	public void setZhiPiao(String zhiPiao) {
		this.zhiPiao = zhiPiao;
	}

	public String getChuNa() {
		return chuNa;
	}

	public void setChuNa(String chuNa) {
		this.chuNa = chuNa;
	}

	public String getBeiZhu() {
		return beiZhu;
	}

	public void setBeiZhu(String beiZhu) {
		this.beiZhu = beiZhu;
	}

	public Integer getIsReport() {
		return isReport;
	}

	public void setIsReport(Integer isReport) {
		this.isReport = isReport;
	}

	public Integer getPrinted() {
		return printed;
	}

	public void setPrinted(Integer printed) {
		this.printed = printed;
	}

	public Integer getIsAccount() {
		return isAccount;
	}

	public void setIsAccount(Integer isAccount) {
		this.isAccount = isAccount;
	}

	public Integer getIsShq() {
		return isShq;
	}

	public void setIsShq(Integer isShq) {
		this.isShq = isShq;
	}

	public Date getSqsj() {
		return sqsj;
	}

	public void setSqsj(Date sqsj) {
		this.sqsj = sqsj;
	}

	public String getRowGuid() {
		return rowGuid;
	}

	public void setRowGuid(String rowGuid) {
		this.rowGuid = rowGuid;
	}

	public String getYshm() {
		return yshm;
	}

	public void setYshm(String yshm) {
		this.yshm = yshm;
	}

	public Date getBbsj() {
		return bbsj;
	}

	public void setBbsj(Date bbsj) {
		this.bbsj = bbsj;
	}

	public String getDzshhd() {
		return dzshhd;
	}

	public void setDzshhd(String dzshhd) {
		this.dzshhd = dzshhd;
	}

	public String getFiwtYdbhid() {
		return fiwtYdbhid;
	}

	public void setFiwtYdbhid(String fiwtYdbhid) {
		this.fiwtYdbhid = fiwtYdbhid;
	}

	public Long getFiwtCwpzhbh() {
		return fiwtCwpzhbh;
	}

	public void setFiwtCwpzhbh(Long fiwtCwpzhbh) {
		this.fiwtCwpzhbh = fiwtCwpzhbh;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSjchWhy() {
		return sjchWhy;
	}

	public void setSjchWhy(String sjchWhy) {
		this.sjchWhy = sjchWhy;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public String getExportFlag() {
		return exportFlag;
	}

	public void setExportFlag(String exportFlag) {
		this.exportFlag = exportFlag;
	}

	public Integer getSourceFlag() {
		return sourceFlag;
	}

	public void setSourceFlag(Integer sourceFlag) {
		this.sourceFlag = sourceFlag;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

}
