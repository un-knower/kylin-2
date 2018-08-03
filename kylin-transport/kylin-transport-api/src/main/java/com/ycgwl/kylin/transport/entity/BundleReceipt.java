package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * TODO Add description
 * 装载清单
 * <p>
 * @developer Create by <a href="mailto:110686@ycgwl.com">wanyj</a> at 2017年6月2日
 */
public class BundleReceipt extends BaseEntity  {
	
	private static final long serialVersionUID = 3915334273224943208L;

	private Integer xuhao;					//序号		xuhao
	private String beizhu;					//记事		beizhu
	private String chxh;					//车厢号		CHXH
	private String clOwner;					//不明确其含义 	cl_owner 	承运方式
	private String company;					//公司		company
	private String daozhan;					//装车到站	DAOZHAN
	private String dhgrid;					//到货录入人	dhgrid
	private Date dhsj;						//到货时间	dhsj
	private String dzshhd;					//到站网点	dzshhd
	private BigDecimal elseCost;			//其他成本	else_cost
	private String fazhan;					//装车发站	FAZHAN
	private Date fchrq;						//发车日期	FCHRQ
	private String fhdwmch;					//发货单位	fhdwmch
	private Date fhshj;						//发货时间	fhshj
	private String fzshhd;					//发站网点	fzshhd
	private String grid;					//清单录入人	grid
	private String hyy;						//货运人		hyy
	private Integer iType;					//清单类型	i_type
	private Short isKaiyun;					//快运标识	is_kaiyun
	private Integer jianshu;				//件数		JIANSHU
	private String khdh;					//客户单号	khdh
	private String listNo;					//不明确其含义 	list_no
	private Date lrsj;						//清单录入时间	lrsj
	private String pinming;					//品名		PINMING
	private BigDecimal qdCost;				//成本		qd_cost
//	private String rowguid;					//行标识		rowguid
	private Integer sent;					//送货通知	sent
	private String shhrlxdh;				//收货人座机	SHHRLXDH
	private String shhrmch;					//收货人名称	SHHRMCH
	private Integer sourceflag;				//不明确其含义 	sourceflag
	private BigDecimal tiji;				//体积		TIJI
	private String wxConName;				//外线联系人	wx_con_name
	private String wxItem;					//外线公司编号	wx_item
	private String wxName;					//外线公司名称	wx_name
	private String wxTel;					//外线联系电话	wx_tel
	private String wxId;					//外线单号
	private String xh;						//型号		xh
	private String xiechechexin;			//不明确其含义	xiechechexin
	private String xiechegrid;				//卸车人		xiechegrid
	private Date xiechesj;					//卸车时间	xiechesj
	private String ydbhid;					//运单编号	YDBHID
	private Integer ydxzh;					//运单细则好	ydxzh
	private Integer ydzh;					//是否到站	ydzh
	private Integer yiti;					//已提（标识）	yiti
	private Date yjddshj;					//预计到达时间	yjddshj
	private String ysfs;					//运输方式	ysfs
	private Integer yxieche;				//不明确其含义  	yxieche
	private Date zhchrq;					//装车日期	ZHCHRQ
	private String zhipiao;					//制票人		zhipiao
	private String zhipiao2;				//制票人2		zhipiao2
	private BigDecimal zhl;					//重量		ZHL
	private Boolean ziti;					//自提		ZITI
	private String driverName;				//司机姓名	driver_name
	private String driverTel;				//司机电话 	driver_tel
	
	private String ydbeginplace;		//运单的起始地
	private String ydendplace;			//运单的目的地
	
	private String transferFlag;			//中转/不中转
	private String sysName ;
	private String yslx;					//整车/零担
	private Integer newbill=0;
	private Integer autoArrive = 0;
	private String parentXuhao;
	
	public String getParentXuhao() {
		return parentXuhao;
	}

	public void setParentXuhao(String parentXuhao) {
		this.parentXuhao = parentXuhao;
	}

	public Integer getAutoArrive() {
		return autoArrive;
	}

	public void setAutoArrive(Integer autoArrive) {
		this.autoArrive = autoArrive;
	}

	public Integer getNewbill() {
		return newbill;
	}

	public void setNewbill(Integer newbill) {
		this.newbill = newbill;
	}

	public void setYslx(String yslx) {
		this.yslx = yslx;
	}
	
	public String getYslx() {
		return yslx;
	}
	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	private Collection<BundleReceipt> bundReceiptList;
	
	public BundleReceipt() {
	}
	public String getYdbeginplace() {
		return ydbeginplace;
	}
	public void setYdbeginplace(String ydbeginplace) {
		this.ydbeginplace = ydbeginplace;
	}
	public String getYdendplace() {
		return ydendplace;
	}



	public void setYdendplace(String ydendplace) {
		this.ydendplace = ydendplace;
	}



	public Collection<BundleReceipt> getBundReceiptList() {
		return bundReceiptList;
	}
	public void setBundReceiptList(Collection<BundleReceipt> bundReceiptList) {
		this.bundReceiptList = bundReceiptList;
	}

