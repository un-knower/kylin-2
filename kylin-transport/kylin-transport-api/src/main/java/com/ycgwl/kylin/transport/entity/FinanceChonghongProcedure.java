package com.ycgwl.kylin.transport.entity;

public class FinanceChonghongProcedure {
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
	 * 线路（公司编号）
	 */
	private String xianlu;
	
	/**
	 * 原因
	 */
	private String why;
	
	/**
	 * 出纳
	 */
	private String chuna;
	
	/**
	 * 冲红状态
	 */
	private int chonghongzhuangtai;
	
	/**
	 * 收据号
	 */
	private int shoujuhao;
	
	/**
	 * 得到的结果
	 */
	private long cwpzhbh2;

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

	public String getXianlu() {
		return xianlu;
	}

	public void setXianlu(String xianlu) {
		this.xianlu = xianlu;
	}

	public String getWhy() {
		return why;
	}

	public void setWhy(String why) {
		this.why = why;
	}

	public String getChuna() {
		return chuna;
	}

	public void setChuna(String chuna) {
		this.chuna = chuna;
	}

	public int getChonghongzhuangtai() {
		return chonghongzhuangtai;
	}

	public void setChonghongzhuangtai(int chonghongzhuangtai) {
		this.chonghongzhuangtai = chonghongzhuangtai;
	}

	public int getShoujuhao() {
		return shoujuhao;
	}

	public void setShoujuhao(int shoujuhao) {
		this.shoujuhao = shoujuhao;
	}

	public long getCwpzhbh2() {
		return cwpzhbh2;
	}

	public void setCwpzhbh2(long cwpzhbh2) {
		this.cwpzhbh2 = cwpzhbh2;
	}
	
}
