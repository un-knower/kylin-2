package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * @note 签收表实体类（T_QS表）.
 * <p>
 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年6月2日
 */
public class TransportSignRecord extends BaseEntity{
	
	private static final long serialVersionUID = -4852372286911328534L;

	public String account;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 运单编码
	 */
	private String ydbhid;
	
	/**
	 * 签收状态(默认是0)
	 */
	private Integer qszt;
	
	private String qsr;
	
	/**
	 * 签收时间.
	 */
	private Date qsTime;
	
	private String lrr;
	
	private Date khyqysTime;
	
	private Date lrTime;
	
	private String comm;
	
	private String lrrGrid;
	
	private String gs;
	
	private Date yxfhsj;
	
	private Double jianshu;
	
	private String qrr;
	
	private String qrrGrid;
	
	private Date qrTime;
	
	private Date fdqsTime;
	
	/**
	 * 默认是0
	 */
	private Integer fdzt;
	
	private String str1;
	
	private String str2;
	
	private String str3;
	
	private String fdfsr;
	
	private String fdfsrGrid;
	
	private Date fdjcTime;
	
	private Date fdlrTime;
	
	private String fdfsComm;
	
	private Integer fdfs;
	
	/**
	 * 默认是1
	 */
	private Integer shlc;
	
	private String fdjsComm;
	
	/**
	 * 主键newid()默认ID.
	 */
	private String rowGuid;
	
	/**
	 * 
	 */
	private String fdYouJiHao;
	
	/**
	 * 默认为0
	 */
	private Integer fdJinZhiLuRu;
	
	private Double psjianshu;
	
	private Double dsjianshu;
	
	private Integer sfxgbs;
	
	private Date oldQsTime;
	
	/**
	 * 默认为0
	 */
	private Integer sendFlag;
	
	private Integer min;
	
	private Integer max;
	
	private String daoZhanOk;
	
	private String cxFlag;
	
	private String qsrphone;

	/** 签收时间记录 */
	private Date recordQsTime;
	
	/** 撤销次数 */
	private Integer cxNumber;
	
	/** 签收类型 0或null:系统 1:手动 */
	public Integer signType=0;	
	
	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public Integer getQszt() {
		return qszt;
	}

	public void setQszt(Integer qszt) {
		this.qszt = qszt;
	}

	public String getQsr() {
		return qsr;
	}

	public void setQsr(String qsr) {
		this.qsr = qsr;
	}

	public Date getQsTime() {
		return qsTime;
	}

	public void setQsTime(Date qsTime) {
		this.qsTime = qsTime;
	}

	public String getLrr() {
		return lrr;
	}

	public void setLrr(String lrr) {
		this.lrr = lrr;
	}

	public Date getKhyqysTime() {
		return khyqysTime;
	}

	public void setKhyqysTime(Date khyqysTime) {
		this.khyqysTime = khyqysTime;
	}

	public Date getLrTime() {
		return lrTime;
	}

