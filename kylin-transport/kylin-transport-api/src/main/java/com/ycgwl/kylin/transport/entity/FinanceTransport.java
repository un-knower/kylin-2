package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * FIWT
 * @Description: 与财凭相关运单信息
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @date 2017年10月25日
 */
public class FinanceTransport implements Serializable{
	
	private static final long serialVersionUID = -7776311630744162159L;

	/**
	 *  是否已生成报表
	 */
	private Integer isreport;
	
	/**
	 *  打印时间
	 */
	private Integer printtime;
	
	/**
	 *  生成时间
	 */
	private Date scsj;
	
	/**
	 * 货物运单编号
	 */
	private String ydbh;
	
	/**
	 * 财凭类型
	 */
	private int type;
	
	private Long cwpzhbh;
	
	private String xianlu;
	
	private String nf;

	public String getXianlu() {
		return xianlu;
	}

	public void setXianlu(String xianlu) {
		this.xianlu = xianlu;
	}

	public Long getCwpzhbh() {
		return cwpzhbh;
	}

	public void setCwpzhbh(Long cwpzhbh) {
		this.cwpzhbh = cwpzhbh;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getYdbh() {
		return ydbh;
	}

	public void setYdbh(String ydbh) {
		this.ydbh = ydbh;
	}

	public Date getScsj() {
		return scsj;
	}

	public void setScsj(Date scsj) {
		this.scsj = scsj;
	}

	public Integer getIsreport() {
		return isreport;
	}

	public void setIsreport(Integer isreport) {
		this.isreport = isreport;
	}

	public Integer getPrinttime() {
		return printtime;
	}

	public void setPrinttime(Integer printtime) {
		this.printtime = printtime;
	}

	public String getNf() {
		return nf;
	}

	public void setNf(String nf) {
		this.nf = nf;
	}
}
