package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * FinanceReceiveMoney
 * 
 * @Description: 财凭金额表（付款方式表）
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @date 2017年10月25日
 */
public class FinanceReceiveMoney implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * @Description:工号
	 */
	private String grid;
	
	/**
	 * @Description:线路
	 */
	private String xianlu;
	
	/**
	 * @Description:财凭号
	 */
	private Long cwpzhbh;
	
	/**
	 * @Description:财凭类型
	 */
	private int type;
	
	/**
	 * @Description:年份
	 */
	private String nf;
	
	/**
	 * @Description:应收账款或款未付
	 */
	private Double yshzhk;
	
	private int yshzhkB;
	
	/**
	 * @Description:现金收入
	 */
	private Double xianjin;
	
	
	private int xianjinB;
	
	/**
	 * @Description:到付（金额）
	 */
	private Double hdfk;
	
	private int hdfkB;
	
	/**
	 * @Description:银收（金额）
	 */
	private Double yhshr;
	
	private int yhshrB;

	/** 
	 * @Description:出纳姓名
	 */
	private String chuna;

	/**
	 * @Description:办单费
	 */
	private Double bdf;
	
	
	private Double chcf;
	private Double dtf;
	private Double bxf;
	private Double yfhj;
	
	/**
	 * @Description:周期性结款
	 */
	private Double yshk;
	
	/**
	 * @Description:合计金额
	 */
	private BigDecimal hjje;
	
	
	/**
	 * 代收款
	 */
	private BigDecimal dsk;
	
	private int dskB;
	
	private int yshkB;

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

	public String getNf() {
		return nf;
	}

	public void setNf(String nf) {
		this.nf = nf;
	}

	public Double getYshzhk() {
		return yshzhk;
	}

	public void setYshzhk(Double yshzhk) {
		this.yshzhk = yshzhk;
	}

	public int getYshzhkB() {
		return yshzhkB;
	}

	public void setYshzhkB(int yshzhkB) {
		this.yshzhkB = yshzhkB;
	}

	public Double getXianjin() {
		return xianjin;
	}

	public void setXianjin(Double xianjin) {
		this.xianjin = xianjin;
	}

	public int getXianjinB() {
		return xianjinB;
	}

	public void setXianjinB(int xianjinB) {
		this.xianjinB = xianjinB;
	}

	public Double getHdfk() {
		return hdfk;
	}

	public void setHdfk(Double hdfk) {
		this.hdfk = hdfk;
	}

	public int getHdfkB() {
		return hdfkB;
	}

	public void setHdfkB(int hdfkB) {
		this.hdfkB = hdfkB;
	}

	public Double getYhshr() {
		return yhshr;
	}

	public void setYhshr(Double yhshr) {
		this.yhshr = yhshr;
	}

	public int getYhshrB() {
		return yhshrB;
	}

	public void setYhshrB(int yhshrB) {
		this.yhshrB = yhshrB;
	}

	public String getChuna() {
		return chuna;
	}

	public void setChuna(String chuna) {
		this.chuna = chuna;
	}

	public Double getBdf() {
		return bdf;
	}

	public void setBdf(Double bdf) {
		this.bdf = bdf;
	}

	public Double getChcf() {
		return chcf;
	}

	public void setChcf(Double chcf) {
		this.chcf = chcf;
	}

	public Double getDtf() {
		return dtf;
	}

	public void setDtf(Double dtf) {
		this.dtf = dtf;
	}

	public Double getBxf() {
		return bxf;
	}

	public void setBxf(Double bxf) {
		this.bxf = bxf;
	}

	public Double getYfhj() {
		return yfhj;
	}

	public void setYfhj(Double yfhj) {
		this.yfhj = yfhj;
	}


	public BigDecimal getHjje() {
		return hjje;
	}

	public void setHjje(BigDecimal hjje) {
		this.hjje = hjje;
	}

	public BigDecimal getDsk() {
		return dsk;
	}

	public void setDsk(BigDecimal dsk) {
		this.dsk = dsk;
	}

	public int getDskB() {
		return dskB;
	}

	public void setDskB(int dskB) {
		this.dskB = dskB;
	}

	public int getYshkB() {
		return yshkB;
	}

	public void setYshkB(int yshkB) {
		this.yshkB = yshkB;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public Double getYshk() {
		return yshk;
	}

	public void setYshk(Double yshk) {
		this.yshk = yshk;
	}
	
}
