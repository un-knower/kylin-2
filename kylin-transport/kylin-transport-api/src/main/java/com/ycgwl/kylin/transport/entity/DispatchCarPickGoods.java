package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 派车取货表(T_CAR_IN)
 *
 */
public class DispatchCarPickGoods implements Serializable{
	
	/*** 派车单号(id) */
	private Integer id;

	/*** 公司编号(gsbh) */
	private String gsid;

	/*** 公司名称(gs) */
	private String gs;

	/*** 发运网点(yywd) */
	private String yywd;

	/*** 销售员(kfy) */
	private String kfy;

	/*** 取货联系人(lxr) */
	private String lxr;

	/*** 托运地址(FHDWDZH) */
	private String fhdwdzh;

	/*** 街道城镇dh_addr */
	private String dhAddr;
	
	private String tjyhsr;
	
	private Date tjyjstime;

	private Integer pcyes;
	private String kfgrid;
	private String lxdh;
	private String fhr;
	private String qhadd;
	private Integer sffd;
	private String sfdzx;
	private Integer sfdyd;
	private Integer sfsyf;
	private String xdtime;
	private String jhqhtime;
	private Date jhqhtimeDate;
	private String yqfhtime;
	private String khsm;
	private String ddpcdd;
	private String ddpctime;
	private Date ddpctimeDate;
	
	/**起始地*/
	private String ddqsd;
	/**取货地*/
	private String ddqhd;
	private String ddcomm;
	private String tjyhsrgrid;
	private String ddpcddgrid;
	private String khbm;
	/**派送指示*/
	private Integer pszhsh;
	/**发票*/
	private Integer isXuYaoFaPiao;
	/**托运人税号*/
	private String tuoyunrenshuihao;
	/**发送路径编号*/
	private String fasonglujingBianhao;
	/**到站网点*/
	private String hwdaozhanWangDian;
	private String fhdwlxdh;
	/** 托运手机号码*/
	private String fhdwlxdhShouji;
	/** 收货人名称*/
	private String shhrmch;
	/** 省市*/
	private String dhShengfen;
	/** 市区县*/
	private String dhChengsi;
	/** 地址*/
	private String shhrdzh;
	/** 座机电话*/
	private String shhrlxdh;
	/** 收货人联系电话*/
	private String shhryb;
	/** 服务方式*/
	private Integer fwfs;
	/** 付款方式*/
	private Integer fuKuanFangShi;
	/** 是否返单*/
	private Integer isfd;
	/** 代客包装*/
	private Integer daikebaozhuang;
	/** 操作要求*/
	private Integer caozuoyaoqiu;
	/** 作业方式*/
	private Integer zyfs;
	/** 返单名称*/
	private String fdnr;
	/** 返单联次*/
	private String fdlc;
	/** 返单份数*/
	private String fdsl;
	/** 代收款*/
	private Double dashoukuanYuan;
	/** 送货上楼*/
	private Integer songhuoshanglou;
	/** 代客装卸*/
	private Integer daikezhuangxie;
	private String ver;
	private String fpmc;
	private String swdb;
	/** 运输方式*/
	private String ysfs;
	private String hwdaozhan;
	/** 业务类型*/
	private Integer ywlx;
	/**发货省市*/
	private String fhShengFen;
	private Integer pcshlc;
	private Integer min;
	private Integer max;
	/**提成趟次*/
	private Integer tangci;
	/**提成目的地*/
	private String pcmdd;
	/**取货里程*/
	private String lc;
	/**发货城市*/
	private String fhChengShi;
	/**订单号*/
	private String orderNo;
	
	private Long js;
	
	private Double tj;
	
	private Double zl;
	
	private String sjcomm;
	
	private String tjyshr;
	
	private String tjybz;
	
	private String sysName = "麒麟";
	
	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public Date getJhqhtimeDate() {
		return jhqhtimeDate;
	}

	public void setJhqhtimeDate(Date jhqhtimeDate) {
		this.jhqhtimeDate = jhqhtimeDate;
	}

	public Date getDdpctimeDate() {
		return ddpctimeDate;
	}

	public void setDdpctimeDate(Date ddpctimeDate) {
		this.ddpctimeDate = ddpctimeDate;
	}

	public String getTjybz() {
		return tjybz;
	}

	public void setTjybz(String tjybz) {
		this.tjybz = tjybz;
	}

	public String getTjyshr() {
		return tjyshr;
	}

	public void setTjyshr(String tjyshr) {
		this.tjyshr = tjyshr;
	}

	public String getSjcomm() {
		return sjcomm;
	}

	public void setSjcomm(String sjcomm) {
		this.sjcomm = sjcomm;
	}

	public String getDdpctime() {
		return ddpctime;
	}

	public void setDdpctime(String ddpctime) {
		this.ddpctime = ddpctime;
	}

	public String getTjyhsr() {
		return tjyhsr;
	}

	public void setTjyhsr(String tjyhsr) {
		this.tjyhsr = tjyhsr;
	}

	public Date getTjyjstime() {
		return tjyjstime;
	}

