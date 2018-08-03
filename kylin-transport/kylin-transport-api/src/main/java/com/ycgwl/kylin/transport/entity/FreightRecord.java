package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 货运记录表   对应数据库 T_HYJL_MASTER
 * @author Acer
 *
 */
public class FreightRecord extends BaseEntity{

	private static final long serialVersionUID = 6575134697080342084L;
	
	public int id;
	
	/** 公司 */
	public String gs;
	
	/** 车次 */
	public String chc;
	
	/** 录入系统时间 */
	public Date shijian;
	
	/** 发车时间  */
	public Date fchsj;
	
	/** 到达时间 */
	public Date ddsj;
	
	/** 靠站时间 */
	public Date kzhsj;
	
	/** 开卸时间 */
	public Date kxsj;
	
	/** 卸空时间 */
	public Date xksj;
	
	/** 装车发站 */
	public String fazhan;
	
	/** 装车到站 */
	public String daozhan;
	
	/** 卸车班组 */
	public String xchbzh;
	
	/** 卸车操作员 */
	public String xchhyy;
	
	/** 操作主管 */
	public String dzhlb;
	
	public int ischeck;
	
	/** 到站意见 */
	public String dzhyj;
	
	/** 发站意见 */
	public String fzhyj;
	
	/** 营运中心意见 */
	public String yyzxyj;
	
	/** 录入人 */
	public String lrgrid;
	
	public String dzlrr;
	
	public Date dzlrsj;
	
	public int dzlrck;
	
	public String fzlrr;
	
	public String fzlrsj;
	
	public int fzlrck;
	
	public String yylrr;
	
	public Date yylrsj;
	
	public int yylrck;
	
	/** 天气情况 */
	public String tq;
	
	/** 运输方式 */
	public String ysfs;
	
	public String dzyzyj;
	
	/** 到站运作负责人 */
	public String dzyzlrr;
	
	public Date dzyzlrsj;
	
	public String dzdwyj;
	
	/** 到站单位负责人 */
	public String dzdwlrr;
	
	public String dzdwlrsj;
	
	public int dzdwlrck;
	
	public int dzyzlrck;
	
	/** 发站运作负责人 */
	public String fzyzlrr;
	
	/** 发站单位负责人 */
	public String fzdwlrr;
	
	public int fzyzlrck;
	
	public int fzdwlrck;
	
	/** 预估损失  1:500元以内   2:500-1000元  3:1000元以上 */
	public int loss_flag;
	
	/** 是否监卸  0:否  1:是  */
	public int sfjx;
	
	/** 是否现场确认 0:否  1:是  */
	public int sfxcqr;
	
	/** 是否设施完好  0:否  1:是 */
	public int sfsfwh;
	
	/** 司机姓名 */
	public String sjxm;
	
	/** 施封号 */
	public String sfh;
	
	/** 录入人  */
	public String lrr;
	
	
	// 2018-4-24  新加的字段
	/**
	 * 异常环节  2:提货  0:干线    1:配送
	 */
	public int abnormalType;

	/**
	 * 发生地点   
	 */
	public String happenedAdress;
	
	/**
	 * 到货时间
	 */
	public String arrivalTime;
	
	/**
	 * 发生时间
	 */
	public String happenedTime;
	
	/**
	 * 责任方
	 */
	public String responsibility;
	
	/**
	 * 是否处理
	 */
	public int isHandle;
	
	/**
	 * 预估损失
	 */
	public String estimatedLoss;
	
	/**
	 * 通知公司
	 */
	public String noticeCompany;
	
	/**
	 * 通知公司意见
	 */
	public String noticeCompanyOpinion;
	
	/**
	 * 装车时间
	 */
	public String fchrq;
	
	/**
	 * 供应商名称
	 */
	public String wxName;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGs() {
		return gs;
	}

	public void setGs(String gs) {
		this.gs = gs;
	}

	public String getChc() {
		return chc;
	}

	public void setChc(String chc) {
		this.chc = chc;
	}

	public Date getShijian() {
		return shijian;
	}

	public void setShijian(Date shijian) {
		this.shijian = shijian;
	}

