package com.ycgwl.kylin.transport.entity;


import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
/**
 * 库存的实体类
 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-01 14:56:46
 */
public class KucunEntity  extends BaseEntity {

	private static final long serialVersionUID = 646767649697720905L;
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
		return jianshu==null?"0":jianshu;
	}
	public void setJianshu(String jianshu) {
		this.jianshu = jianshu;
	}
	public String getTiji() {
		return tiji==null?"0":tiji;
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
		return zhl==null?"0":zhl;
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
