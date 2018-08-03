package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;


/**
 * 成本基础信息
 * The persistent class for the T_INCOME_COST_H database table.
 */

public class BundleCostTopic extends BaseEntity  {
	private static final long serialVersionUID = 2224734719234805233L;
	
	/** 序号	ID */
	private Integer id;	
	
	/** 成本类型	i_type*/
	private Integer iType;	
	
	/**单据编号	INS_NO */
	private String insNo;
	
	/**创建公司	CREATE_GS */
	private String createGs;	
	
	/** 创建人	CREATE_USER */
	private String createUser;	
	
	/** 创建人名称	CREATE_USERNAME */
	private String createUsername;
	
	/** 创建时间	RECGENDATE */
	private Timestamp recgendate;
	
	/** 车厢号	CXH */
	private String cxh;	
	
	/** 到站	DAOZHAN */
	private String daozhan;
	
	/** 发站	FAZHAN */
	private String fazhan;
	
	/** 不明确其含义 js_dttm */
	private Timestamp jsDttm;	
	
	/**不明确其含义 js_tag*/
	private Object jsTag;	
	
	/** 不明确其含义 js_user*/
	private String jsUser;	
	
	/** 修改前装卸成本	old_T_COST_ELSE */
	private BigDecimal old_T_COST_ELSE;	
	
	/** 修改前干线成本	old_T_COST_SALE */
	private BigDecimal old_T_COST_SALE;	
	
	/** 不明确其含义 rowkeyflag */
	private Integer rowkeyflag;	
	
	/** 总成本	T_COST */
	private BigDecimal tCost;	
	
	/** 装卸成本	T_COST_ELSE */
	private BigDecimal tCostElse;
	
	/** 干线成本	T_COST_SALE */
	private BigDecimal tCostSale;	
	
	/** 不明确其含义 t_cost_sale1 */
	private BigDecimal tCostSale1;
	
	/** 不明确其含义 t_cost_sale2*/
	private BigDecimal tCostSale2;	
	
	/** 总收入	T_INCOME */
	private BigDecimal tIncome;	
	
	/** 外线公司名称	WX_GSNAME */
	private String wxGsname;	
	
	/** 外线单号	WX_GSNO */
	private String wxGsno;
	
	/** 外线单号	wx_id */
	private String wxId;	
	
	/** 外线类型	wx_type */
	private String wxType;
	
	/** 运输方式	YSFS */
	private String ysfs;
	
	/** 运输号码	YSHM */
	private String yshm;
	
	/** 装车日期	ZHCHRQ */
	private Timestamp zhchrq;				
	
	/** 成本明细 */
	private Collection<BundleCostDetail> details;
	
	public BundleCostTopic() {
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

	public String getCreateGs() {
		return createGs;
	}

	public void setCreateGs(String createGs) {
		this.createGs = createGs;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public String getCxh() {
		return cxh;
	}

	public void setCxh(String cxh) {
		this.cxh = cxh;
	}

	public String getDaozhan() {
		return daozhan;
	}

	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}

	public String getFazhan() {
		return fazhan;
	}

	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
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

	public String getJsUser() {
		return jsUser;
	}

	public void setJsUser(String jsUser) {
		this.jsUser = jsUser;
	}

	public BigDecimal getOld_T_COST_ELSE() {
		return old_T_COST_ELSE;
	}

	public void setOld_T_COST_ELSE(BigDecimal old_T_COST_ELSE) {
		this.old_T_COST_ELSE = old_T_COST_ELSE;
	}

	public BigDecimal getOld_T_COST_SALE() {
		return old_T_COST_SALE;
	}

	public void setOld_T_COST_SALE(BigDecimal old_T_COST_SALE) {
		this.old_T_COST_SALE = old_T_COST_SALE;
	}

	public Timestamp getRecgendate() {
		return recgendate;
	}

	public void setRecgendate(Timestamp recgendate) {
		this.recgendate = recgendate;
	}

	public Integer getRowkeyflag() {
		return rowkeyflag;
	}

	public void setRowkeyflag(Integer rowkeyflag) {
		this.rowkeyflag = rowkeyflag;
	}

	public BigDecimal gettCost() {
		return tCost;
	}

	public void settCost(BigDecimal tCost) {
		this.tCost = tCost;
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

	public BigDecimal gettCostSale1() {
		return tCostSale1;
	}

	public void settCostSale1(BigDecimal tCostSale1) {
		this.tCostSale1 = tCostSale1;
	}

	public BigDecimal gettCostSale2() {
		return tCostSale2;
	}

	public void settCostSale2(BigDecimal tCostSale2) {
		this.tCostSale2 = tCostSale2;
	}

	public BigDecimal gettIncome() {
		return tIncome;
	}

	public void settIncome(BigDecimal tIncome) {
		this.tIncome = tIncome;
	}

	public String getWxGsname() {
		return wxGsname;
	}

	public void setWxGsname(String wxGsname) {
		this.wxGsname = wxGsname;
	}

	public String getWxGsno() {
		return wxGsno;
	}

	public void setWxGsno(String wxGsno) {
		this.wxGsno = wxGsno;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	public String getWxType() {
		return wxType;
	}

	public void setWxType(String wxType) {
		this.wxType = wxType;
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

	public Collection<BundleCostDetail> getDetails() {
		return details;
	}

	public void setDetails(Collection<BundleCostDetail> details) {
		this.details = details;
	}
}