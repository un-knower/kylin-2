package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BundleReceiptHomePageEntity extends BaseEntity {

	private static final long serialVersionUID = -4521230633630271268L;

	private String chxh;
	private String driverName;
	private Date lrsj;
	private String fazhan;
	private String daozhan;
	private BigDecimal qd_cost;
	private BigDecimal else_cost;
	private Date fchrq;
	private Date yjddshj;
	
	private String ydbhid;
	private String fhdwmch;
	private String pinming;
	private Integer jianshu;
	private BigDecimal zhl;
	private BigDecimal tiji;
	private Integer buildIncome;
	
	//2018-03-27 增加两个字段.外线名称和外线单号
	private String wx_name;
	private String wx_item;
	private String cl_owner;
	
	private List<BundleReceiptHomePageDetail> detail;
	
	public void setDetailData(List<BundleReceiptHomePageEntity> list) {
		this.detail = new ArrayList<>();
		for (BundleReceiptHomePageEntity entity : list) {
			BundleReceiptHomePageDetail detail = new BundleReceiptHomePageDetail();
			detail.setFhdwmch(entity.getFhdwmch());
			detail.setYdbhid(entity.getYdbhid());
			detail.setJianshu(entity.getJianshu());
			detail.setPinming(entity.getPinming());
			detail.setTiji(entity.getTiji());
			detail.setZhl(entity.getZhl());
			this.detail.add(detail);
		}
	}
	
	public String getCl_owner() {
		return cl_owner;
	}

	public void setCl_owner(String cl_owner) {
		this.cl_owner = cl_owner;
	}

	public String getWx_name() {
		return wx_name;
	}

	public void setWx_name(String wx_name) {
		this.wx_name = wx_name;
	}

	public String getWx_item() {
		return wx_item;
	}

	public void setWx_item(String wx_item) {
		this.wx_item = wx_item;
	}

	public Integer getBuildIncome() {
		return buildIncome;
	}
	public void setBuildIncome(Integer buildIncome) {
		this.buildIncome = buildIncome;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public List<BundleReceiptHomePageDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<BundleReceiptHomePageDetail> detail) {
		this.detail = detail;
	}
	public Integer getJianshu() {
		return jianshu;
	}
	public void setJianshu(Integer jianshu) {
		this.jianshu = jianshu;
	}
	public BigDecimal getZhl() {
		if(zhl == null) {
      return BigDecimal.ZERO;
    }
		return zhl;
	}
	public void setZhl(BigDecimal zhl) {
		this.zhl = zhl;
	}
	public BigDecimal getTiji() {
		if(tiji == null) {
      return BigDecimal.ZERO;
    }
		return tiji;
	}
	public void setTiji(BigDecimal tiji) {
		this.tiji = tiji;
	}
	public String getChxh() {
		if(chxh==null) {
      return "";
    }
		return chxh;
	}
	public void setChxh(String chxh) {
		this.chxh = chxh;
	}
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public String getFhdwmch() {
		return fhdwmch;
	}
	public void setFhdwmch(String fhdwmch) {
		this.fhdwmch = fhdwmch;
	}
	public String getPinming() {
		return pinming;
	}
	public void setPinming(String pinming) {
		this.pinming = pinming;
	}
	public Date getLrsj() {
		return lrsj;
	}
	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
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
	public BigDecimal getElse_cost() {
		if(else_cost == null) {
      return BigDecimal.ZERO;
    }
		return else_cost;
	}
	public BigDecimal getQd_cost() {
		if(qd_cost == null) {
      return BigDecimal.ZERO;
    }
		return qd_cost;
	}
	public void setElse_cost(BigDecimal else_cost) {
		this.else_cost = else_cost;
	}
	public void setQd_cost(BigDecimal qd_cost) {
		this.qd_cost = qd_cost;
	}
	public Date getFchrq() {
		return fchrq;
	}
	public void setFchrq(Date fchrq) {
		this.fchrq = fchrq;
	}
	public Date getYjddshj() {
		return yjddshj;
	}
	public void setYjddshj(Date yjddshj) {
		this.yjddshj = yjddshj;
	}
	
	
}

