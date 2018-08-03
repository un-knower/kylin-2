package com.ycgwl.kylin.transport.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ycgwl.kylin.entity.BaseEntity;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Date;


/**
 * The persistent class for the HWYD database table.
 * 
 */
@JsonInclude(Include.NON_NULL) 
public class TransportOrder extends BaseEntity{
	
	private static final long serialVersionUID = -6727770560952618203L;
	
	/** 运单编号 */
	private String ydbhid; 

	/** 办单费 */
	private Double bandanfei; 

	/** 保险费 */
	private Double baoxianfei;

	/** 包装费 */
	private Double baozhuangfei;
	
	/** 始发地 */
	private Object beginPlacename;

	/** 其他服务项目 */
	private String beizhu;

	//包装费
	private Double bzf;

	//
	private Integer bzqr;

	private String bzzht;

	/** 仓位1 */
	private String cangwei1;

	/** 仓位2 */
	private String cangwei2;

	private Double caozuoyaoqiuDay;

	private Integer caozuoyaoqiuPcdf;

	private Integer caozuoyaoqiuXcps;

	private Integer caozuoyaoqiuZdsh;

	/** 车厢号 */
	private String chxh;

	private String chyrjzshx;

	private Integer cwpzhy;

	/** 代客包装 */
	private Integer daikebaozhuang;

	/** 代客装卸 */
	private Integer daikezhuangxie;

	/** 到达天数 */
	private Integer daodatianshu;

	/** 到站 */
	private String daozhan;

	/**代收款 */
	private Integer dashoukuan;

	/** 代收货款 */
	private Double dashoukuanYuan;

	/** 区 */
	private String dhAddr;

	/** 市 */
	private String dhChengsi;

	/** 省份 */
	private String dhShengfen;

	/** 纸箱 */
	private Integer dkbzCarton;

	/** 木箱 */
	private Integer dkbzWooden;
	
	/** 编织袋 */
	private Integer dkbzWoven;

	/**到站付款 */
	private Integer dzfk;

	/** 到站网点 */
	private String dzshhd;

	/** Edi标识 */
	private Integer ediflag;

	/** 目的地*/
	private Object endPlacename;

	/** 发站*/
	private String fazhan;

	private String fdlc;

	/** 返单名称 */
	private String fdnr;

	private String fdsl;

	/** 返单天数 */
	private Integer fdts;

	/** 返单要求 */
	private String fdyq;

	/** 客户地址*/
	private String fhdwdzh;

	/** 客户座机电话 */
	private String fhdwlxdh;

	/** 客户名称 */
	private String fhdwmch;

	/** 客户手机号码*/
	private String fhdwyb;

	/** 发货时间 */
	private Integer fhjs;

	/** 客户行业类别 */
	private String fhkhhy;

	/**发货人 */
	private String fhr;

	/**发货时间 */
	private Date fhshj;

	/** 发货体积 */
	private Double fhtiji;

	/** 发货重量 */
	private Double fhzhl;

	/** FKFSH（直接插入为0，不清楚中文意义，疑为付款方式，但值全部是0） */
	private Integer fkfsh = 0;

	private Integer flsj;

	/**发票全称 */
	private String fpmc;

	/** 服务方式 */
	private Integer fwfs;

	/**发站付款*/
	private Integer fzfk;

	private Date fzhchyrq;

	/** 公司编号*/
	private String gsbh;

	private String gysnamebh;

	private String gyspcdbh;

	/**  包装说明*/
	private String hwzht;

	/** 货运人*/
	private String hyy;

	private boolean idf;
	
	/** 导入标识 */
	private Integer importflag;

	private Integer isGreenchannel;

	/** 快运标识 */
	private short isKaiyun;

	/** 是否返单 */
	private Integer isfd;

	private boolean isjj;

	private Integer isshsx;

	/** 是否投保*/
	private Integer istb;

	private String jsgs;

	/**是否取整*/
	private Integer jshhj = 0;

	private String khKind;

