package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class DeliveryOperateEntity extends BaseEntity {

	private static final long serialVersionUID = -2938485043455324965L;

	private String gsid;
	
	//派车单号
	private Integer id;
	
	//公司
	private String gs;
	
	//运输号码
	private String yshm;
	
	//业务类型
	private String ywlx;
	
	//发货单位
	private String fhdw;
	
	//送货单位
	private String shdw;
	
	//送货联系人
	private String shlxr;

	//送货地址
	private String shdz;
	
	//送货电话
	private String shdh;
	//单证员
	private String dzy;
	
	private String dzygrid;
	private Date kdtime;
	private Date jhshtime;
	private Date thtime;
	
	//装卸班组
	private String zxbz;
	//仓管员
	private String cgyqm;
	
//	dbo.T_QS.qsr;
	private String qsr;
//	dbo.T_QS.qstime;
	private Date qstime;
	//派车调度
	private String pcdd;
	//调度账号
	private String pcddgrid;
	
	private Date pcpctime;
	//起始地
	private String pcqsd;
	//送货地
	private String pcshd;
	//核算人
	private String tjhsr;
	//核算人账号
	private String tjhsrgrid;
	
	private Date tjtime;
	//已派车
	private Integer pcyes;
	//发货电话
	private String fhdh;
	private String ydbhid;
//	dbo.V_CAR_OUT_HEJI.JS;
	private Integer js;
//	dbo.V_CAR_OUT_HEJI.ZL;
	private Float zl;
//	dbo.V_CAR_OUT_HEJI.TJ;
	private Float tj;
//	dbo.V_CWPZH.hdfk;
	private BigDecimal hdfk;
//	dbo.V_CWPZH.dsk;
	private BigDecimal dsk;
//	dbo.HWYD.pszhsh;	派送指示
	private Integer pszhsh;
//	dbo.HWYD.shhd		发站网点
	private String shhd;
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
	public String getDzygrid() {
		return dzygrid;
	}
	public void setDzygrid(String dzygrid) {
		this.dzygrid = dzygrid;
	}
	public Date getKdtime() {
		return kdtime;
	}
	public void setKdtime(Date kdtime) {
		this.kdtime = kdtime;
	}
	public Date getJhshtime() {
		return jhshtime;
	}
	public void setJhshtime(Date jhshtime) {
		this.jhshtime = jhshtime;
	}
	public Date getThtime() {
		return thtime;
	}
	public void setThtime(Date thtime) {
		this.thtime = thtime;
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
	public String getQsr() {
		return qsr;
	}
	public void setQsr(String qsr) {
		this.qsr = qsr;
	}
	public Date getQstime() {
		return qstime;
	}
	public void setQstime(Date qstime) {
		this.qstime = qstime;
	}
	public String getPcdd() {
		return pcdd;
	}
	public void setPcdd(String pcdd) {
		this.pcdd = pcdd;
	}
	public String getPcddgrid() {
		return pcddgrid;
	}
	public void setPcddgrid(String pcddgrid) {
		this.pcddgrid = pcddgrid;
	}
	public Date getPcpctime() {
		return pcpctime;
	}
	public void setPcpctime(Date pcpctime) {
		this.pcpctime = pcpctime;
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
	public String getTjhsr() {
		return tjhsr;
	}
	public void setTjhsr(String tjhsr) {
		this.tjhsr = tjhsr;
	}
	public String getTjhsrgrid() {
		return tjhsrgrid;
	}
	public void setTjhsrgrid(String tjhsrgrid) {
		this.tjhsrgrid = tjhsrgrid;
	}
	public Date getTjtime() {
		return tjtime;
	}
	public void setTjtime(Date tjtime) {
		this.tjtime = tjtime;
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
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public Integer getJs() {
		return js;
	}
	public void setJs(Integer js) {
		this.js = js;
	}
	public Float getZl() {
		return zl;
	}
	public void setZl(Float zl) {
		this.zl = zl;
	}
	public Float getTj() {
		return tj;
	}
	public void setTj(Float tj) {
		this.tj = tj;
	}
	public BigDecimal getHdfk() {
		return hdfk;
	}
	public void setHdfk(BigDecimal hdfk) {
		this.hdfk = hdfk;
	}
	public BigDecimal getDsk() {
		return dsk;
	}
	public void setDsk(BigDecimal dsk) {
		this.dsk = dsk;
	}
	public Integer getPszhsh() {
		return pszhsh == null ? 0 : pszhsh;
	}
	public void setPszhsh(Integer pszhsh) {
		this.pszhsh = pszhsh;
	}
	public String getShhd() {
		return shhd;
	}
	public void setShhd(String shhd) {
		this.shhd = shhd;
	}
	
}
