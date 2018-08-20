package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class FenliKucunEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String gs;
	/** 运单号 */
	private String ydbhid;
	/** 运单细则号 */
	private String ydxzh;	
	/** 件数 */
	private String jianshu;
	/** 体积 */
	private String tiji;
	/** 品名 */ 
	private String pinming;
	/** 重量 */
	private String zhl;
	
	private Integer xuhao;
	/**
	 * 到货时间
	 */
	private Date dhsj;
	
	public Date getDhsj() {
		return dhsj;
	}
	public void setDhsj(Date dhsj) {
		this.dhsj = dhsj;
	}
	public Integer getXuhao() {
		return xuhao;
	}
	public void setXuhao(Integer xuhao) {
		this.xuhao = xuhao;
	}
	public String getGs() {
		return gs;
	}
	public void setGs(String gs) {
		this.gs = gs;
	}
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public String getYdxzh() {
		return ydxzh;
	}
	public void setYdxzh(String ydxzh) {
		this.ydxzh = ydxzh;
	}
	public String getJianshu() {
		return jianshu;
	}
	public void setJianshu(String jianshu) {
		this.jianshu = jianshu;
	}
	public String getTiji() {
		return tiji;
	}
	public void setTiji(String tiji) {
		this.tiji = tiji;
	}
	public String getPinming() {
		return pinming;
	}
	public void setPinming(String pinming) {
		this.pinming = pinming;
	}
	public String getZhl() {
		return zhl;
	}
	public void setZhl(String zhl) {
		this.zhl = zhl;
	}
	public boolean isNotEmpty() {
		BigDecimal tiji = new BigDecimal(this.tiji);
		BigDecimal zhl = new BigDecimal(this.zhl);
		Integer jianshu = Integer.parseInt(this.jianshu);
		if(tiji.compareTo(BigDecimal.ZERO) == 1 || zhl.compareTo(BigDecimal.ZERO) == 1 ||
				jianshu > 0) {
      return true;
    }
		return false;
	}
	
	

}