	public Date getFchsj() {
		return fchsj;
	}

	public void setFchsj(Date fchsj) {
		this.fchsj = fchsj;
	}

	public Date getDdsj() {
		return ddsj;
	}

	public void setDdsj(Date ddsj) {
		this.ddsj = ddsj;
	}

	public Date getKzhsj() {
		return kzhsj;
	}

	public void setKzhsj(Date kzhsj) {
		this.kzhsj = kzhsj;
	}

	public Date getKxsj() {
		return kxsj;
	}

	public void setKxsj(Date kxsj) {
		this.kxsj = kxsj;
	}

	public Date getXksj() {
		return xksj;
	}

	public void setXksj(Date xksj) {
		this.xksj = xksj;
	}

	public String getFazhan() {
		return fazhan;
	}

	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}

	public String getDaozhan() {
		return daozhan;
	}

	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}

	public String getXchbzh() {
		return xchbzh;
	}

	public void setXchbzh(String xchbzh) {
		this.xchbzh = xchbzh;
	}

	public String getXchhyy() {
		return xchhyy;
	}

	public void setXchhyy(String xchhyy) {
		this.xchhyy = xchhyy;
	}

	public String getDzhlb() {
		return dzhlb;
	}

	public void setDzhlb(String dzhlb) {
		this.dzhlb = dzhlb;
	}

	public int getIscheck() {
		return ischeck;
	}

	public void setIscheck(int ischeck) {
		this.ischeck = ischeck;
	}

	public String getDzhyj() {
		return dzhyj;
	}

	public void setDzhyj(String dzhyj) {
		this.dzhyj = dzhyj;
	}

	public String getFzhyj() {
		return fzhyj;
	}

	public void setFzhyj(String fzhyj) {
		this.fzhyj = fzhyj;
	}

	public String getYyzxyj() {
		return yyzxyj;
	}

	public void setYyzxyj(String yyzxyj) {
		this.yyzxyj = yyzxyj;
	}

	public String getLrgrid() {
		return lrgrid;
	}

	public void setLrgrid(String lrgrid) {
		this.lrgrid = lrgrid;
	}

	public String getDzlrr() {
		return dzlrr;
	}

	public void setDzlrr(String dzlrr) {
		this.dzlrr = dzlrr;
	}

	public Date getDzlrsj() {
		return dzlrsj;
	}

	public void setDzlrsj(Date dzlrsj) {
		this.dzlrsj = dzlrsj;
	}

	public int getDzlrck() {
		return dzlrck;
	}

	public void setDzlrck(int dzlrck) {
		this.dzlrck = dzlrck;
	}

	public String getFzlrr() {
		return fzlrr;
	}

	public void setFzlrr(String fzlrr) {
		this.fzlrr = fzlrr;
	}

	public String getFzlrsj() {
		return fzlrsj;
	}

	public void setFzlrsj(String fzlrsj) {
		this.fzlrsj = fzlrsj;
	}

	public int getFzlrck() {
		return fzlrck;
	}

	public void setFzlrck(int fzlrck) {
		this.fzlrck = fzlrck;
	}

	public String getYylrr() {
		return yylrr;
	}

	public void setYylrr(String yylrr) {
		this.yylrr = yylrr;
	}

	public Date getYylrsj() {
		return yylrsj;
	}

	public void setYylrsj(Date yylrsj) {
		this.yylrsj = yylrsj;
	}

	public int getYylrck() {
		return yylrck;
	}

	public void setYylrck(int yylrck) {
		this.yylrck = yylrck;
	}

	public String getTq() {
		return tq;
	}

	public void setTq(String tq) {
		this.tq = tq;
	}

	public String getYsfs() {
		return ysfs;
	}

	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}

	public String getDzyzyj() {
		return dzyzyj;
	}

	public void setDzyzyj(String dzyzyj) {
		this.dzyzyj = dzyzyj;
	}

	public String getDzyzlrr() {
		return dzyzlrr;
	}

	public void setDzyzlrr(String dzyzlrr) {
		this.dzyzlrr = dzyzlrr;
	}

	public Date getDzyzlrsj() {
		return dzyzlrsj;
	}

	public void setDzyzlrsj(Date dzyzlrsj) {
		this.dzyzlrsj = dzyzlrsj;
	}

	public String getDzdwyj() {
		return dzdwyj;
	}

	public void setDzdwyj(String dzdwyj) {
		this.dzdwyj = dzdwyj;
	}

	public String getDzdwlrr() {
		return dzdwlrr;
	}

	public void setDzdwlrr(String dzdwlrr) {
		this.dzdwlrr = dzdwlrr;
	}

	public String getDzdwlrsj() {
		return dzdwlrsj;
	}

	public void setDzdwlrsj(String dzdwlrsj) {
		this.dzdwlrsj = dzdwlrsj;
	}

	public int getDzdwlrck() {
		return dzdwlrck;
	}

	public void setDzdwlrck(int dzdwlrck) {
		this.dzdwlrck = dzdwlrck;
	}

	public int getDzyzlrck() {
		return dzyzlrck;
	}

	public void setDzyzlrck(int dzyzlrck) {
		this.dzyzlrck = dzyzlrck;
	}

	public String getFzyzlrr() {
		return fzyzlrr;
	}

	public void setFzyzlrr(String fzyzlrr) {
		this.fzyzlrr = fzyzlrr;
	}

	public String getFzdwlrr() {
		return fzdwlrr;
	}

	public void setFzdwlrr(String fzdwlrr) {
		this.fzdwlrr = fzdwlrr;
	}

	public int getFzyzlrck() {
		return fzyzlrck;
	}

	public void setFzyzlrck(int fzyzlrck) {
		this.fzyzlrck = fzyzlrck;
	}

	public int getFzdwlrck() {
		return fzdwlrck;
	}

	public void setFzdwlrck(int fzdwlrck) {
		this.fzdwlrck = fzdwlrck;
	}

	public int getLoss_flag() {
		return loss_flag;
	}

	public void setLoss_flag(int loss_flag) {
		this.loss_flag = loss_flag;
	}

	public int getSfjx() {
		return sfjx;
	}

	public void setSfjx(int sfjx) {
		this.sfjx = sfjx;
	}

	public int getSfxcqr() {
		return sfxcqr;
	}

	public void setSfxcqr(int sfxcqr) {
		this.sfxcqr = sfxcqr;
	}

	public int getSfsfwh() {
		return sfsfwh;
	}

	public void setSfsfwh(int sfsfwh) {
		this.sfsfwh = sfsfwh;
	}

	public String getSjxm() {
		return sjxm;
	}

	public void setSjxm(String sjxm) {
		this.sjxm = sjxm;
	}

	public String getSfh() {
		return sfh;
	}

	public void setSfh(String sfh) {
		this.sfh = sfh;
	}

	public int getAbnormalType() {
		return abnormalType;
	}

	public void setAbnormalType(int abnormalType) {
		this.abnormalType = abnormalType;
	}

	public String getHappenedAdress() {
		return happenedAdress;
	}

	public void setHappenedAdress(String happenedAdress) {
		this.happenedAdress = happenedAdress;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getHappenedTime() {
		return happenedTime;
	}

	public void setHappenedTime(String happenedTime) {
		this.happenedTime = happenedTime;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public int getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(int isHandle) {
		this.isHandle = isHandle;
	}

	public String getEstimatedLoss() {
		return estimatedLoss;
	}

	public void setEstimatedLoss(String estimatedLoss) {
		this.estimatedLoss = estimatedLoss;
	}

	public String getLrr() {
		return lrr;
	}

	public void setLrr(String lrr) {
		this.lrr = lrr;
	}

	public String getNoticeCompany() {
		return noticeCompany;
	}

	public void setNoticeCompany(String noticeCompany) {
		this.noticeCompany = noticeCompany;
	}

	public String getNoticeCompanyOpinion() {
		return noticeCompanyOpinion;
	}

	public void setNoticeCompanyOpinion(String noticeCompanyOpinion) {
		this.noticeCompanyOpinion = noticeCompanyOpinion;
	}

	public String getFchrq() {
		return fchrq;
	}

	public void setFchrq(String fchrq) {
		this.fchrq = fchrq;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}
	

}
