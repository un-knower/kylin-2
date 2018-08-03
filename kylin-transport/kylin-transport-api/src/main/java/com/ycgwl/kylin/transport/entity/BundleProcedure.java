package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * 装载页面进行保存直接调用存储过程的实体类
 * @author hp001
 *
 */
public class BundleProcedure  extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**装载类型*/
	private Integer iType;
	/** 运单标号 */
	private String ydbhid;	
	
	/** 车牌号 */
	private String cxh;	
	
	/** 装车日期 */
	private String zcrq;	
	
	/** 发车日期 */
	private String fchrq;	
	
	/** 登录用户 */
	private String user;	

	/** 登录工号 */
	private String grid;	
	
	/** 预计到达时间 */
	private String yjddshj;	
	
	/** 外线单号 */
	private String wxId;	
	
	/** 外线名称 */
	private String wxName;	
	
	/** 外线联系人 */
	private String wxConName;
	
	/** 外线电话 */
	private String wxTel;	
	
	/** 是外线还是外车   也有可能是自由车 */
	private String clOwner;	
	
	/** 运输成本 */
	private BigDecimal qdCost;	
	
	/** 其他成本 */
	private BigDecimal elseCost;
	
	/** 运输起始地 */
	private String fazhan;	
	
	/** 运输目的地 */
	private String daozhan;	
	
	/** 中转网点 */
	private String dzshhd;

	/** 返回值(1:成功,0:失败) */
	private Integer flag;
	
	/** 返回消息 */
	private String message;

	public Integer getiType() {
		return iType;
	}

	public void setiType(Integer iType) {
		this.iType = iType;
	}

	public String getDzshhd() {
		return dzshhd;
	}

	public void setDzshhd(String dzshhd) {
		this.dzshhd = dzshhd;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public String getCxh() {
		return cxh;
	}

	public void setCxh(String cxh) {
		this.cxh = cxh;
	}

	public String getZcrq() {
		return zcrq;
	}

	public void setZcrq(String zcrq) {
		this.zcrq = zcrq;
	}

	public String getFchrq() {
		return fchrq;
	}

	public void setFchrq(String fchrq) {
		this.fchrq = fchrq;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getYjddshj() {
		return yjddshj;
	}

	public void setYjddshj(String yjddshj) {
		this.yjddshj = yjddshj;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	public String getWxConName() {
		return wxConName;
	}

	public void setWxConName(String wxConName) {
		this.wxConName = wxConName;
	}

	public String getWxTel() {
		return wxTel;
	}

	public void setWxTel(String wxTel) {
		this.wxTel = wxTel;
	}

	public String getClOwner() {
		return clOwner;
	}

	public void setClOwner(String clOwner) {
		this.clOwner = clOwner;
	}

	public BigDecimal getQdCost() {
		return qdCost;
	}

	public void setQdCost(BigDecimal qdCost) {
		this.qdCost = qdCost;
	}

	public BigDecimal getElseCost() {
		return elseCost;
	}

	public void setElseCost(BigDecimal elseCost) {
		this.elseCost = elseCost;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	/**
	  * @Description: 该实体类保存中可能为空的字段
	  * @exception
	 */
	public void replaceNull(){
		if(StringUtils.isBlank(cxh)){	cxh = "";	}
		if(StringUtils.isBlank(wxId)){	wxId = "";	}
		if(StringUtils.isBlank(wxName)){	wxName = "";	}
		if(StringUtils.isBlank(wxConName)){	wxConName = "";	}
		if(StringUtils.isBlank(wxTel)){	wxTel = "";	}
		if(StringUtils.isBlank(fazhan)){	fazhan = "";	}
		if(StringUtils.isBlank(dzshhd)){	dzshhd = "";	}
	}
	
	
}
