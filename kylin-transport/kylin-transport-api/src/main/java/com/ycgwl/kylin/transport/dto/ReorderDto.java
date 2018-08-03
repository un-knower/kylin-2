package com.ycgwl.kylin.transport.dto;

import java.io.Serializable;

public class ReorderDto implements Serializable {

	private static final long serialVersionUID = 6579529372567311747L;
	
	// 返单日志参数 start
	public String waybillNum;
	public String xgr; // 修改人
	public String xgsj;//修改时间
	public String fdfsr;
	public String fdfsrgrid;
	public String fdjctime;//返单寄出时间
	public String fdyoujihao; //返单邮件号
	public String fdfscomm; //返单备注
	public String fdzt; //返单状态
	public String qrr; //确认人
	public String qrrgrid;//确认人公司
	public String qrtime;//确认时间
	public String fdjscomm;//返单接收时间
	public int fdJinZhiLuRu; //是否禁止录入
	// 返单日志参数 end
	
	public String fdqstime;
	public String account;
	public String company;
	public String fdfs;//返单方式
	public String fdlrtime;// 返单录入时间
	
	public String getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	public String getXgr() {
		return xgr;
	}
	public void setXgr(String xgr) {
		this.xgr = xgr;
	}
	public String getXgsj() {
		return xgsj;
	}
	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}
	public String getFdfsr() {
		return fdfsr;
	}
	public void setFdfsr(String fdfsr) {
		this.fdfsr = fdfsr;
	}
	public String getFdfsrgrid() {
		return fdfsrgrid;
	}
	public void setFdfsrgrid(String fdfsrgrid) {
		this.fdfsrgrid = fdfsrgrid;
	}
	public String getFdjctime() {
		return fdjctime;
	}
	public void setFdjctime(String fdjctime) {
		this.fdjctime = fdjctime;
	}
	public String getFdyoujihao() {
		return fdyoujihao;
	}
	public void setFdyoujihao(String fdyoujihao) {
		this.fdyoujihao = fdyoujihao;
	}
	public String getFdfscomm() {
		return fdfscomm;
	}
	public void setFdfscomm(String fdfscomm) {
		this.fdfscomm = fdfscomm;
	}
	public String getFdzt() {
		return fdzt;
	}
	public void setFdzt(String fdzt) {
		this.fdzt = fdzt;
	}
	public String getQrr() {
		return qrr;
	}
	public void setQrr(String qrr) {
		this.qrr = qrr;
	}
	public String getQrrgrid() {
		return qrrgrid;
	}
	public void setQrrgrid(String qrrgrid) {
		this.qrrgrid = qrrgrid;
	}
	public String getQrtime() {
		return qrtime;
	}
	public void setQrtime(String qrtime) {
		this.qrtime = qrtime;
	}
	public String getFdjscomm() {
		return fdjscomm;
	}
	public void setFdjscomm(String fdjscomm) {
		this.fdjscomm = fdjscomm;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCompany() {
		return company;
	}
	public int getFdJinZhiLuRu() {
		return fdJinZhiLuRu;
	}
	public void setFdJinZhiLuRu(int fdJinZhiLuRu) {
		this.fdJinZhiLuRu = fdJinZhiLuRu;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getFdfs() {
		return fdfs;
	}
	public void setFdfs(String fdfs) {
		this.fdfs = fdfs;
	}
	public String getFdlrtime() {
		return fdlrtime;
	}
	public void setFdlrtime(String fdlrtime) {
		this.fdlrtime = fdlrtime;
	}
	public String getFdqstime() {
		return fdqstime;
	}
	public void setFdqstime(String fdqstime) {
		this.fdqstime = fdqstime;
	}

}
