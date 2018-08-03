package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * FinanceReceiveMoneyPrint
 * 
 * @Description: 财凭金额打印信息
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @date 2017年10月27日
 */
public class FinanceReceiveMoneyPrint implements Serializable{
	
	private static final long serialVersionUID = -8749864302691288683L;

	public static BigDecimal getSacleBigDecimal(Double num,int digestCount){
		BigDecimal a = new BigDecimal(num);
		return a.setScale(digestCount, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * @Description:保险费
	 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
	 * @createDate 2017-10-29 10:06:16
	 * @return com_bx
	 */
	public Double calcInsuranceExpense(){
		if (this.getYdly()==1) {
			if(this.getBaolu()*this.getTbje()<1){
				return Math.ceil(getBaolu()*getTbje());
			}else{
				return Math.round(getBaolu()*getTbje()*100)/100.00;
			}
		}else{
			if(getJshhj()<1){
				return Math.round(getBaolu() * getTbje())*1.00;
			}else{
				return Math.round(getBaolu() * getTbje()*100)/100.00;
			}
		}
	}
	
	/**
	 * @Description:保险费
	 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
	 * @createDate 2017-10-29 10:06:16
	 * @return com_bx
	 */
	public Double calcInsuranceExpense(Double tbje,Double baolu){
		if (this.getYdly()==1) {
			if(this.baolu*tbje<1){
				return Math.ceil(baolu*tbje);
			}else{
				return Math.round(baolu*tbje*100)/100.00;
			}
		}else{
			if(getJshhj()<1){
				return Math.round(baolu * tbje)*1.00;
			}else{
				return Math.round(baolu *tbje*100)/100.00;
			}
		}
	}


	/**
	 * @Description:运杂费合计计算
	 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
	 * @createDate 2017-10-27 14:36:16
	 * @transportSum 运费合计
	 * @return com_qthj
	 */
	public Double calcSumTransportFees(Double forkliftCharge,Double lightLoadingFee,Double insuranceExpense,Double heavyLoadingFee,Double transportSum) {
		//  com_chc  +  com_zhx  +  com_zhzhx  +  com_bx +  if (jshhj<1,int( fiwt_shshmf  +  fiwt_shmshhf + fkfsh_qtfy +0.5),  fiwt_shshmf  +  fiwt_shmshhf + fkfsh_qtfy)  +  fiwt_bdf+ com_yfhj  
		Double sum = forkliftCharge + lightLoadingFee + insuranceExpense + heavyLoadingFee;
		if (this.jshhj < 1) {
			sum += Math.floor(getShshmf() + getShmshhf() + getQtfy() + 0.5);
		} else {
			sum += getShshmf() + getShmshhf() + getQtfy();
		}
		sum += getBdf() + transportSum;
		return sum;
	}
	
	public Double multiply(Double num1,Double num2) {
		return new BigDecimal(num1.toString()).multiply(new BigDecimal(num2.toString())).doubleValue();
	}
	
	/**
	 * @Description:运费合计
	 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
	 * @createDate 2017-10-27 14:36:16
	 * @return com_yfhj
	 */
	public Double calcTotalTransport(){
		//  if (jshhj<1,
		//round(sum ( if ( cwpzhxz_jffs =0,( cwpzhxz_zhl  *  fiwt_zhhyj ), 
							//if (cwpzhxz_jffs=1,(cwpzhxz_tiji *  fiwt_qhyj )
							//,( fiwt_ajyj  *  cwpzhxz_jianshu ))
					  //) for all ),0)
		//,round(sum ( if ( cwpzhxz_jffs =0,( cwpzhxz_zhl  *  fiwt_zhhyj ), 
						//if (cwpzhxz_jffs=1,(cwpzhxz_tiji *  fiwt_qhyj ),( fiwt_ajyj  *  cwpzhxz_jianshu )) ) for all ),2)) -  if (isnull(fkfsh_youhuijine),  0 , fkfsh_youhuijine)
		
		Double result = new Double(0.0);
		if(this.jshhj < 1){
			if(this.getJffs()==0){
				result =  multiply(this.getZhl(),this.getZhhyj());
			}else if(this.getJffs()==1){
				result = multiply(this.getTiji(),this.getQhyj());
			}else {
				result = multiply(this.getAjyj(),this.getJianshu().doubleValue());
			}
			result = Math.round(result)*1.00;
		}else{
			if(this.getJffs()==0){
				result =  multiply(this.getZhl(),this.getZhhyj());
			}else if(this.getJffs()==1){
				result = multiply(this.getTiji(),this.getQhyj());
			}else {
				result = multiply(this.getAjyj(),this.getJianshu().doubleValue());
			}
			result = Math.round(result*100)/100.00;
		}
		return result;//-plus
	}
	
	/**
	 * @Description:运输受理单合计金额
	 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
	 * @createDate 2017-10-27 14:36:16
	 * @return com_hj
	 */
	public Double calcTransportationAcceptanceSheet(Double forkliftCharge,Double lightLoadingFee,Double insuranceExpense,Double heavyLoadingFee,Double transportSum){
		// com_qthj  +  if (isnull(fiwt_dsk ),  0 ,   fiwt_dsk  )
		return calcSumTransportFees(forkliftCharge,lightLoadingFee,insuranceExpense,heavyLoadingFee,transportSum) +getDsk();
	}
	
	
	/**
	 * @Description:装卸费（轻）
	 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
	 * @createDate 2017-10-27 14:36:16
	 * @return com_zhx
	 */
	public Double calcLightLoadingFee(){
		//  if (jshhj <1 ,round( fiwt_qhzhxfdj  * sum(   cwpzhxz_qzxl   for all ),0) , fiwt_qhzhxfdj  * sum(   cwpzhxz_qzxl   for all )) 
		Double sum = new Double(0.0);
		if(this.getJshhj() < 1){
			sum += Math.round(this.getQhzhxfdj()  * this.getQzxl());
		}else{
			sum += this.getQhzhxfdj() * this.getQzxl();
		}
		return sum;
	}
	
	/**
	 * @Description:装卸费（重）
	 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
	 * @createDate 2017-10-27 14:36:16
	 * @return com_zhzhx
	 */
	public Double calcHeavyLoadingFee(){
		// if (jshhj<1,round( fiwt_zhzhxfdj  * sum(  cwpzhxz_zzxl  for all ),0),fiwt_zhzhxfdj  * sum(  cwpzhxz_zzxl  for all )) 
		Double sum = new Double(0.0);
		if(this.getJshhj() < 1){
			sum += Math.round(this.getZhzhxfdj()  * this.getZzxl());
		}else{
			sum += this.getZhzhxfdj() * this.getZzxl();
		}
		return sum;
	}
	
	/**
	 * @Description:叉车费
	 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
	 * @createDate 2017-10-29 09:36:16
	 * @return com_chc
	 */
	public Double calcForkliftCharge(){
		// if  ( jshhj <1, round( fiwt_zhjxzyf * ( sum (  cwpzhxz_zchl for all) + sum (  cwpzhxz_qchl for all ) ),0)   ,   fiwt_zhjxzyf * ( sum (  cwpzhxz_zchl for all) + sum (  cwpzhxz_qchl for all ) )) 
		Double sum = new Double(0.0);
		if(this.getJshhj() < 1){
			sum += Math.round(this.getZhjxzyf() * (this.getZchl() + this.getQchl()));
		}else{
			sum += this.getZhjxzyf() * (this.getZchl() + this.getQchl());
		}
		return sum;
	}
	
	/**
	 * @Description:总金额小写
	 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
	 * @createDate 2017-10-27 14:36:16
	 * @return com_hj
	 */
	public Double totalAmountOfLowerCase(Double forkliftCharge,Double lightLoadingFee,Double insuranceExpense,Double heavyLoadingFee,Double transportSum){
		return (this.dsk==null?0:this.dsk) + calcSumTransportFees(forkliftCharge,lightLoadingFee,insuranceExpense,heavyLoadingFee,transportSum);
	}
	
	/**
	 *	合计金额
	 */
	public Double totalFee(){
		Double forkliftCharge = calcForkliftCharge();
		Double lightLoadingFee = calcLightLoadingFee();
		Double insuranceExpense = calcInsuranceExpense();
		Double heavyLoadingFee = calcHeavyLoadingFee();
		Double transportSum = calcTotalTransport();
		return (this.dsk==null?0:this.dsk) + calcSumTransportFees(forkliftCharge,lightLoadingFee,insuranceExpense,heavyLoadingFee,transportSum);
	}
	
	private String grid;
	private String nf;
	private String xianlu;
	private Integer type;
	private String pinming;
	private Long cwpzhbh;
	private Double zhjxzyf;
	private Double zchl;
	private Double dsk;
	private Double qchl;
	private Double qhzhxfdj;
	private Double qhyj;
	private Double zhhyj;
	private Double youhuijine;
	private Double zhl;
	private Double shmshhf;
	private Double zhzhxfdj;
	private Integer jshhj;
	private Double shshmf;
	private Double zzxl;
	private Double qzxl;
	private Double ajyj;
	private Integer jffs;
	private Double tiji;
	private Double bdf;
	private Double yfhj;
	private Integer jianshu;
	private Double qtfy;
	private String fazhan;
	private Double xianjin;
	private Double hdfk;
	private Double yhshr;
	private Double qthj;
	private Double hjje;
	private String qtfymch;
	private Date fhshj;
	private Double yshzhk;
	private String fhdwmch;
	private String fhdwlxdh;
	private String fhdwdzh;
	private String shhrmch;
	private String shhrlxdh;
	private String shhrdzh;
	private String daozhan;
	private String yshhm;
	private String fhdwyb;
	private String shhryb;
	private String chuna;
	private String zhipiao;
	private Double baolu;
	private String ydbhid;
	private String sqshj;
	private Double tbje;
	private String shhd;
	private Double fwfs;
	private String fpmc;
	private String swzh;
	private Double yshk;
	private Integer yshkb;
	private Integer yshzhkb;
	private String fdnr;
	private String fdlc;
	private String ysfs;
	private Integer dzfk;
	private String fpid;
	private String ywlx;
	private String beizhu;
	private Integer fzfk;
	private String dzshhd;
	private Integer ydly;

	public Double getXianjin() {
		return xianjin;
	}

	public void setXianjin(Double xianjin) {
		this.xianjin = xianjin;
	}

	public Double getHdfk() {
		return hdfk;
	}

	public void setHdfk(Double hdfk) {
		this.hdfk = hdfk;
	}

	public Double getYhshr() {
		return yhshr!=null?yhshr:0.00;
	}

	public void setYhshr(Double yhshr) {
		this.yhshr = yhshr;
	}

	public Double getQthj() {
		return qthj!=null?qthj:0.00;
	}

	public void setQthj(Double qthj) {
		this.qthj = qthj;
	}

	public Double getHjje() {
		return hjje!=null?hjje:0.00;
	}

	public void setHjje(Double hjje) {
		this.hjje = hjje;
	}

	public String getQtfymch() {
		return qtfymch;
	}

	public void setQtfymch(String qtfymch) {
		this.qtfymch = qtfymch;
	}

	public Date getFhshj() {
		return fhshj;
	}

	public void setFhshj(Date fhshj) {
		this.fhshj = fhshj;
	}

	public Double getYshzhk() {
		return yshzhk!=null?yshzhk:0.00;
	}

	public void setYshzhk(Double yshzhk) {
		this.yshzhk = yshzhk;
	}

	public String getFhdwmch() {
		return fhdwmch;
	}

	public void setFhdwmch(String fhdwmch) {
		this.fhdwmch = fhdwmch;
	}

	public String getFhdwlxdh() {
		return fhdwlxdh;
	}

	public void setFhdwlxdh(String fhdwlxdh) {
		this.fhdwlxdh = fhdwlxdh;
	}

	public String getFhdwdzh() {
		return fhdwdzh;
	}

	public void setFhdwdzh(String fhdwdzh) {
		this.fhdwdzh = fhdwdzh;
	}

	public String getShhrmch() {
		return shhrmch;
	}

	public void setShhrmch(String shhrmch) {
		this.shhrmch = shhrmch;
	}

	public String getShhrlxdh() {
		return shhrlxdh;
	}

	public void setShhrlxdh(String shhrlxdh) {
		this.shhrlxdh = shhrlxdh;
	}

	public String getShhrdzh() {
		return shhrdzh;
	}

	public void setShhrdzh(String shhrdzh) {
		this.shhrdzh = shhrdzh;
	}

	public String getDaozhan() {
		return daozhan;
	}

	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}

	public String getYshhm() {
		return yshhm;
	}

	public void setYshhm(String yshhm) {
		this.yshhm = yshhm;
	}

	public String getFhdwyb() {
		return fhdwyb;
	}

	public void setFhdwyb(String fhdwyb) {
		this.fhdwyb = fhdwyb;
	}

	public String getShhryb() {
		return shhryb;
	}

	public void setShhryb(String shhryb) {
		this.shhryb = shhryb;
	}

	public String getChuna() {
		return chuna;
	}

	public void setChuna(String chuna) {
		this.chuna = chuna;
	}

	public String getZhipiao() {
		return zhipiao;
	}

	public void setZhipiao(String zhipiao) {
		this.zhipiao = zhipiao;
	}

	public Double getBaolu() {
		return baolu!=null?baolu:0.00;
	}

	public void setBaolu(Double baolu) {
		this.baolu = baolu;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public String getSqshj() {
		return sqshj;
	}

	public void setSqshj(String sqshj) {
		this.sqshj = sqshj;
	}

	public Double getTbje() {
		return tbje!=null?tbje:0.00;
	}

	public void setTbje(Double tbje) {
		this.tbje = tbje;
	}

	public String getShhd() {
		return shhd;
	}

	public void setShhd(String shhd) {
		this.shhd = shhd;
	}

	public Double getFwfs() {
		return fwfs!=null?fwfs:0.00;
	}

	public void setFwfs(Double fwfs) {
		this.fwfs = fwfs;
	}

	public String getFpmc() {
		return fpmc;
	}

	public void setFpmc(String fpmc) {
		this.fpmc = fpmc;
	}

	public String getSwzh() {
		return swzh;
	}

	public void setSwzh(String swzh) {
		this.swzh = swzh;
	}

	public Double getYshk() {
		return yshk!=null?yshk:0.00;
	}

	public void setYshk(Double yshk) {
		this.yshk = yshk;
	}

	public Integer getYshkb() {
		return yshkb!=null?yshkb:0;
	}

	public void setYshkb(Integer yshkb) {
		this.yshkb = yshkb;
	}

	public Integer getYshzhkb() {
		return yshzhkb!=null?yshzhkb:0;
	}

	public void setYshzhjb(Integer yshzhkb) {
		this.yshzhkb = yshzhkb;
	}

	public String getFdnr() {
		return fdnr;
	}

	public void setFdnr(String fdnr) {
		this.fdnr = fdnr;
	}

	public String getFdlc() {
		return fdlc;
	}

	public void setFdlc(String fdlc) {
		this.fdlc = fdlc;
	}

	public String getYsfs() {
		return ysfs;
	}

	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}

	public Integer getDzfk() {
		return dzfk!=null?dzfk:0;
	}

	public void setDzfk(Integer dzfk) {
		this.dzfk = dzfk;
	}

	public String getFpid() {
		return fpid;
	}

	public void setFpid(String fpid) {
		this.fpid = fpid;
	}

	public String getYwlx() {
		return ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public Integer getFzfk() {
		return fzfk!=null?fzfk:0;
	}

	public void setFzfk(Integer fzfk) {
		this.fzfk = fzfk;
	}

	public String getDzshhd() {
		return dzshhd;
	}

	public void setDzshhd(String dzshhd) {
		this.dzshhd = dzshhd;
	}

	public Integer getYdly() {
		return ydly!=null?ydly:0;
	}

	public void setYdly(Integer ydly) {
		this.ydly = ydly;
	}

	public String getFazhan() {
		return fazhan;
	}

	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}

	public String getNf() {
		return nf;
	}

	public void setNf(String nf) {
		this.nf = nf;
	}

	public String getXianlu() {
		return xianlu;
	}

	public void setXianlu(String xianlu) {
		this.xianlu = xianlu;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCwpzhbh() {
		return cwpzhbh;
	}

	public void setCwpzhbh(Long cwpzhbh) {
		this.cwpzhbh = cwpzhbh;
	}

	public Double getZhjxzyf() {
		return zhjxzyf!=null?zhjxzyf:0.00;
	}

	public void setZhjxzyf(Double zhjxzyf) {
		this.zhjxzyf = zhjxzyf;
	}

	public Double getZchl() {
		return zchl!=null?zchl:0.00;
	}

	public void setZchl(Double zchl) {
		this.zchl = zchl;
	}

	public Double getDsk() {
		return dsk!=null?dsk:0.00;
	}

	public void setDsk(Double dsk) {
		this.dsk = dsk;
	}

	public Double getQchl() {
		return qchl!=null?qchl:0.00;
	}

	public void setQchl(Double qchl) {
		this.qchl = qchl;
	}

	public Double getQhzhxfdj() {
		return qhzhxfdj!=null?qhzhxfdj:0.00;
	}

	public void setQhzhxfdj(Double qhzhxfdj) {
		this.qhzhxfdj = qhzhxfdj;
	}

	public Double getQhyj() {
		return qhyj!=null?qhyj:0.00;
	}

	public void setQhyj(Double qhyj) {
		this.qhyj = qhyj;
	}

	public Double getZhhyj() {
		return zhhyj!=null?zhhyj:0.00;
	}

	public void setZhhyj(Double zhhyj) {
		this.zhhyj = zhhyj;
	}

	public Double getYouhuijine() {
		return youhuijine!=null?youhuijine:0.00;
	}

	public void setYouhuijine(Double youhuijine) {
		this.youhuijine = youhuijine;
	}

	public Double getZhl() {
		return zhl!=null?zhl:0.00;
	}

	public void setZhl(Double zhl) {
		this.zhl = zhl;
	}

	public Double getShmshhf() {
		return shmshhf!=null?shmshhf:0.00;
	}

	public void setShmshhf(Double shmshhf) {
		this.shmshhf = shmshhf;
	}

	public Double getZhzhxfdj() {
		return zhzhxfdj!=null?zhzhxfdj:0.00;
	}

	public void setZhzhxfdj(Double zhzhxfdj) {
		this.zhzhxfdj = zhzhxfdj;
	}

	public Integer getJshhj() {
		return jshhj!=null?jshhj:0;
	}

	public void setJshhj(Integer jshhj) {
		this.jshhj = jshhj;
	}

	public Double getShshmf() {
		return shshmf!=null?shshmf:0.00;
	}

	public void setShshmf(Double shshmf) {
		this.shshmf = shshmf;
	}

	public Double getZzxl() {
		return zzxl!=null?zzxl:0.00;
	}

	public void setZzxl(Double zzxl) {
		this.zzxl = zzxl;
	}

	public Double getQzxl() {
		return qzxl!=null?qzxl:0.00;
	}

	public void setQzxl(Double qzxl) {
		this.qzxl = qzxl;
	}

	public Double getAjyj() {
		return ajyj!=null?ajyj:0.00;
	}

	public void setAjyj(Double ajyj) {
		this.ajyj = ajyj;
	}

	public Integer getJffs() {
		return jffs!=null?jffs:0;
	}

	public void setJffs(Integer jffs) {
		this.jffs = jffs;
	}

	public Double getTiji() {
		return tiji!=null?tiji:0.00;
	}

	public void setTiji(Double tiji) {
		this.tiji = tiji;
	}

	public Double getBdf() {
		return bdf!=null?bdf:0.00;
	}

	public void setBdf(Double bdf) {
		this.bdf = bdf;
	}

	public Double getYfhj() {
		return yfhj!=null?yfhj:0.00;
	}

	public void setYfhj(Double yfhj) {
		this.yfhj = yfhj;
	}

	public Integer getJianshu() {
		return jianshu!=null?jianshu:0;
	}

	public void setJianshu(Integer jianshu) {
		this.jianshu = jianshu;
	}

	public Double getQtfy() {
		return qtfy!=null?qtfy:0.00;
	}

	public void setQtfy(Double qtfy) {
		this.qtfy = qtfy;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getPinming() {
		return pinming;
	}

	public void setPinming(String pinming) {
		this.pinming = pinming;
	}
}