	public void setLrTime(Date lrTime) {
		this.lrTime = lrTime;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public String getLrrGrid() {
		return lrrGrid;
	}

	public void setLrrGrid(String lrrGrid) {
		this.lrrGrid = lrrGrid;
	}

	public String getGs() {
		return gs;
	}

	public void setGs(String gs) {
		this.gs = gs;
	}

	public Date getYxfhsj() {
		return yxfhsj;
	}

	public void setYxfhsj(Date yxfhsj) {
		this.yxfhsj = yxfhsj;
	}

	public Double getJianshu() {
		return jianshu;
	}

	public void setJianshu(Double jianshu) {
		this.jianshu = jianshu;
	}

	public String getQrr() {
		return qrr;
	}

	public void setQrr(String qrr) {
		this.qrr = qrr;
	}

	public String getQrrGrid() {
		return qrrGrid;
	}

	public void setQrrGrid(String qrrGrid) {
		this.qrrGrid = qrrGrid;
	}

	public Date getQrTime() {
		return qrTime;
	}

	public void setQrTime(Date qrTime) {
		this.qrTime = qrTime;
	}

	public Date getFdqsTime() {
		return fdqsTime;
	}

	public void setFdqsTime(Date fdqsTime) {
		this.fdqsTime = fdqsTime;
	}

	public Integer getFdzt() {
		return fdzt;
	}

	public void setFdzt(Integer fdzt) {
		this.fdzt = fdzt;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public String getStr3() {
		return str3;
	}

	public void setStr3(String str3) {
		this.str3 = str3;
	}

	public String getFdfsr() {
		return fdfsr;
	}

	public void setFdfsr(String fdfsr) {
		this.fdfsr = fdfsr;
	}

	public String getFdfsrGrid() {
		return fdfsrGrid;
	}

	public void setFdfsrGrid(String fdfsrGrid) {
		this.fdfsrGrid = fdfsrGrid;
	}

	public Date getFdjcTime() {
		return fdjcTime;
	}

	public void setFdjcTime(Date fdjcTime) {
		this.fdjcTime = fdjcTime;
	}

	public Date getFdlrTime() {
		return fdlrTime;
	}

	public void setFdlrTime(Date fdlrTime) {
		this.fdlrTime = fdlrTime;
	}

	public String getFdfsComm() {
		return fdfsComm;
	}

	public void setFdfsComm(String fdfsComm) {
		this.fdfsComm = fdfsComm;
	}

	public Integer getFdfs() {
		return fdfs;
	}

	public void setFdfs(Integer fdfs) {
		this.fdfs = fdfs;
	}

	public Integer getShlc() {
		return shlc;
	}

	public void setShlc(Integer shlc) {
		this.shlc = shlc;
	}

	public String getFdjsComm() {
		return fdjsComm;
	}

	public void setFdjsComm(String fdjsComm) {
		this.fdjsComm = fdjsComm;
	}

	public String getRowGuid() {
		return rowGuid;
	}

	public void setRowGuid(String rowGuid) {
		this.rowGuid = rowGuid;
	}

	public String getFdYouJiHao() {
		return fdYouJiHao;
	}

	public void setFdYouJiHao(String fdYouJiHao) {
		this.fdYouJiHao = fdYouJiHao;
	}

	public Integer getFdJinZhiLuRu() {
		return fdJinZhiLuRu;
	}

	public void setFdJinZhiLuRu(Integer fdJinZhiLuRu) {
		this.fdJinZhiLuRu = fdJinZhiLuRu;
	}

	public Double getPsjianshu() {
		return psjianshu;
	}

	public void setPsjianshu(Double psjianshu) {
		this.psjianshu = psjianshu;
	}

	public Double getDsjianshu() {
		return dsjianshu;
	}

	public void setDsjianshu(Double dsjianshu) {
		this.dsjianshu = dsjianshu;
	}

	public Integer getSfxgbs() {
		return sfxgbs;
	}

	public void setSfxgbs(Integer sfxgbs) {
		this.sfxgbs = sfxgbs;
	}

	public Date getOldQsTime() {
		return oldQsTime;
	}

	public void setOldQsTime(Date oldQsTime) {
		this.oldQsTime = oldQsTime;
	}

	public Integer getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public String getDaoZhanOk() {
		return daoZhanOk;
	}

	public void setDaoZhanOk(String daoZhanOk) {
		this.daoZhanOk = daoZhanOk;
	}

	public String getCxFlag() {
		return cxFlag;
	}

	public void setCxFlag(String cxFlag) {
		this.cxFlag = cxFlag;
	}

	public String getQsrphone() {
		return qsrphone;
	}

	public void setQsrphone(String qsrphone) {
		this.qsrphone = qsrphone;
	}

	public Date getRecordQsTime() {
		return recordQsTime;
	}

	public void setRecordQsTime(Date recordQsTime) {
		this.recordQsTime = recordQsTime;
	}

	public Integer getCxNumber() {
		return cxNumber;
	}

	public void setCxNumber(Integer cxNumber) {
		this.cxNumber = cxNumber;
	}
	
}
