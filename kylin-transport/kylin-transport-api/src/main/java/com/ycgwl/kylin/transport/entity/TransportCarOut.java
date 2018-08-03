package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 派车单信息
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
 */
public class TransportCarOut extends BaseEntity{

	private static final long serialVersionUID = -3180008884417934639L;

	private String gsid;
	
	private Integer id;
	
	private String gs;
	
	private String yshm;
	
	private String ywlx;
	
	private String fhdw;
	
	private String fhlxr;
	
	private String shdw;
	
	private String shlxr;
	
	private String shdz;
	
	private String shdh;
	
	private String dzy;
	
	private String dzyGrid;
	
	private Date kdTime; 
	
	private Date jhshTime;
	
	private Date thTime;
	
	private String zxbz;
	
	private String cgyqm;
	
	private String comm;
	
	private String khqs;
	
	private Date khqsTime;
	
	private String pcdd;
	
	private String pcddGrid;
	
	private Date pcpcTime;
	
	private String pcqsd;
	
	private String pcshd;
	
	private String pcComm;
	
	private String sjComm;
	
	private String tjhsr;
	
	private String tjhsrGrid;
	
	private String tjComm;
	
	private Date tjTime;
	
	private Integer pcyes;
	
	private String fhdh;
	
	private Integer inout;
	
	private Integer shjhm;
	
	private String ydbhid;
	
	private String rowguid;
	
	private String dzshhd;
	
	private Integer pcshlc;
	
	private Integer min;
	
	private Integer max;
	
	private String mdd;
	
	private Integer tangci;
	
	private String pcmdd;
	
	private String khObject;
	
	private String sysName;

	public String getGsid() {
		return gsid;
	}

	public void setGsid(String gsid) {
		this.gsid = gsid;
	}

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

	public String getYshm() {
		return yshm;
	}

	public void setYshm(String yshm) {
		this.yshm = yshm;
	}

	public String getYwlx() {
		return ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	public String getFhdw() {
		return fhdw;
	}

	public void setFhdw(String fhdw) {
		this.fhdw = fhdw;
	}

	public String getFhlxr() {
		return fhlxr;
	}

	public void setFhlxr(String fhlxr) {
		this.fhlxr = fhlxr;
	}

	public String getShdw() {
		return shdw;
	}

	public void setShdw(String shdw) {
		this.shdw = shdw;
	}

	public String getShlxr() {
		return shlxr;
	}

	public void setShlxr(String shlxr) {
		this.shlxr = shlxr;
	}

	public String getShdz() {
		return shdz;
	}

	public void setShdz(String shdz) {
		this.shdz = shdz;
	}

	public String getShdh() {
		return shdh;
	}

	public void setShdh(String shdh) {
		this.shdh = shdh;
	}

	public String getDzy() {
		return dzy;
	}

	public void setDzy(String dzy) {
		this.dzy = dzy;
	}

	public String getDzyGrid() {
		return dzyGrid;
	}

	public void setDzyGrid(String dzyGrid) {
		this.dzyGrid = dzyGrid;
	}

	public Date getKdTime() {
		return kdTime;
	}

	public void setKdTime(Date kdTime) {
		this.kdTime = kdTime;
	}

	public Date getJhshTime() {
		return jhshTime;
	}

	public void setJhshTime(Date jhshTime) {
		this.jhshTime = jhshTime;
	}

	public Date getThTime() {
		return thTime;
	}

	public void setThTime(Date thTime) {
		this.thTime = thTime;
	}

	public String getZxbz() {
		return zxbz;
	}

	public void setZxbz(String zxbz) {
		this.zxbz = zxbz;
	}

	public String getCgyqm() {
		return cgyqm;
	}

	public void setCgyqm(String cgyqm) {
		this.cgyqm = cgyqm;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public String getKhqs() {
		return khqs;
	}

	public void setKhqs(String khqs) {
		this.khqs = khqs;
	}

	public Date getKhqsTime() {
		return khqsTime;
	}

	public void setKhqsTime(Date khqsTime) {
		this.khqsTime = khqsTime;
	}

	public String getPcdd() {
		return pcdd;
	}

	public void setPcdd(String pcdd) {
		this.pcdd = pcdd;
	}

	public String getPcddGrid() {
		return pcddGrid;
	}

	public void setPcddGrid(String pcddGrid) {
		this.pcddGrid = pcddGrid;
	}

	public Date getPcpcTime() {
		return pcpcTime;
	}

	public void setPcpcTime(Date pcpcTime) {
		this.pcpcTime = pcpcTime;
	}

	public String getPcqsd() {
		return pcqsd;
	}

	public void setPcqsd(String pcqsd) {
		this.pcqsd = pcqsd;
	}

	public String getPcshd() {
		return pcshd;
	}

	public void setPcshd(String pcshd) {
		this.pcshd = pcshd;
	}

	public String getPcComm() {
		return pcComm;
	}

	public void setPcComm(String pcComm) {
		this.pcComm = pcComm;
	}

	public String getSjComm() {
		return sjComm;
	}

	public void setSjComm(String sjComm) {
		this.sjComm = sjComm;
	}

	public String getTjhsr() {
		return tjhsr;
	}

	public void setTjhsr(String tjhsr) {
		this.tjhsr = tjhsr;
	}

	public String getTjhsrGrid() {
		return tjhsrGrid;
	}

	public void setTjhsrGrid(String tjhsrGrid) {
		this.tjhsrGrid = tjhsrGrid;
	}

	public String getTjComm() {
		return tjComm;
	}

	public void setTjComm(String tjComm) {
		this.tjComm = tjComm;
	}

	public Date getTjTime() {
		return tjTime;
	}

	public void setTjTime(Date tjTime) {
		this.tjTime = tjTime;
	}

	public Integer getPcyes() {
		return pcyes;
	}

	public void setPcyes(Integer pcyes) {
		this.pcyes = pcyes;
	}

	public String getFhdh() {
		return fhdh;
	}

	public void setFhdh(String fhdh) {
		this.fhdh = fhdh;
	}

	public Integer getInout() {
		return inout;
	}

	public void setInout(Integer inout) {
		this.inout = inout;
	}

	public Integer getShjhm() {
		return shjhm;
	}

	public void setShjhm(Integer shjhm) {
		this.shjhm = shjhm;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public String getRowguid() {
		return rowguid;
	}

	public void setRowguid(String rowguid) {
		this.rowguid = rowguid;
	}

	public String getDzshhd() {
		return dzshhd;
	}

	public void setDzshhd(String dzshhd) {
		this.dzshhd = dzshhd;
	}

	public Integer getPcshlc() {
		return pcshlc;
	}

	public void setPcshlc(Integer pcshlc) {
		this.pcshlc = pcshlc;
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

	public String getMdd() {
		return mdd;
	}

	public void setMdd(String mdd) {
		this.mdd = mdd;
	}

	public Integer getTangci() {
		return tangci;
	}

	public void setTangci(Integer tangci) {
		this.tangci = tangci;
	}

	public String getPcmdd() {
		return pcmdd;
	}

	public void setPcmdd(String pcmdd) {
		this.pcmdd = pcmdd;
	}

	public String getKhObject() {
		return khObject;
	}

	public void setKhObject(String khObject) {
		this.khObject = khObject;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
}