	public String getDriverName() {
		return driverName;
	}


	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}


	public String getDriverTel() {
		return driverTel;
	}


	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}


	public void setqdCost(Double qdCost){
		this.qdCost = new BigDecimal(qdCost);
	}
	public void setqdCost(Integer qdCost){
		this.qdCost = new BigDecimal(qdCost);
	}
	public Integer getXuhao() {
		return xuhao;
	}

	public void setXuhao(Integer xuhao) {
		this.xuhao = xuhao;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getChxh() {
		return chxh;
	}

	public void setChxh(String chxh) {
		this.chxh = chxh;
	}

	public String getClOwner() {
		return clOwner;
	}

	public void setClOwner(String clOwner) {
		this.clOwner = clOwner;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDaozhan() {
		return daozhan;
	}

	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}

	public String getDhgrid() {
		return dhgrid;
	}

	public void setDhgrid(String dhgrid) {
		this.dhgrid = dhgrid;
	}

	public Date getDhsj() {
		return dhsj;
	}

	public void setDhsj(Date dhsj) {
		this.dhsj = dhsj;
	}

	public String getDzshhd() {
		return dzshhd;
	}

	public void setDzshhd(String dzshhd) {
		this.dzshhd = dzshhd;
	}

	public BigDecimal getElseCost() {
		return elseCost == null ? BigDecimal.ZERO : elseCost;
	}

	public void setElseCost(BigDecimal elseCost) {
		this.elseCost = elseCost;
	}

	public String getFazhan() {
		return fazhan;
	}

	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}

	public Date getFchrq() {
		return fchrq;
	}

	public void setFchrq(Date fchrq) {
		this.fchrq = fchrq;
	}

	public String getFhdwmch() {
		return fhdwmch;
	}

	public void setFhdwmch(String fhdwmch) {
		this.fhdwmch = fhdwmch;
	}

	public Date getFhshj() {
		return fhshj;
	}

	public void setFhshj(Date fhshj) {
		this.fhshj = fhshj;
	}

	public String getFzshhd() {
		return fzshhd;
	}

	public void setFzshhd(String fzshhd) {
		this.fzshhd = fzshhd;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getHyy() {
		return hyy;
	}

	public void setHyy(String hyy) {
		this.hyy = hyy;
	}

	public Integer getiType() {
		return iType;
	}

	public void setiType(Integer iType) {
		this.iType = iType;
	}

	public Short getIsKaiyun() {
		return isKaiyun;
	}

	public void setIsKaiyun(Short isKaiyun) {
		this.isKaiyun = isKaiyun;
	}

	public Integer getJianshu() {
		return jianshu;
	}

	public void setJianshu(Integer jianshu) {
		this.jianshu = jianshu;
	}

	public Object getKhdh() {
		return khdh;
	}

	public void setKhdh(String khdh) {
		this.khdh = khdh;
	}

	public String getListNo() {
		return listNo;
	}

	public void setListNo(String listNo) {
		this.listNo = listNo;
	}

	public Date getLrsj() {
		return lrsj;
	}

	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
	}

	public String getPinming() {
		return pinming;
	}

	public void setPinming(String pinming) {
		this.pinming = pinming;
	}

	public BigDecimal getQdCost() {
		return qdCost == null ? BigDecimal.ZERO : qdCost;
	}

	public void setQdCost(BigDecimal qdCost) {
		this.qdCost = qdCost;
	}

	public Integer getSent() {
		return sent;
	}

	public void setSent(Integer sent) {
		this.sent = sent;
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

	public Integer getSourceflag() {
		return sourceflag;
	}

	public void setSourceflag(Integer sourceflag) {
		this.sourceflag = sourceflag;
	}

	public BigDecimal getTiji() {
		return tiji;
	}

	public void setTiji(BigDecimal tiji) {
		this.tiji = tiji;
	}

	public String getWxConName() {
		return wxConName;
	}

	public void setWxConName(String wxConName) {
		this.wxConName = wxConName;
	}

	public String getWxItem() {
		return wxItem;
	}

	public void setWxItem(String wxItem) {
		this.wxItem = wxItem;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	public String getWxTel() {
		return wxTel;
	}

	public void setWxTel(String wxTel) {
		this.wxTel = wxTel;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getXiechechexin() {
		return xiechechexin;
	}

	public void setXiechechexin(String xiechechexin) {
		this.xiechechexin = xiechechexin;
	}

	public String getXiechegrid() {
		return xiechegrid;
	}

	public void setXiechegrid(String xiechegrid) {
		this.xiechegrid = xiechegrid;
	}

	public Date getXiechesj() {
		return xiechesj;
	}

	public void setXiechesj(Date xiechesj) {
		this.xiechesj = xiechesj;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public Integer getYdxzh() {
		return ydxzh;
	}

	public void setYdxzh(Integer ydxzh) {
		this.ydxzh = ydxzh;
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

	public Date getYjddshj() {
		return yjddshj;
	}

	public void setYjddshj(Date yjddshj) {
		this.yjddshj = yjddshj;
	}

	public String getYsfs() {
		return ysfs;
	}

	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}

	public Integer getYxieche() {
		return yxieche;
	}

	public void setYxieche(Integer yxieche) {
		this.yxieche = yxieche;
	}

	public Date getZhchrq() {
		return zhchrq;
	}

	public void setZhchrq(Date zhchrq) {
		this.zhchrq = zhchrq;
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

	public BigDecimal getZhl() {
		return zhl;
	}

	public void setZhl(BigDecimal zhl) {
		this.zhl = zhl;
	}

	public Boolean getZiti() {
		return ziti;
	}

	public void setZiti(Boolean ziti) {
		this.ziti = ziti;
	}


	public String getWxId() {
		return wxId;
	}


	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	public String getTransferFlag() {
		return transferFlag;
	}
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}
}