	/** 客户编码 */
	private String khbm;

	/** 客户单号 */
	private String khdh;

	private String kyBdren;

	/** 快运分单费 */
	private Double kyFendanfei;

	private String kyKind;

	/** 快运派送人*/
	private String kyPsren;

	/** 快运收货人 */
	private String kyShren;

	/** 快运中介费 */
	private Double kyZongjinfei;

	/** 路由站 */
	private String luyouzhan;

	private Integer lxtjflag;

	private String orderno;

	/** 打印时间 */
	private Integer printtime;

	private Integer pszhsh;

	private String qtsx;

	private String routezhan;

	//private String rowguid;//行标识

	/** 送货通知 */
	private Integer sent;

	/**发运网点 */
	private String shhd;

	/** 收货人地址 */
	private String shhrdzh;

	/** 收货人座机电话 */
	private String shhrlxdh;

	/** 收货人名称 */
	private String shhrmch;

	/**收货人手机号 */
	private String shhryb;

	private Double shmshhf;

	/** 收货人税务证号 */
	private String shouhuorenshuihao;

	/** 收货人邮编 */
	private String shouhuorenyoubian;

	/** 送货上门费 */
	private Double shshmf;

	/** 送货上楼 */
	private Integer songhuoshanglou;

	private Integer source;

	private String swdb;

	/** 税号 */
	private String swzh;

	/** 特别说明 */
	private String tiebieshuoming;

	/**体积合计*/
	private Double tijihj; 

	/**托运人税务证号 */
	private String tuoyunrenshuihao;

	/** 托运人邮编 */
	private String tuoyunrenyoubian;

	private String tyFdmc;

	private Integer tyFdqr;

	private String tyFdsl;

	private String tyrjzshx;

	/**托运人签名 */
	private String tyrqm; 

	private boolean xfhtf;

	//private Timestamp xiadanshj;

	/** 线路 */
	private String xianlu;

	private String xiaoshoucode;

	private Integer ydly;

	private Integer ydts;

	/**运输方式 */
	private String ysfs;

	/**运输号码 */
	private String yshhm;

	/** 运单类型 */
	private Integer yundantype;

	private short ywlx;

	private String yxzt;

	/** 单证（制票）人 */
	private String zhipiao;

	/**重量合计*/
	private Integer zhlhj; 

	/** 装卸费 */
	private Double zhuangxiefei;

	/** 班组 */
	private String zhxbz;

	/** 自重 */
	private boolean zisong;

	/** 自提 */
	private boolean ziti;

	/** 作业方式 */
	private Integer zyfs;
	
	private Collection<TransportOrderDetail> detailList;
	
	/**
	 * 系统名称
	 */
	private String sysName = "麒麟";
	
	private Integer hasReceipt;
	
	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	/** 该运单最后一次的装载类型 (提干配)*/
	private String iType;
	
	public TransportOrder() {
	}

	public Collection<TransportOrderDetail> getDetailList() {
		return detailList;
	}



	public void setDetailList(Collection<TransportOrderDetail> detailList) {
		this.detailList = detailList;
	}



	public String getiType() {
		return iType;
	}

	public void setiType(String iType) {
		this.iType = iType;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}


	public void setBaozhuangfei(Double baozhuangfei) {
		this.baozhuangfei = baozhuangfei;
	}

