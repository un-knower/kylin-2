package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 成本明细
 * @author Acer
 *
 */
public class BundleCostDetail extends BaseEntity  {

	private static final long serialVersionUID = -5914208706956481981L;
	/** 序号	ID */
	private Integer id;		
	
	/**成本类型	i_type */
	private Integer iType;	
	
	/** 单据编号	INS_NO */
	public String insNo;
	
	/** 单据细则号	INS_XH */
	public Integer insXh;
	
	/** 运单编号	YDBHID */
	private String ydbhid;
	
	/**运单细则号	YDXZH */
	private Integer ydxzh;	
	
	/** 总成本	D_COST */
	private BigDecimal dCost;
	
	/**总收入	D_INCOME */
	private BigDecimal dIncome;	
	
	/** 干线成本	T_COST_SALE */
	private BigDecimal tCostElse;
	
	/**装卸成本	T_COST_ELSE */
	private BigDecimal tCostSale;	
	
	/** 发站	FAZHAN */
	private String fazhan;		
	
	/** 到站	DAOZHAN */
	private String daozhan;	
	
	/** 品名	PINGMING */
	private String pingming;	
	
	/** 件数	JIANSHU */
	private Integer jianshu;	
	
	/** 体积	TIJI */
	private BigDecimal tiji;	
	
	/** 重量	ZHL */
	private BigDecimal zhl;	
	
	/** 序号	xuhao */
	private Integer xuhao;	
	
	/** 不明确其含义  drecgendate */
	private Timestamp drecgendate;
	
	/** 分理费1	fenlifee1 */
	private BigDecimal fenlifee1;
	
	/** 分理费2	fenlifee2 */
	private BigDecimal fenlifee2;	
	
	/** 不明确其含义 js_dttm */
	private Timestamp jsDttm;	
	
	/** 不明确其含义 js_tag */
	private Object jsTag;	
	
	/** 运输方式	YSFS */
	private String ysfs;	
	
	/** 运输号码	YSHM */
	private String yshm;	
	
	/** 装车日期	ZHCHRQ */
	private Timestamp zhchrq;	
	
	/** 中转费	zhongzhuanfee */
	private BigDecimal zhongzhuanfee;		
	
	public BundleCostDetail() {
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getiType() {
		return iType;
	}
	public void setiType(Integer iType) {
		this.iType = iType;
	}
	public String getInsNo() {
		return insNo;
	}
	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}
	public Integer getInsXh() {
		return insXh;
	}
	public void setInsXh(Integer insXh) {
		this.insXh = insXh;
	}
	public BigDecimal getdCost() {
		return dCost;
	}
	public void setdCost(BigDecimal dCost) {
		this.dCost = dCost;
	}
	public BigDecimal getdIncome() {
		return dIncome;
	}
	public void setdIncome(BigDecimal dIncome) {
		this.dIncome = dIncome;
	}
	public String getDaozhan() {
		return daozhan;
	}
	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}
	public Timestamp getDrecgendate() {
		return drecgendate;
	}
	public void setDrecgendate(Timestamp drecgendate) {
		this.drecgendate = drecgendate;
	}
	public String getFazhan() {
		return fazhan;
	}
	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}
	public BigDecimal getFenlifee1() {
		return fenlifee1;
	}
	public void setFenlifee1(BigDecimal fenlifee1) {
		this.fenlifee1 = fenlifee1;
	}
	public BigDecimal getFenlifee2() {
		return fenlifee2;
	}
	public void setFenlifee2(BigDecimal fenlifee2) {
		this.fenlifee2 = fenlifee2;
	}
	public Integer getJianshu() {
		return jianshu;
	}
	public void setJianshu(Integer jianshu) {
		this.jianshu = jianshu;
	}
	public Timestamp getJsDttm() {
		return jsDttm;
	}
	public void setJsDttm(Timestamp jsDttm) {
		this.jsDttm = jsDttm;
	}
	public Object getJsTag() {
		return jsTag;
	}
	public void setJsTag(Object jsTag) {
		this.jsTag = jsTag;
	}
	public String getPingming() {
		return pingming;
	}
	public void setPingming(String pingming) {
		this.pingming = pingming;
	}
	public BigDecimal gettCostElse() {
		return tCostElse;
	}
	public void settCostElse(BigDecimal tCostElse) {
		this.tCostElse = tCostElse;
	}
	public BigDecimal gettCostSale() {
		return tCostSale;
	}
	public void settCostSale(BigDecimal tCostSale) {
		this.tCostSale = tCostSale;
	}
	public BigDecimal getTiji() {
		return tiji;
	}
	public void setTiji(BigDecimal tiji) {
		this.tiji = tiji;
	}
	public Integer getXuhao() {
		return xuhao;
	}
	public void setXuhao(Integer xuhao) {
		this.xuhao = xuhao;
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
	public String getYsfs() {
		return ysfs;
	}
	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}
	public String getYshm() {
		return yshm;
	}
	public void setYshm(String yshm) {
		this.yshm = yshm;
	}
	public Timestamp getZhchrq() {
		return zhchrq;
	}
	public void setZhchrq(Timestamp zhchrq) {
		this.zhchrq = zhchrq;
	}
	public BigDecimal getZhl() {
		return zhl;
	}
	public void setZhl(BigDecimal zhl) {
		this.zhl = zhl;
	}
	public BigDecimal getZhongzhuanfee() {
		return zhongzhuanfee;
	}
	public void setZhongzhuanfee(BigDecimal zhongzhuanfee) {
		this.zhongzhuanfee = zhongzhuanfee;
	}
}