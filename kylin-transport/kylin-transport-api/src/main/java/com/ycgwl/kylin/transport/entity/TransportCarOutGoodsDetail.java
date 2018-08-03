package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * 送货派车单货物明细
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月27日
 */
public class TransportCarOutGoodsDetail extends BaseEntity{

	private static final long serialVersionUID = -2592314127845616926L;
	
	private Integer detailId;
	
	private String gsid;
	
	private Integer id;
	
	private String hwmc;
	
	private String ch;
	
	private Integer js;
	
	private Float zl;
	
	private Float tj;
	
	private Integer fd;
	
	private Integer qdxh;
	
	private String ydbhid;
	
	private Integer ydxz;
	
	private String dzshhd;
	
	private String rowguid;
	
	private Integer duanYunJieGeBiaoId;
	
	private BigDecimal duanYunFei;
	
	private Short isKaiYun;

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

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

	public String getHwmc() {
		return hwmc;
	}

	public void setHwmc(String hwmc) {
		this.hwmc = hwmc;
	}

	public String getCh() {
		return ch;
	}

	public void setCh(String ch) {
		this.ch = ch;
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

	public Integer getFd() {
		return fd;
	}

	public void setFd(Integer fd) {
		this.fd = fd;
	}

	public Integer getQdxh() {
		return qdxh;
	}

	public void setQdxh(Integer qdxh) {
		this.qdxh = qdxh;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public Integer getYdxz() {
		return ydxz;
	}

	public void setYdxz(Integer ydxz) {
		this.ydxz = ydxz;
	}

	public String getDzshhd() {
		return dzshhd;
	}

	public void setDzshhd(String dzshhd) {
		this.dzshhd = dzshhd;
	}

	public String getRowguid() {
		return rowguid;
	}

	public void setRowguid(String rowguid) {
		this.rowguid = rowguid;
	}

	public Integer getDuanYunJieGeBiaoId() {
		return duanYunJieGeBiaoId;
	}

	public void setDuanYunJieGeBiaoId(Integer duanYunJieGeBiaoId) {
		this.duanYunJieGeBiaoId = duanYunJieGeBiaoId;
	}

	public BigDecimal getDuanYunFei() {
		return duanYunFei;
	}

	public void setDuanYunFei(BigDecimal duanYunFei) {
		this.duanYunFei = duanYunFei;
	}

	public Short getIsKaiYun() {
		return isKaiYun;
	}

	public void setIsKaiYun(Short isKaiYun) {
		this.isKaiYun = isKaiYun;
	}
	
}
