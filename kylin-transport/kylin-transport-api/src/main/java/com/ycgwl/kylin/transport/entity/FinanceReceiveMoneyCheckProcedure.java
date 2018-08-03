package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;

/**
 * FinanceCheckMonthReceiveProcedure
 * 
 * @Description: 调用存储过程checkyuejie的参数类
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @date 2017年10月25日
 */
public class FinanceReceiveMoneyCheckProcedure implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 年份
	 */
	private String nf;
	
	/**
	 * 未结算金额
	 */
	private Double yejieje;
	
	/**
	 * 公司
	 */
	private String gs;
	
	/**
	 * 财凭号
	 */
	private Long cwpzhbh;
	
	/**
	 * 客户编码
	 */
	private String mycustomercode;
	
	/**
	 * 存储过程检查结果
	 */
	private Integer myresult;
	
	/**
	 * 公司编号
	 */
	private String gsbh;

	public String getGsbh() {
		return gsbh;
	}

	public void setGsbh(String gsbh) {
		this.gsbh = gsbh;
	}

	public String getMycustomercode() {
		return mycustomercode;
	}

	public void setMycustomercode(String mycustomercode) {
		this.mycustomercode = mycustomercode;
	}

	public Integer getMyresult() {
		return myresult;
	}

	public void setMyresult(Integer myresult) {
		this.myresult = myresult;
	}

	public String getNf() {
		return nf;
	}

	public void setNf(String nf) {
		this.nf = nf;
	}

	public Double getYejieje() {
		return yejieje;
	}

	public void setYejieje(Double yejieje) {
		this.yejieje = yejieje;
	}

	public String getGs() {
		return gs;
	}

	public void setGs(String gs) {
		this.gs = gs;
	}

	public Long getCwpzhbh() {
		return cwpzhbh;
	}

	public void setCwpzhbh(Long cwpzhbh) {
		this.cwpzhbh = cwpzhbh;
	}
}
