package com.ycgwl.kylin.transport.entity.adjunct;


import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 
 * 客户价格表<br>
 * 
 * @author zdl
 * @version [V1.0, 2018年5月16日]
 */
public class CustomerPrice extends BaseEntity {

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	//起站
	private String qizhan;
    //客户编码
	private String khbm;
	// 到站
	private String daozhan;
	// 轻货(单价)
	private String qhyj;
	// 重货(单价)
	private String zhhyj;
	// 按件(单价)
	private String ajyj;
	// 轻货(装卸费)
	private String qhzhxfdj;
	// 重货(装卸费)
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
	private String zhixingkaishiriqi;
	// 执行结束时间
	private String zhixingjieshuriqi;
	// 备注
	private String beizhu;
	// 登记时间
	private String djsj;

	public String getDaozhan() {
		return daozhan;
	}

	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
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

	public String getZhixingkaishiriqi() {
		return zhixingkaishiriqi;
	}

	public void setZhixingkaishiriqi(String zhixingkaishiriqi) {
		this.zhixingkaishiriqi = zhixingkaishiriqi;
	}

	public String getZhixingjieshuriqi() {
		return zhixingjieshuriqi;
	}

	public void setZhixingjieshuriqi(String zhixingjieshuriqi) {
		this.zhixingjieshuriqi = zhixingjieshuriqi;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getDjsj() {
		return djsj;
	}

	public void setDjsj(String djsj) {
		this.djsj = djsj;
	}

	public String getKhbm() {
		return khbm;
	}

	public void setKhbm(String khbm) {
		this.khbm = khbm;
	}

	public String getQizhan() {
		return qizhan;
	}

	public void setQizhan(String qizhan) {
		this.qizhan = qizhan;
	}
	
   
}
