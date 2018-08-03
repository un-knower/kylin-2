package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;

/**
 * FinanceStandardPrice
 * 
 * @Description: 标准单价
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @date 2017年11月27日
 */
public class FinanceStandardPrice implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 叉车费单价(FIWT表字段）.
	 */
	private Double zhjxzyf;
	
	/**
	 * 轻货装卸费单价(FIWT表字段）.
	 */
	private Double qhzhxfdj;
	
	/**
	 * 重货装卸费单价(FIWT表字段）.
	 */
	private Double zhzhxfdj;
	
	/**
	 * 保率（FIWT字段）.
	 */
	private Double premiumRate;
	
	/**
	 * 公司.
	 */
	private String gs;

	public Double getPremiumRate() {
		return premiumRate;
	}

	public void setPremiumRate(Double premiumRate) {
		this.premiumRate = premiumRate;
	}

	public Double getZhjxzyf() {
		return zhjxzyf;
	}

	public void setZhjxzyf(Double zhjxzyf) {
		this.zhjxzyf = zhjxzyf;
	}

	public Double getQhzhxfdj() {
		return qhzhxfdj;
	}

	public void setQhzhxfdj(Double qhzhxfdj) {
		this.qhzhxfdj = qhzhxfdj;
	}

	public Double getZhzhxfdj() {
		return zhzhxfdj;
	}

	public void setZhzhxfdj(Double zhzhxfdj) {
		this.zhzhxfdj = zhzhxfdj;
	}


	public String getGs() {
		return gs;
	}

	public void setGs(String gs) {
		this.gs = gs;
	}
}
