package com.ycgwl.kylin.transport.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 录入客户价格<br> 
 * 客户价格，操作要求--传入参数Dto
 * @author zdl
 * @version [V1.0, 2018年5月16日]
 */
public class CustomerPriceDto implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private String 	account;//账号
	
	/*****录入价格参数*****/
	// 到站
	private String daozhan;
	// 起站
	private String qizhan;
	// 轻货
	private String qhyj;
	// 重货
	private String zhhyj;
	// 按件
	private String ajyj;
	// 轻货
	private String qhzhxfdj;
	// 重货
	private String zhzhxfdj;
	// 重货机械作业费
	private String zhjxzyf;
	// 保率
	private String baolu;
	// 办单费
	private String bdf;
	// 服务方式
	private String fwfs;
	// 走货方式
	private String zhfs;
	// 业务接单号
	private String yewujiedanhao;
	// 执行开始时间
	private Date zhixingkaishiriqi;
	// 执行结束时间
	private Date zhixingjieshuriqi;
	// 备注
	private String beizhu;
	// 登记时间
	private Date djsj;
 
    /***操作要求参数***/
    //结款方式
    private String clearingcode;
    //有无合同
    private Integer ywht;
    //返单要求
    private String fdyq;
    //返单客户单传真件
    private Integer fdfax;
    //返单客户单原件
    private Integer fdkhyj;
    //返单公司签收单
    private String fdqsd;
    //重量
    private String jfzhl;
    //体积
    private String jftiji;
    //其他
    private String jfqt;
    //代收款
    private Integer dsk;
    //客户性质
    private Integer khxz;
    //有效期
    private Date yxq;
    //其他特殊操作说明
    private String czsm;
    //客户编码
    private String khbm;
    
    
	public String getClearingcode() {
		return clearingcode;
	}
	public void setClearingcode(String clearingcode) {
		this.clearingcode = clearingcode;
	}
	public Integer getYwht() {
		return ywht;
	}
	public void setYwht(Integer ywht) {
		this.ywht = ywht;
	}
	public String getFdyq() {
		return fdyq;
	}
	public void setFdyq(String fdyq) {
		this.fdyq = fdyq;
	}
	public Integer getFdfax() {
		return fdfax;
	}
	public void setFdfax(Integer fdfax) {
		this.fdfax = fdfax;
	}
	public Integer getFdkhyj() {
		return fdkhyj;
	}
	public void setFdkhyj(Integer fdkhyj) {
		this.fdkhyj = fdkhyj;
	}
	public String getFdqsd() {
		return fdqsd;
	}
	public void setFdqsd(String fdqsd) {
		this.fdqsd = fdqsd;
	}
	public String getJfzhl() {
		return jfzhl;
	}
	public void setJfzhl(String jfzhl) {
		this.jfzhl = jfzhl;
	}
	public String getJftiji() {
		return jftiji;
	}
	public void setJftiji(String jftiji) {
		this.jftiji = jftiji;
	}
	public String getJfqt() {
		return jfqt;
	}
	public void setJfqt(String jfqt) {
		this.jfqt = jfqt;
	}
	public Integer getDsk() {
		return dsk;
	}
	public void setDsk(Integer dsk) {
		this.dsk = dsk;
	}
	public Integer getKhxz() {
		return khxz;
	}
	public void setKhxz(Integer khxz) {
		this.khxz = khxz;
	}
	public Date getYxq() {
		return yxq;
	}
	public void setYxq(Date yxq) {
		this.yxq = yxq;
	}
	public String getCzsm() {
		return czsm;
	}
	public void setCzsm(String czsm) {
		this.czsm = czsm;
	}
	
	
	public String getDaozhan() {
		return daozhan;
	}

	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}
	 
	public String getQizhan() {
		return qizhan;
	}
	public void setQizhan(String qizhan) {
		this.qizhan = qizhan;
	}
	public String getQhyj() {
		return qhyj;
	}

	public void setQhyj(String qhyj) {
		this.qhyj = qhyj;
	}

	public String getZhhyj() {
		return zhhyj;
	}

	public void setZhhyj(String zhhyj) {
		this.zhhyj = zhhyj;
	}

	public String getAjyj() {
		return ajyj;
	}

	public void setAjyj(String ajyj) {
		this.ajyj = ajyj;
	}

	public String getQhzhxfdj() {
		return qhzhxfdj;
	}

	public void setQhzhxfdj(String qhzhxfdj) {
		this.qhzhxfdj = qhzhxfdj;
	}

	public String getZhzhxfdj() {
		return zhzhxfdj;
	}

	public void setZhzhxfdj(String zhzhxfdj) {
		this.zhzhxfdj = zhzhxfdj;
	}

	public String getZhjxzyf() {
		return zhjxzyf;
	}

	public void setZhjxzyf(String zhjxzyf) {
		this.zhjxzyf = zhjxzyf;
	}

	public String getBaolu() {
		return baolu;
	}

	public void setBaolu(String baolu) {
		this.baolu = baolu;
	}

	public String getBdf() {
		return bdf;
	}

	public void setBdf(String bdf) {
		this.bdf = bdf;
	}

	public String getFwfs() {
		return fwfs;
	}

	public void setFwfs(String fwfs) {
		this.fwfs = fwfs;
	}

	public String getZhfs() {
		return zhfs;
	}

	public void setZhfs(String zhfs) {
		this.zhfs = zhfs;
	}

	public String getYewujiedanhao() {
		return yewujiedanhao;
	}

	public void setYewujiedanhao(String yewujiedanhao) {
		this.yewujiedanhao = yewujiedanhao;
	}

	public Date getZhixingkaishiriqi() {
		return zhixingkaishiriqi;
	}

	public void setZhixingkaishiriqi(Date zhixingkaishiriqi) {
		this.zhixingkaishiriqi = zhixingkaishiriqi;
	}

	public Date getZhixingjieshuriqi() {
		return zhixingjieshuriqi;
	}

	public void setZhixingjieshuriqi(Date zhixingjieshuriqi) {
		this.zhixingjieshuriqi = zhixingjieshuriqi;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public Date getDjsj() {
		return djsj;
	}

	public void setDjsj(Date djsj) {
		this.djsj = djsj;
	}
	public String getKhbm() {
		return khbm;
	}
	public void setKhbm(String khbm) {
		this.khbm = khbm;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
   
	
     
}
