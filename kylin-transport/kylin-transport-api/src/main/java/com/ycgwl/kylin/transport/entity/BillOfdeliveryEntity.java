package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 表:THQSD
 * TODO Add description
 * <p>
 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-01-24 13:27:00
 */
public class BillOfdeliveryEntity extends BaseEntity{
	
	private static final long serialVersionUID = -7691898085058113681L;
	
	private Integer thqshdid;
	private String  ydbhid;
	private String	shhrmch;
	private String pinming;
	private Integer jianshu;
	private BigDecimal zhl;
	private BigDecimal tiji;
	private String bzh;
	private String cangwei1;
	private String thrsfzhm;
	private String shhrsfzhm;
	private String kpr;
	private String beizhu;
	private Date shijian;
	private Short ydxzh;
	private Integer printed;
	private String gs;
	private Integer thqsdxz;
	private String thrmch;
	private String thrlxdh;
	private Integer xuhao;
	private Integer old;
	private Integer shjhm;
	private String sysName = "麒麟";
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public Integer getThqshdid() {
		return thqshdid;
	}
	public void setThqshdid(Integer thqshdid) {
		this.thqshdid = thqshdid;
	}
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public String getShhrmch() {
		return shhrmch;
	}
	public void setShhrmch(String shhrmch) {
		this.shhrmch = shhrmch;
	}
	public String getPinming() {
		return pinming;
	}
	public void setPinming(String pinming) {
		this.pinming = pinming;
	}
	public Integer getJianshu() {
		return jianshu;
	}
	public void setJianshu(Integer jianshu) {
		this.jianshu = jianshu;
	}
	public BigDecimal getZhl() {
		return zhl;
	}
	public void setZhl(BigDecimal zhl) {
		this.zhl = zhl;
	}
	public BigDecimal getTiji() {
		return tiji;
	}
	public void setTiji(BigDecimal tiji) {
		this.tiji = tiji;
	}
	public String getBzh() {
		return bzh;
	}
	public void setBzh(String bzh) {
		this.bzh = bzh;
	}
	public String getCangwei1() {
		return cangwei1 == null || "null".equals(cangwei1) ? "": cangwei1;
	}
	public void setCangwei1(String cangwei1) {
		this.cangwei1 = cangwei1;
	}
	public String getThrsfzhm() {
		return thrsfzhm;
	}
	public void setThrsfzhm(String thrsfzhm) {
		this.thrsfzhm = thrsfzhm;
	}
	public String getShhrsfzhm() {
		return shhrsfzhm;
	}
	public void setShhrsfzhm(String shhrsfzhm) {
		this.shhrsfzhm = shhrsfzhm;
	}
	public String getKpr() {
		return kpr;
	}
	public void setKpr(String kpr) {
		this.kpr = kpr;
	}
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	public Date getShijian() {
		return shijian;
	}
	public void setShijian(Date shijian) {
		this.shijian = shijian;
	}
	public Short getYdxzh() {
		return ydxzh;
	}
	public void setYdxzh(Short ydxzh) {
		this.ydxzh = ydxzh;
	}
	public Integer getPrinted() {
		return printed;
	}
	public void setPrinted(Integer printed) {
		this.printed = printed;
	}
	public String getGs() {
		return gs;
	}
	public void setGs(String gs) {
		this.gs = gs;
	}
	public Integer getThqsdxz() {
		return thqsdxz;
	}
	public void setThqsdxz(Integer thqsdxz) {
		this.thqsdxz = thqsdxz;
	}
	public String getThrmch() {
		return thrmch;
	}
	public void setThrmch(String thrmch) {
		this.thrmch = thrmch;
	}
	public String getThrlxdh() {
		return thrlxdh;
	}
	public void setThrlxdh(String thrlxdh) {
		this.thrlxdh = thrlxdh;
	}
	public Integer getXuhao() {
		return xuhao;
	}
	public void setXuhao(Integer xuhao) {
		this.xuhao = xuhao;
	}
	public Integer getOld() {
		return old;
	}
	public void setOld(Integer old) {
		this.old = old;
	}
	public Integer getShjhm() {
		return shjhm;
	}
	public void setShjhm(Integer shjhm) {
		this.shjhm = shjhm;
	}
	
}