	public void setBeginPlacename(Object beginPlacename) {
		this.beginPlacename = beginPlacename;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public void setBzf(Double bzf) {
		this.bzf = bzf;
	}

	public void setBzqr(Integer bzqr) {
		this.bzqr = bzqr;
	}

	public void setBzzht(String bzzht) {
		this.bzzht = bzzht;
	}

	public void setCangwei1(String cangwei1) {
		this.cangwei1 = cangwei1;
	}

	public void setCangwei2(String cangwei2) {
		this.cangwei2 = cangwei2;
	}

	public void setCaozuoyaoqiuDay(Double caozuoyaoqiuDay) {
		this.caozuoyaoqiuDay = caozuoyaoqiuDay;
	}

	public void setCaozuoyaoqiuPcdf(Integer caozuoyaoqiuPcdf) {
		this.caozuoyaoqiuPcdf = caozuoyaoqiuPcdf;
	}

	public void setCaozuoyaoqiuXcps(Integer caozuoyaoqiuXcps) {
		this.caozuoyaoqiuXcps = caozuoyaoqiuXcps;
	}

	public void setCaozuoyaoqiuZdsh(Integer caozuoyaoqiuZdsh) {
		this.caozuoyaoqiuZdsh = caozuoyaoqiuZdsh;
	}

	public void setChxh(String chxh) {
		this.chxh = chxh;
	}

	public void setChyrjzshx(String chyrjzshx) {
		this.chyrjzshx = chyrjzshx;
	}

	public void setCwpzhy(Integer cwpzhy) {
		this.cwpzhy = cwpzhy;
	}

	public void setDaikebaozhuang(Integer daikebaozhuang) {
		this.daikebaozhuang = daikebaozhuang;
	}

	public void setDaikezhuangxie(Integer daikezhuangxie) {
		this.daikezhuangxie = daikezhuangxie;
	}

	public void setDaodatianshu(Integer daodatianshu) {
		this.daodatianshu = daodatianshu;
	}

	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}

	public void setDashoukuan(Integer dashoukuan) {
		this.dashoukuan = dashoukuan;
	}

	public void setDhAddr(String dhAddr) {
		this.dhAddr = dhAddr;
	}

	public void setDhChengsi(String dhChengsi) {
		this.dhChengsi = dhChengsi;
	}

	public void setDhShengfen(String dhShengfen) {
		this.dhShengfen = dhShengfen;
	}

	public void setDkbzCarton(Integer dkbzCarton) {
		this.dkbzCarton = dkbzCarton;
	}

	public void setDkbzWooden(Integer dkbzWooden) {
		this.dkbzWooden = dkbzWooden;
	}

	public void setDkbzWoven(Integer dkbzWoven) {
		this.dkbzWoven = dkbzWoven;
	}

	public void setDzfk(Integer dzfk) {
		this.dzfk = dzfk;
	}

	public void setDzshhd(String dzshhd) {
		this.dzshhd = dzshhd;
	}

	public void setEdiflag(Integer ediflag) {
		this.ediflag = ediflag;
	}

