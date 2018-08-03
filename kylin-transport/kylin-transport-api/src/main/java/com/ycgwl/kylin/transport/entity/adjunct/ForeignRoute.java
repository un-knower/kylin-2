package com.ycgwl.kylin.transport.entity.adjunct;

import com.ycgwl.kylin.entity.BaseEntity;


/**
 * 外线
 * The persistent class for the t_waichexinxi_gysjbzl database table.
 */
public class ForeignRoute extends BaseEntity  {
	
	private static final long serialVersionUID = 1L;
	
	private String gysGs;//外线名称gys_gs
	private String gysDhsj;//外线司机电话gys_dhsj
	private String gysLxr;//外线联系人gys_lxr
	private String gysDz;//外线地址gys_dz
	private Integer wno;// TODO 待定是否是外线单号
	private String xzh;
	
	public String getGysGs() {
		return gysGs;
	}
	public void setGysGs(String gysGs) {
		this.gysGs = gysGs;
	}
	public String getGysDhsj() {
		return gysDhsj;
	}
	public void setGysDhsj(String gysDhsj) {
		this.gysDhsj = gysDhsj;
	}
	public String getGysLxr() {
		return gysLxr;
	}
	public void setGysLxr(String gysLxr) {
		this.gysLxr = gysLxr;
	}
	public String getGysDz() {
		return gysDz;
	}
	public void setGysDz(String gysDz) {
		this.gysDz = gysDz;
	}
	public Integer getWno() {
		return wno;
	}
	public void setWno(Integer wno) {
		this.wno = wno;
	}
	public String getXzh() {
		return xzh;
	}
	public void setXzh(String xzh) {
		this.xzh = xzh;
	}
}