package com.ycgwl.kylin.transport.entity.adjunct;

import java.io.Serializable;



public class PrintData implements Serializable {

	private static final long serialVersionUID = -0472025754L;
	
	/**
	 * 运费合计
	 */
	private Double conveyFee;
	
	/**
	 * 件数 
	 */
	private Integer jianShu;
	
	/**
	 * 重量  
	 */
	private Double zhongLiang;
	
	/**
	 * 体积 
	 */	
	private Double tiJi;
	
	private String pingMing;
	
	
	public String getPingMing() {
		return pingMing;
	}

	public void setPingMing(String pingMing) {
		this.pingMing = pingMing;
	}

	public PrintData(Double conveyFee,Integer jianShu,Double zhongLiang,Double tiJi,String pingMing) {
		this.conveyFee=conveyFee;
		this.jianShu=jianShu;
		this.zhongLiang=zhongLiang;
		this.tiJi=tiJi;
	}
	
	public PrintData() {
		
	}
	
	public Double getConveyFee() {
		return conveyFee;
	}

	public void setConveyFee(Double conveyFee) {
		this.conveyFee = conveyFee;
	}

	public Integer getJianShu() {
		return jianShu;
	}

	public void setJianShu(Integer jianShu) {
		this.jianShu = jianShu;
	}

	public Double getZhongLiang() {
		return zhongLiang;
	}

	public void setZhongLiang(Double zhongLiang) {
		this.zhongLiang = zhongLiang;
	}

	public Double getTiJi() {
		return tiJi;
	}

	public void setTiJi(Double tiJi) {
		this.tiJi = tiJi;
	}

}