	public void setTjyjstime(Date tjyjstime) {
		this.tjyjstime = tjyjstime;
	}

	public Long getJs() {
		return js;
	}

	public void setJs(Long js) {
		this.js = js;
	}

	public Double getTj() {
		return tj;
	}

	public void setTj(Double tj) {
		this.tj = tj;
	}

	public Double getZl() {
		return zl;
	}

	public void setZl(Double zl) {
		this.zl = zl;
	}

	public void setFhShengFen(String fhShengFen) {
		this.fhShengFen = fhShengFen;
	}

	public String getFhChengShi() {
		return fhChengShi;
	}

	public void setFhChengShi(String fhChengShi) {
		this.fhChengShi = fhChengShi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGsid() {
		return gsid;
	}

	public void setGsid(String gsid) {
		this.gsid = gsid;
	}

	public String getGs() {
		return gs;
	}

	public void setGs(String gs) {
		this.gs = gs;
	}

	public String getYywd() {
		return yywd;
	}

	public void setYywd(String yywd) {
		this.yywd = yywd;
	}

	public String getKfy() {
		return kfy;
	}

	public void setKfy(String kfy) {
		this.kfy = kfy;
	}

	public String getLxr() {
		return lxr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public String getFhdwdzh() {
		return fhdwdzh;
	}

	public void setFhdwdzh(String fhdwdzh) {
		this.fhdwdzh = fhdwdzh;
	}

	public String getDhAddr() {
		return dhAddr;
	}

	public void setDhAddr(String dhAddr) {
		this.dhAddr = dhAddr;
	}

	public Integer getPcyes() {
		return pcyes;
	}

	public void setPcyes(Integer pcyes) {
		this.pcyes = pcyes;
	}

	public String getKfgrid() {
		return kfgrid;
	}

	public void setKfgrid(String kfgrid) {
		this.kfgrid = kfgrid;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getFhr() {
		return fhr;
	}

	public void setFhr(String fhr) {
		this.fhr = fhr;
	}

	public String getQhadd() {
		return qhadd;
	}

	public void setQhadd(String qhadd) {
		this.qhadd = qhadd;
	}

	public Integer getSffd() {
		return sffd;
	}

	public void setSffd(Integer sffd) {
		this.sffd = sffd;
	}

	public String getSfdzx() {
		return sfdzx;
	}

	public void setSfdzx(String sfdzx) {
		this.sfdzx = sfdzx;
	}

	public Integer getSfdyd() {
		return sfdyd;
	}

	public void setSfdyd(Integer sfdyd) {
		this.sfdyd = sfdyd;
	}

	public Integer getSfsyf() {
		return sfsyf;
	}

	public void setSfsyf(Integer sfsyf) {
		this.sfsyf = sfsyf;
	}

	public String getXdtime() {
		return xdtime;
	}

	public void setXdtime(String xdtime) {
		this.xdtime = xdtime;
	}

	public String getJhqhtime() {
		return jhqhtime;
	}

	public void setJhqhtime(String jhqhtime) {
		this.jhqhtime = jhqhtime;
	}

	public String getYqfhtime() {
		return yqfhtime;
	}

	public void setYqfhtime(String yqfhtime) {
		this.yqfhtime = yqfhtime;
	}

	public String getKhsm() {
		return khsm;
	}

	public void setKhsm(String khsm) {
		this.khsm = khsm;
	}

	public String getDdpcdd() {
		return ddpcdd;
	}

	public void setDdpcdd(String ddpcdd) {
		this.ddpcdd = ddpcdd;
	}

	public String getDdqsd() {
		return ddqsd;
	}

	public void setDdqsd(String ddqsd) {
		this.ddqsd = ddqsd;
	}

	public String getDdqhd() {
		return ddqhd;
	}

	public void setDdqhd(String ddqhd) {
		this.ddqhd = ddqhd;
	}

	public String getDdcomm() {
		return ddcomm;
	}

	public void setDdcomm(String ddcomm) {
		this.ddcomm = ddcomm;
	}

	public String getTjyhsrgrid() {
		return tjyhsrgrid;
	}

	public void setTjyhsrgrid(String tjyhsrgrid) {
		this.tjyhsrgrid = tjyhsrgrid;
	}

	public String getDdpcddgrid() {
		return ddpcddgrid;
	}

	public void setDdpcddgrid(String ddpcddgrid) {
		this.ddpcddgrid = ddpcddgrid;
	}

	public String getKhbm() {
		return khbm;
	}

	public void setKhbm(String khbm) {
		this.khbm = khbm;
	}

	public Integer getPszhsh() {
		return pszhsh;
	}

	public void setPszhsh(Integer pszhsh) {
		this.pszhsh = pszhsh;
	}

	public Integer getIsXuYaoFaPiao() {
		return isXuYaoFaPiao;
	}

	public void setIsXuYaoFaPiao(Integer isXuYaoFaPiao) {
		this.isXuYaoFaPiao = isXuYaoFaPiao;
	}

	public String getTuoyunrenshuihao() {
		return tuoyunrenshuihao;
	}

	public void setTuoyunrenshuihao(String tuoyunrenshuihao) {
		this.tuoyunrenshuihao = tuoyunrenshuihao;
	}

	public String getFasonglujingBianhao() {
		return fasonglujingBianhao;
	}

	public void setFasonglujingBianhao(String fasonglujingBianhao) {
		this.fasonglujingBianhao = fasonglujingBianhao;
	}

	public String getHwdaozhanWangDian() {
		return hwdaozhanWangDian;
	}

	public void setHwdaozhanWangDian(String hwdaozhanWangDian) {
		this.hwdaozhanWangDian = hwdaozhanWangDian;
	}

	public String getFhdwlxdh() {
		return fhdwlxdh;
	}

	public void setFhdwlxdh(String fhdwlxdh) {
		this.fhdwlxdh = fhdwlxdh;
	}

	public String getFhdwlxdhShouji() {
		return fhdwlxdhShouji;
	}

	public void setFhdwlxdhShouji(String fhdwlxdhShouji) {
		this.fhdwlxdhShouji = fhdwlxdhShouji;
	}

	public String getShhrmch() {
		return shhrmch;
	}

	public void setShhrmch(String shhrmch) {
		this.shhrmch = shhrmch;
	}

	public String getDhShengfen() {
		return dhShengfen;
	}

	public void setDhShengfen(String dhShengfen) {
		this.dhShengfen = dhShengfen;
	}

	public String getDhChengsi() {
		return dhChengsi;
	}

	public void setDhChengsi(String dhChengsi) {
		this.dhChengsi = dhChengsi;
	}

	public String getShhrdzh() {
		return shhrdzh;
	}

	public void setShhrdzh(String shhrdzh) {
		this.shhrdzh = shhrdzh;
	}

	public String getShhrlxdh() {
		return shhrlxdh;
	}

	public void setShhrlxdh(String shhrlxdh) {
		this.shhrlxdh = shhrlxdh;
	}

	public String getShhryb() {
		return shhryb;
	}

	public void setShhryb(String shhryb) {
		this.shhryb = shhryb;
	}

	public Integer getFuKuanFangShi() {
		return fuKuanFangShi;
	}

	public void setFuKuanFangShi(Integer fuKuanFangShi) {
		this.fuKuanFangShi = fuKuanFangShi;
	}

	public void setFwfs(Integer fwfs) {
		this.fwfs = fwfs;
	}

	public Integer getIsfd() {
		return isfd;
	}

	public void setIsfd(Integer isfd) {
		this.isfd = isfd;
	}

	public Integer getDaikebaozhuang() {
		return daikebaozhuang;
	}

	public void setDaikebaozhuang(Integer daikebaozhuang) {
		this.daikebaozhuang = daikebaozhuang;
	}

	public Integer getCaozuoyaoqiu() {
		return caozuoyaoqiu;
	}

	public void setCaozuoyaoqiu(Integer caozuoyaoqiu) {
		this.caozuoyaoqiu = caozuoyaoqiu;
	}

	public Integer getZyfs() {
		return zyfs;
	}

	public void setZyfs(Integer zyfs) {
		this.zyfs = zyfs;
	}

	public String getFdnr() {
		return fdnr;
	}

	public void setFdnr(String fdnr) {
		this.fdnr = fdnr;
	}

	public String getFdlc() {
		return fdlc;
	}

	public void setFdlc(String fdlc) {
		this.fdlc = fdlc;
	}

	public String getFdsl() {
		return fdsl;
	}

	public void setFdsl(String fdsl) {
		this.fdsl = fdsl;
	}

	public Double getDashoukuanYuan() {
		return dashoukuanYuan;
	}

	public void setDashoukuanYuan(Double dashoukuanYuan) {
		this.dashoukuanYuan = dashoukuanYuan;
	}

	public Integer getSonghuoshanglou() {
		return songhuoshanglou;
	}

	public void setSonghuoshanglou(Integer songhuoshanglou) {
		this.songhuoshanglou = songhuoshanglou;
	}

	public Integer getDaikezhuangxie() {
		return daikezhuangxie;
	}

	public void setDaikezhuangxie(Integer daikezhuangxie) {
		this.daikezhuangxie = daikezhuangxie;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getFpmc() {
		return fpmc;
	}

	public void setFpmc(String fpmc) {
		this.fpmc = fpmc;
	}

	public String getSwdb() {
		return swdb;
	}

	public void setSwdb(String swdb) {
		this.swdb = swdb;
	}

	public String getYsfs() {
		return ysfs;
	}

	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}

	public String getHwdaozhan() {
		return hwdaozhan;
	}

	public void setHwdaozhan(String hwdaozhan) {
		this.hwdaozhan = hwdaozhan;
	}

	public Integer getYwlx() {
		return ywlx;
	}

	public void setYwlx(Integer ywlx) {
		this.ywlx = ywlx;
	}

	public String getFhShengFen() {
		return fhShengFen;
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

	public String getLc() {
		return lc;
	}

	public void setLc(String lc) {
		this.lc = lc;
	}


	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getFwfs() {
		return fwfs;
	}
	

}
