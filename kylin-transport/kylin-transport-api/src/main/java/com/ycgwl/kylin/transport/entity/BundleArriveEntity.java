package com.ycgwl.kylin.transport.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * 装载到货查询数据对象
 */
public class BundleArriveEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Double tijiCalc(){
		if(this.getJffsHwydxz()==0){
			return this.getZhl();
		}else if(this.getJffsHwydxz()==1){
			return 0.0;
		}else if(this.getTiji()<=0){
			return this.getZhl();
		}else{
			return this.getZhl()/this.getTiji()>=0.3?zhl:0; 
		}
	}
	
	public Double zhlCalc(){
		if(this.getJffsHwydxz()==1){
			return this.getTiji();
		}else if(this.getJffsHwydxz()==0){
			return 0.0;
		}else if(this.getTiji()<=0){
			return 0.0;
		}else{
			return this.getZhl()/this.getTiji()<0.3?tiji:0; 
		}
	}
	
	/**
	 * 装载清单表字段
	 */
	private String daozhan;
	private String fchrq;
	private String zhchrq;
	private String chxh;
	private Integer jianshu;
	private String pinming;
	private String shhrmch;
	private Double tiji;
	private Double zhl;
	private Integer ziti;
	private String fazhan;
	private String beizhu;
	private Integer ydxzh;
	private String xh;
	private Integer xuhao;
	private String zhipiao;
	private String zhipiao2;
	private String ydbhid;
	private String hyy;
	private Integer ydzh;
	private Integer yiti;
	private String grid;
	private String lrsj;
	private String dhgrid;  
	private String dhsj;
	private String fzshhd;
	private String dzshhd;
	private String ysfs;
	private int isKaiyun;
	private String orginalCompanyName;
	
	/**
	 * 货物运单表字段
	 */
	private String yshhm;
	private String fhdwmch;
	private String daozhanHwyd;
	private String ysfsHwyd;
	private String fazhanHwyd;
	private String fhshj;
	private String isfd;
	private String ywlx;
	private String pszhsh;
	private String ydts;
	private String shhrdzh;
	private String shhd;
	private String dzshhdHwyd;
	private String yxztHwyd;
	private String shhrlxdh;
	private String fhdwyb;
	private String shhryb;
	
	/**
	 * 视图字段
	 */
	/*** 合计金额*/
	private String vhjje;
	
	/*** 货到付款*/
	private String vhdfk;
	
	/*** 代收货款*/
	private String vdsk;
	
	/**
	 * 签收表字段
	 */
	/*** 录入时间*/
	private String lrtime;
	
	/**
	 * 货物细则号字段
	 */
	private String jianshuHwydxz;
	
	private String tbjeHwydxz;
	
	private int jffsHwydxz;
	
	/**
	 * 是否是绿色通道
	 */
	private int isGreenChannel;
	
	private Integer tzfhzt;
	
	private int isTiShi=0;
	
	private Double tijiCalc;
	
	private Double zhlCalc;

	public String getFhdwyb() {
		return fhdwyb;
	}

	public void setFhdwyb(String fhdwyb) {
		this.fhdwyb = fhdwyb;
	}

	public Double getTijiCalc() {
		return this.tijiCalc;
	}
	
	public void setTijiCalc(Double tijiCalc) {
		this.tijiCalc = tijiCalc;
	}

	public Double getZhlCalc() {
		return this.zhlCalc;
	}

	public void setZhlCalc(Double zhlCalc) {
		this.zhlCalc = zhlCalc;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  
	public String getFhshj() {
		return fhshj;
	}

	public void setFhshj(String fhshj) {
		this.fhshj = fhshj;
	}

	public String getIsfd() {
		return isfd;
	}

	public void setIsfd(String isfd) {
		this.isfd = isfd;
	}

	public String getYwlx() {
		return ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	public String getPszhsh() {
		return pszhsh;
	}

	public void setPszhsh(String pszhsh) {
		this.pszhsh = pszhsh;
	}

	public String getYdts() {
		return ydts;
	}

	public void setYdts(String ydts) {
		this.ydts = ydts;
	}

	public String getShhrdzh() {
		return shhrdzh;
	}

	public void setShhrdzh(String shhrdzh) {
		this.shhrdzh = shhrdzh;
	}

	public String getShhd() {
		return shhd;
	}

	public void setShhd(String shhd) {
		this.shhd = shhd;
	}

	public String getDaozhan() {
		return daozhan;
	}

	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}

	public String getFchrq() {
		return fchrq;
	}

	public void setFchrq(String fchrq) {
		this.fchrq = fchrq;
	}

	public String getZhchrq() {
		return zhchrq;
	}

	public void setZhchrq(String zhchrq) {
		this.zhchrq = zhchrq;
	}

	public String getLrsj() {
		return lrsj;
	}

	public void setLrsj(String lrsj) {
		this.lrsj = lrsj;
	}

	public String getDhsj() {
		return dhsj;
	}

	public void setDhsj(String dhsj) {
		this.dhsj = dhsj;
	}

	public String getLrtime() {
		return lrtime;
	}

	public void setLrtime(String lrtime) {
		this.lrtime = lrtime;
	}

	public String getChxh() {
		return chxh;
	}

	public void setChxh(String chxh) {
		this.chxh = chxh;
	}

	public Integer getJianshu() {
		return jianshu;
	}

	public void setJianshu(Integer jianshu) {
		this.jianshu = jianshu;
	}

	public String getPinming() {
		return pinming;
	}

	public void setPinming(String pinming) {
		this.pinming = pinming;
	}

	public String getShhrlxdh() {
		return shhrlxdh;
	}

	public void setShhrlxdh(String shhrlxdh) {
		this.shhrlxdh = shhrlxdh;
	}

	public String getShhrmch() {
		return shhrmch;
	}

	public void setShhrmch(String shhrmch) {
		this.shhrmch = shhrmch;
	}

	public Double getTiji() {
		return tiji;
	}

	public void setTiji(Double tiji) {
		this.tiji = tiji;
	}

	public Double getZhl() {
		return zhl;
	}

	public void setZhl(Double zhl) {
		this.zhl = zhl;
	}

	public Integer getZiti() {
		return ziti;
	}

	public void setZiti(Integer ziti) {
		this.ziti = ziti;
	}

	public String getFazhan() {
		return fazhan;
	}

	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public Integer getYdxzh() {
		return ydxzh;
	}

	public void setYdxzh(Integer ydxzh) {
		this.ydxzh = ydxzh;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public Integer getXuhao() {
		return xuhao;
	}

	public void setXuhao(Integer xuhao) {
		this.xuhao = xuhao;
	}

	public String getZhipiao() {
		return zhipiao;
	}

	public void setZhipiao(String zhipiao) {
		this.zhipiao = zhipiao;
	}

	public String getZhipiao2() {
		return zhipiao2;
	}

	public void setZhipiao2(String zhipiao2) {
		this.zhipiao2 = zhipiao2;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public String getHyy() {
		return hyy;
	}

	public void setHyy(String hyy) {
		this.hyy = hyy;
	}

	public Integer getYdzh() {
		return ydzh;
	}

	public void setYdzh(Integer ydzh) {
		this.ydzh = ydzh;
	}

	public Integer getYiti() {
		return yiti;
	}

	public void setYiti(Integer yiti) {
		this.yiti = yiti;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getDhgrid() {
		return dhgrid;
	}

	public void setDhgrid(String dhgrid) {
		this.dhgrid = dhgrid;
	}

	

	public String getFzshhd() {
		return fzshhd;
	}

	public void setFzshhd(String fzshhd) {
		this.fzshhd = fzshhd;
	}

	public String getDzshhd() {
		return dzshhd;
	}

	public void setDzshhd(String dzshhd) {
		this.dzshhd = dzshhd;
	}

	public String getYsfs() {
		return ysfs;
	}

	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}

	public int getIsKaiyun() {
		return isKaiyun;
	}

	public void setIsKaiyun(int isKaiyun) {
		this.isKaiyun = isKaiyun;
	}

	public String getYshhm() {
		return yshhm;
	}

	public void setYshhm(String yshhm) {
		this.yshhm = yshhm;
	}

	public String getFhdwmch() {
		return fhdwmch;
	}

	public void setFhdwmch(String fhdwmch) {
		this.fhdwmch = fhdwmch;
	}

	public String getDaozhanHwyd() {
		return daozhanHwyd;
	}

	public void setDaozhanHwyd(String daozhanHwyd) {
		this.daozhanHwyd = daozhanHwyd;
	}

	public String getYsfsHwyd() {
		return ysfsHwyd;
	}

	public void setYsfsHwyd(String ysfsHwyd) {
		this.ysfsHwyd = ysfsHwyd;
	}

	public String getFazhanHwyd() {
		return fazhanHwyd;
	}

	public void setFazhanHwyd(String fazhanHwyd) {
		this.fazhanHwyd = fazhanHwyd;
	}
	
	public String getDzshhdHwyd() {
		return dzshhdHwyd;
	}

	public void setDzshhdHwyd(String dzshhdHwyd) {
		this.dzshhdHwyd = dzshhdHwyd;
	}

	public String getYxztHwyd() {
		return yxztHwyd;
	}

	public void setYxztHwyd(String yxztHwyd) {
		this.yxztHwyd = yxztHwyd;
	}

	public String getVhjje() {
		return vhjje;
	}

	public void setVhjje(String vhjje) {
		this.vhjje = vhjje;
	}

	public String getVhdfk() {
		return vhdfk;
	}

	public void setVhdfk(String vhdfk) {
		this.vhdfk = vhdfk;
	}

	public String getVdsk() {
		return vdsk;
	}

	public void setVdsk(String vdsk) {
		this.vdsk = vdsk;
	}

	
	public String getJianshuHwydxz() {
		return jianshuHwydxz;
	}

	public void setJianshuHwydxz(String jianshuHwydxz) {
		this.jianshuHwydxz = jianshuHwydxz;
	}

	public String getTbjeHwydxz() {
		return tbjeHwydxz;
	}

	public void setTbjeHwydxz(String tbjeHwydxz) {
		this.tbjeHwydxz = tbjeHwydxz;
	}

	public int getJffsHwydxz() {
		return jffsHwydxz;
	}

	public void setJffsHwydxz(int jffsHwydxz) {
		this.jffsHwydxz = jffsHwydxz;
	}


	public int getIsGreenChannel() {
		return isGreenChannel;
	}

	public void setIsGreenChannel(int isGreenChannel) {
		this.isGreenChannel = isGreenChannel;
	}

	public Integer getTzfhzt() {
		return tzfhzt;
	}

	public void setTzfhzt(Integer tzfhzt) {
		this.tzfhzt = tzfhzt;
	}

	public int getIsTiShi() {
		return isTiShi;
	}

	public void setIsTiShi(int isTiShi) {
		this.isTiShi = isTiShi;
	}

	public String getShhryb() {
		return shhryb;
	}

	public void setShhryb(String shhryb) {
		this.shhryb = shhryb;
	}

	public String getOrginalCompanyName() {
		return orginalCompanyName;
	}

	public void setOrginalCompanyName(String orginalCompanyName) {
		this.orginalCompanyName = orginalCompanyName;
	}
}