	public void setEndPlacename(Object endPlacename) {
		this.endPlacename = endPlacename;
	}

	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}

	public void setFdlc(String fdlc) {
		this.fdlc = fdlc;
	}

	public void setFdnr(String fdnr) {
		this.fdnr = fdnr;
	}

	public void setFdsl(String fdsl) {
		this.fdsl = fdsl;
	}

	public void setFdts(Integer fdts) {
		this.fdts = fdts;
	}

	public void setFdyq(String fdyq) {
		this.fdyq = fdyq;
	}

	public void setFhdwdzh(String fhdwdzh) {
		this.fhdwdzh = fhdwdzh;
	}

	public void setFhdwlxdh(String fhdwlxdh) {
		this.fhdwlxdh = fhdwlxdh;
	}

	public void setFhdwmch(String fhdwmch) {
		this.fhdwmch = fhdwmch;
	}

	public void setFhdwyb(String fhdwyb) {
		this.fhdwyb = fhdwyb;
	}

	public void setFhjs(Integer fhjs) {
		this.fhjs = fhjs;
	}

	public void setFhkhhy(String fhkhhy) {
		this.fhkhhy = fhkhhy;
	}

	public void setFhr(String fhr) {
		this.fhr = fhr;
	}

	public void setFhshj(Date fhshj) {
		this.fhshj = fhshj;
	}

	

	public void setFkfsh(Integer fkfsh) {
		this.fkfsh = fkfsh;
	}

	public void setFlsj(Integer flsj) {
		this.flsj = flsj;
	}

	public void setFpmc(String fpmc) {
		this.fpmc = fpmc;
	}

	public void setFwfs(Integer fwfs) {
		this.fwfs = fwfs;
	}

	public void setFzfk(Integer fzfk) {
		this.fzfk = fzfk;
	}

	public void setFzhchyrq(Date fzhchyrq) {
		this.fzhchyrq = fzhchyrq;
	}

	public void setGsbh(String gsbh) {
		this.gsbh = gsbh;
	}

	public void setGysnamebh(String gysnamebh) {
		this.gysnamebh = gysnamebh;
	}

	public void setGyspcdbh(String gyspcdbh) {
		this.gyspcdbh = gyspcdbh;
	}

	public void setHwzht(String hwzht) {
		this.hwzht = hwzht;
	}

	public void setHyy(String hyy) {
		this.hyy = hyy;
	}

	public void setIdf(boolean idf) {
		this.idf = idf;
	}

	public void setImportflag(Integer importflag) {
		this.importflag = importflag;
	}

	public void setIsGreenchannel(Integer isGreenchannel) {
		this.isGreenchannel = isGreenchannel;
	}

	public void setIsKaiyun(short isKaiyun) {
		this.isKaiyun = isKaiyun;
	}

	public void setIsfd(Integer isfd) {
		this.isfd = isfd;
	}

	public void setIsjj(boolean isjj) {
		this.isjj = isjj;
	}

	public void setIsshsx(Integer isshsx) {
		this.isshsx = isshsx;
	}

	public void setIstb(Integer istb) {
		this.istb = istb;
	}

	public void setJsgs(String jsgs) {
		this.jsgs = jsgs;
	}

	public void setJshhj(Integer jshhj) {
		this.jshhj = jshhj;
	}

	public void setKhKind(String khKind) {
		this.khKind = khKind;
	}

	public void setKhbm(String khbm) {
		this.khbm = khbm;
	}

	public void setKhdh(String khdh) {
		this.khdh = khdh;
	}

	public void setKyBdren(String kyBdren) {
		this.kyBdren = kyBdren;
	}

	public void setKyFendanfei(Double kyFendanfei) {
		this.kyFendanfei = kyFendanfei;
	}

	public void setKyKind(String kyKind) {
		this.kyKind = kyKind;
	}

	public void setKyPsren(String kyPsren) {
		this.kyPsren = kyPsren;
	}

	public void setKyShren(String kyShren) {
		this.kyShren = kyShren;
	}

	public void setKyZongjinfei(Double kyZongjinfei) {
		this.kyZongjinfei = kyZongjinfei;
	}

	public void setLuyouzhan(String luyouzhan) {
		this.luyouzhan = luyouzhan;
	}

	public void setLxtjflag(Integer lxtjflag) {
		this.lxtjflag = lxtjflag;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public void setPrinttime(Integer printtime) {
		this.printtime = printtime;
	}

	public void setPszhsh(Integer pszhsh) {
		this.pszhsh = pszhsh;
	}

	public void setQtsx(String qtsx) {
		this.qtsx = qtsx;
	}

	public void setRoutezhan(String routezhan) {
		this.routezhan = routezhan;
	}

	public void setSent(Integer sent) {
		this.sent = sent;
	}

	public void setShhd(String shhd) {
		this.shhd = shhd;
	}

	public void setShhrdzh(String shhrdzh) {
		this.shhrdzh = shhrdzh;
	}

	public void setShhrlxdh(String shhrlxdh) {
		this.shhrlxdh = shhrlxdh;
	}

	public void setShhrmch(String shhrmch) {
		this.shhrmch = shhrmch;
	}

	public void setShhryb(String shhryb) {
		this.shhryb = shhryb;
	}

	public void setShmshhf(Double shmshhf) {
		this.shmshhf = shmshhf;
	}

	public void setShouhuorenshuihao(String shouhuorenshuihao) {
		this.shouhuorenshuihao = shouhuorenshuihao;
	}

	public void setShouhuorenyoubian(String shouhuorenyoubian) {
		this.shouhuorenyoubian = shouhuorenyoubian;
	}

	public void setShshmf(Double shshmf) {
		this.shshmf = shshmf;
	}

	public void setSonghuoshanglou(Integer songhuoshanglou) {
		this.songhuoshanglou = songhuoshanglou;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public void setSwdb(String swdb) {
		this.swdb = swdb;
	}

	public void setSwzh(String swzh) {
		this.swzh = swzh;
	}

	public void setTiebieshuoming(String tiebieshuoming) {
		this.tiebieshuoming = tiebieshuoming;
	}

	public void setTuoyunrenshuihao(String tuoyunrenshuihao) {
		this.tuoyunrenshuihao = tuoyunrenshuihao;
	}

	public void setTuoyunrenyoubian(String tuoyunrenyoubian) {
		this.tuoyunrenyoubian = tuoyunrenyoubian;
	}

	public void setTyFdmc(String tyFdmc) {
		this.tyFdmc = tyFdmc;
	}

	public void setTyFdqr(Integer tyFdqr) {
		this.tyFdqr = tyFdqr;
	}

	public void setTyFdsl(String tyFdsl) {
		this.tyFdsl = tyFdsl;
	}

	public void setTyrjzshx(String tyrjzshx) {
		this.tyrjzshx = tyrjzshx;
	}

	public void setTyrqm(String tyrqm) {
		this.tyrqm = tyrqm;
	}

	public void setXfhtf(boolean xfhtf) {
		this.xfhtf = xfhtf;
	}

	public void setXianlu(String xianlu) {
		this.xianlu = xianlu;
	}

	public void setXiaoshoucode(String xiaoshoucode) {
		this.xiaoshoucode = xiaoshoucode;
	}

	public void setYdly(Integer ydly) {
		this.ydly = ydly;
	}

	public void setYdts(Integer ydts) {
		this.ydts = ydts;
	}

	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}

	public void setYshhm(String yshhm) {
		this.yshhm = yshhm;
	}

	public void setYundantype(Integer yundantype) {
		this.yundantype = yundantype;
	}

	public void setYwlx(short ywlx) {
		this.ywlx = ywlx;
	}

	public void setYxzt(String yxzt) {
		this.yxzt = yxzt;
	}

	public void setZhipiao(String zhipiao) {
		this.zhipiao = zhipiao;
	}

	public void setZhlhj(Integer zhlhj) {
		this.zhlhj = zhlhj;
	}

	public void setZhuangxiefei(Double zhuangxiefei) {
		this.zhuangxiefei = zhuangxiefei;
	}

	public void setZhxbz(String zhxbz) {
		this.zhxbz = zhxbz;
	}

	public void setZisong(boolean zisong) {
		this.zisong = zisong;
	}

	public void setZiti(boolean ziti) {
		this.ziti = ziti;
	}

	public void setZyfs(Integer zyfs) {
		this.zyfs = zyfs;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public Double getBandanfei() {
		return bandanfei;
	}

	public Double getBaoxianfei() {
		return baoxianfei;
	}

	public Double getBaozhuangfei() {
		return baozhuangfei;
	}

	public Object getBeginPlacename() {
		return beginPlacename;
	}

	public String getBeizhu() {
		if(StringUtils.isBlank(beizhu)){
			return "无";
		}
		return beizhu;
	}

	public Double getBzf() {
		return bzf;
	}

	public Integer getBzqr() {
		return bzqr;
	}

	public String getBzzht() {
		return bzzht;
	}

	public String getCangwei1() {
		return cangwei1;
	}

	public String getCangwei2() {
		return cangwei2;
	}

	public Double getCaozuoyaoqiuDay() {
		return caozuoyaoqiuDay;
	}

	public Integer getCaozuoyaoqiuPcdf() {
		return caozuoyaoqiuPcdf;
	}

	public Integer getCaozuoyaoqiuXcps() {
		return caozuoyaoqiuXcps;
	}

	public Integer getCaozuoyaoqiuZdsh() {
		return caozuoyaoqiuZdsh;
	}

	public String getChxh() {
		return chxh;
	}

	public String getChyrjzshx() {
		return chyrjzshx;
	}

	public Integer getCwpzhy() {
		return cwpzhy;
	}

	public Integer getDaikebaozhuang() {
		return daikebaozhuang;
	}

	public Integer getDaikezhuangxie() {
		return daikezhuangxie;
	}

	public Integer getDaodatianshu() {
		return daodatianshu;
	}

	public String getDaozhan() {
		return daozhan;
	}

	public Integer getDashoukuan() {
		return dashoukuan;
	}

	public Double getDashoukuanYuan() {
		return dashoukuanYuan;
	}

	public void setDashoukuanYuan(Double dashoukuanYuan) {
		this.dashoukuanYuan = dashoukuanYuan;
	}

	public void setBandanfei(Double bandanfei) {
		this.bandanfei = bandanfei;
	}

	public void setBaoxianfei(Double baoxianfei) {
		this.baoxianfei = baoxianfei;
	}

	public String getDhAddr() {
		return dhAddr;
	}

	public String getDhChengsi() {
		return dhChengsi;
	}

	public String getDhShengfen() {
		return dhShengfen;
	}

	public Integer getDkbzCarton() {
		return dkbzCarton;
	}

	public Integer getDkbzWooden() {
		return dkbzWooden;
	}

	public Integer getDkbzWoven() {
		return dkbzWoven;
	}

	public Integer getDzfk() {
		return dzfk;
	}

	public String getDzshhd() {
		return dzshhd;
	}

	public Integer getEdiflag() {
		return ediflag;
	}

	public Object getEndPlacename() {
		return endPlacename;
	}

	public String getFazhan() {
		return fazhan;
	}

	public String getFdlc() {
		return fdlc;
	}

	public String getFdnr() {
		return fdnr;
	}

	public String getFdsl() {
		return fdsl;
	}

	public Integer getFdts() {
		return fdts;
	}

	public String getFdyq() {
		return fdyq;
	}

	public String getFhdwdzh() {
		return fhdwdzh;
	}

	public String getFhdwlxdh() {
		return fhdwlxdh;
	}

	public String getFhdwmch() {
		return fhdwmch;
	}

	public String getFhdwyb() {
		return fhdwyb;
	}

	public Integer getFhjs() {
		return fhjs;
	}

	public String getFhkhhy() {
		return fhkhhy;
	}

	public String getFhr() {
		return fhr;
	}

	public Date getFhshj() {
		return fhshj;
	}

	public Double getFhtiji() {
		return fhtiji;
	}

	public void setFhtiji(Double fhtiji) {
		this.fhtiji = fhtiji;
	}

	public Double getFhzhl() {
		return fhzhl;
	}

	public void setFhzhl(Double fhzhl) {
		this.fhzhl = fhzhl;
	}

	public Integer getFkfsh() {
		return fkfsh;
	}

	public Integer getFlsj() {
		return flsj;
	}

	public String getFpmc() {
		return fpmc;
	}

	public Integer getFwfs() {
		return fwfs;
	}

	public Integer getFzfk() {
		return fzfk;
	}

	public Date getFzhchyrq() {
		return fzhchyrq;
	}

	public String getGsbh() {
		return gsbh;
	}

	public String getGysnamebh() {
		return gysnamebh;
	}

	public String getGyspcdbh() {
		return gyspcdbh;
	}

	public String getHwzht() {
		return hwzht;
	}

	public String getHyy() {
		return hyy;
	}

	public boolean isIdf() {
		return idf;
	}

	public Integer getImportflag() {
		return importflag;
	}

	public Integer getIsGreenchannel() {
		return isGreenchannel;
	}

	public short getIsKaiyun() {
		return isKaiyun;
	}

	public Integer getIsfd() {
		return isfd;
	}

	public boolean isIsjj() {
		return isjj;
	}

	public Integer getIsshsx() {
		return isshsx;
	}

	public Integer getIstb() {
		return istb;
	}

	public String getJsgs() {
		return jsgs;
	}

	public Integer getJshhj() {
		return jshhj;
	}

	public String getKhKind() {
		return khKind;
	}

	public String getKhbm() {
		return khbm;
	}

	public String getKhdh() {
		return khdh;
	}

	public String getKyBdren() {
		return kyBdren;
	}

	public Double getKyFendanfei() {
		return kyFendanfei;
	}

	public String getKyKind() {
		return kyKind;
	}

	public String getKyPsren() {
		return kyPsren;
	}

	public String getKyShren() {
		return kyShren;
	}

	public Double getKyZongjinfei() {
		return kyZongjinfei;
	}

	public String getLuyouzhan() {
		return luyouzhan;
	}

	public Integer getLxtjflag() {
		return lxtjflag;
	}

	public String getOrderno() {
		return orderno;
	}

	public Integer getPrinttime() {
		return printtime;
	}

	public Integer getPszhsh() {
		return pszhsh;
	}

	public String getQtsx() {
		return qtsx;
	}

	public String getRoutezhan() {
		return routezhan;
	}

	public Integer getSent() {
		return sent;
	}

	public String getShhd() {
		return shhd;
	}

	public String getShhrdzh() {
		return shhrdzh;
	}

	public String getShhrlxdh() {
		return shhrlxdh;
	}

	public String getShhrmch() {
		return shhrmch;
	}

	public String getShhryb() {
		return shhryb;
	}

	public Double getShmshhf() {
		return shmshhf;
	}

	public String getShouhuorenshuihao() {
		return shouhuorenshuihao;
	}

	public String getShouhuorenyoubian() {
		return shouhuorenyoubian;
	}

	public Double getShshmf() {
		return shshmf;
	}

	public Integer getSonghuoshanglou() {
		return songhuoshanglou;
	}

	public Integer getSource() {
		return source;
	}

	public String getSwdb() {
		return swdb;
	}

	public String getSwzh() {
		return swzh;
	}

	public String getTiebieshuoming() {
		return tiebieshuoming;
	}

	public Double getTijihj() {
		return tijihj;
	}

	public void setTijihj(Double tijihj) {
		this.tijihj = tijihj;
	}

	public String getTuoyunrenshuihao() {
		return tuoyunrenshuihao;
	}

	public String getTuoyunrenyoubian() {
		return tuoyunrenyoubian;
	}

	public String getTyFdmc() {
		return tyFdmc;
	}

	public Integer getTyFdqr() {
		return tyFdqr;
	}

	public String getTyFdsl() {
		return tyFdsl;
	}

	public String getTyrjzshx() {
		return tyrjzshx;
	}

	public String getTyrqm() {
		return tyrqm;
	}

	public boolean isXfhtf() {
		return xfhtf;
	}

	public String getXianlu() {
		return xianlu;
	}

	public String getXiaoshoucode() {
		return xiaoshoucode;
	}

	public Integer getYdly() {
		return ydly;
	}

	public Integer getYdts() {
		return ydts;
	}

	public String getYsfs() {
		return ysfs;
	}

	public String getYshhm() {
		return yshhm;
	}

	public Integer getYundantype() {
		return yundantype;
	}

	public short getYwlx() {
		return ywlx;
	}

	public String getYxzt() {
		return yxzt;
	}

	public String getZhipiao() {
		return zhipiao;
	}

	public Integer getZhlhj() {
		return zhlhj;
	}

	public Double getZhuangxiefei() {
		return zhuangxiefei;
	}

	public String getZhxbz() {
		return zhxbz;
	}

	public boolean isZisong() {
		return zisong;
	}

	public boolean isZiti() {
		return ziti;
	}

	public Integer getZyfs() {
		return zyfs;
	}

	public Integer getHasReceipt() {
		return hasReceipt;
	}

	public void setHasReceipt(Integer hasReceipt) {
		this.hasReceipt = hasReceipt;
	}
	
	

}