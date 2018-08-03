package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * 运单查询主页面返回财凭信息实体
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月29日
 */
public class FinanceCertifyResult extends BaseEntity{
	
	private static final long serialVersionUID = 2313849473303774073L;

	/** 运单编号 */
	private String ydbhid; 
	
	/** 财凭号 */
	private BigDecimal cwpzhbh;
	
	/** 合计金额 */
	private BigDecimal hjje;
	
	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public BigDecimal getCwpzhbh() {
		return cwpzhbh;
	}

	public void setCwpzhbh(BigDecimal cwpzhbh) {
		this.cwpzhbh = cwpzhbh;
	}

	public BigDecimal getHjje() {
		return hjje;
	}

	public void setHjje(BigDecimal hjje) {
		this.hjje = hjje;
	}
	
}
