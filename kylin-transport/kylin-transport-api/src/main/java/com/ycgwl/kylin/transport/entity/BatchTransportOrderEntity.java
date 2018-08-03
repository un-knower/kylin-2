package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 
 * @Description: 批量导入运单的实体类
 * 	截止2017年10月9日 15:42:08 有56个字段
 * @author <a href="mailto:108252@ycgwl.com">万玉杰</a>
 * @date 2017年10月9日 下午2:19:12
 * @version 需求对应版本号
 *
 */
public class BatchTransportOrderEntity extends BaseEntity {

	private static final long serialVersionUID = -3802736687719692637L;
	//异常运单
	/** 异常的字段 */
	private String errorField;
	/** 错误信息 */
	private String errorMsg;
	//基本信息
	/** 运单编号 */
	private String ydbhid;
	/** 托运时间 */
	private String fhshj;
	/** 录单员 */
	private String zhipiao;
	/** 始发站 */
	private String fazhan;
	/** 到站 */
	private String daozhan;
	/** 始发地 */
	private String beginPlacename;
	/** 目的地 */
	private String endPlacename;
	
	/** 发站网点 */
	private String shhd;
	/** 到站网点 */
	private String dzshhd;
	//客户信息
	/** 客户名称 */
	private String fhdwmch;
	/** 客户编码 */
	private String khbm;
	/** 客户地址 */
	private String fhdwdzh;
	/** 固话 */
	private String fhdwlxdh;
	/** 手机号 */
	private String fhdwyb;
	/** 行业类别 */
	private String fhkhhy;
	//收货人信息
	/** 名称 */
	private String shhrmch;
	/** 手机 */
	private String shhryb;
	/** 省市区  此处需要注意: 在后台中截取该字符串分别保存在省、市、区中 */
	private String shrProvinces;	
	/** 地址  */
	private String shhrdzh;
	//发运信息
	/** 服务方式 */
	private String fwfs;
	/** 承运人 */
	private String hyy;
	/** 等待托运人指令发货 */
	private Integer releaseWaiting;
	/** 运输方式 */
	private String ysfs;
	/** 运输天数 */
	private Integer daodatianshu;
	/** 是否返单 */
	private String isfd;
	/** 返单要求 */
	private String fdyq;
	/** 付费方式 :１　到付，０　发付*/
	private String fffs;
	/**到站付款 */
	private Integer dzfk = 0;
	/**发站付款   ???   此处需要确定为什么到站付款和发站付款是复选框 */
	private Integer fzfk = 0;
	/** 保险费 */
	private Double baoxianfei;
	/** 包装费 */
	private Double baozhuangfei;
	/** 装卸费 */
	private Double zhuangxiefei;
	/** 办单费 */
	private Double bandanfei; 
	/**客户单号 */
	private String khdh;
	//运单明细
	/** 序号 */
	private Integer ydxzh;
	/** 品名 */
	private String pinming;
	/** 型号 */ 
	private String xh;	
	/** 件数 */
	private Integer jianshu;
	/** 包装 */
	private String bzh;
	/** 重量 */
	private Double zhl;	
	/** 体积 */
	private Double tiji;
	/** 保价金额 */
	private Double tbje;
	/** 计费方式 */
	private String jffs;
	/** 运价 */
	private Double yunjia;
	//财凭
	/** 重货运价 */
	private Double weightprice;
	/** 轻货运价 */
	private Double lightprice;
	/** 按件运价 */
	private Double piecework;
	/** 代收货款 */
	private Double receipt;
	/** 办单费 */
	private Double invoice;	
	/** 上门取货费 */ 
	private Double tohome;
	/** 送货上门费 */
	private Double delivery;
	/** 其他费用 */ 
	private Double other;
	/** 合计费用 */
	private Double cost;
	/**	成本号    //财凭号 */
	private String insNo;
	/**是不是新文件:  意思是是否是第一条数据*/
	private Boolean isNewDoc;
	/** 18-02-05 新增加特别说明的字段*/
	private String tiebieshuoming;
	
	//新加字段
	/** 保险费率 */
	private Double premiumRate;
	/** 是否录入财务凭证 */
	private String withFinance;
	/** 是否取整 */
	private String isRound;
	
	/** 是否等拖指令 */
	public String isReleaseWaiting;
	
	public String getShhd() {
		return shhd;
	}
	public void setShhd(String shhd) {
		this.shhd = shhd;
	}
	public String getJffs() {
		return jffs;
	}
	public void setJffs(String jffs) {
		this.jffs = jffs;
	}
	public String getIsRound() {
		return isRound;
	}
	public void setIsRound(String isRound) {
		this.isRound = isRound;
	}
	public Double getPremiumRate() {
		return premiumRate==null?0.0:premiumRate;
	}
	public void setPremiumRate(Double premiumRate) {
		this.premiumRate = premiumRate;
	}
	public String getWithFinance() {
		return withFinance;
	}
	public void setWithFinance(String withFinance) {
		this.withFinance = withFinance;
	}
	public String getTiebieshuoming() {
		return tiebieshuoming;
	}
	public void setTiebieshuoming(String tiebieshuoming) {
		this.tiebieshuoming = tiebieshuoming;
	}
	public void setIsNewDoc(Boolean isNewDoc) {
		this.isNewDoc = isNewDoc;
	}
	public Boolean getIsNewDoc() {
		return isNewDoc;
	}
	public String getInsNo() {
		return insNo;
	}
	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}
	public String getHyy() {
		return hyy;
	}
	public void setHyy(String hyy) {
		this.hyy = hyy;
	}
	public Integer getReleaseWaiting() {
		return releaseWaiting;
	}
	public void setReleaseWaiting(Integer releaseWaiting) {
		this.releaseWaiting = releaseWaiting;
	}
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public String getFhshj() {
		return fhshj;
	}
	public void setFhshj(String fhshj) {
		this.fhshj = fhshj;
	}
	public String getZhipiao() {
		return zhipiao;
	}
	public void setZhipiao(String zhipiao) {
		this.zhipiao = zhipiao;
	}
	public String getFazhan() {
		return fazhan;
	}
	public void setFazhan(String fazhan) {
		this.fazhan = fazhan;
	}
	public String getDaozhan() {
		return daozhan;
	}
	public void setDaozhan(String daozhan) {
		this.daozhan = daozhan;
	}
	public String getBeginPlacename() {
		return beginPlacename;
	}
	public void setBeginPlacename(String beginPlacename) {
		this.beginPlacename = beginPlacename;
	}
	public String getEndPlacename() {
		return endPlacename;
	}
	public void setEndPlacename(String endPlacename) {
		this.endPlacename = endPlacename;
	}
	public String getFhdwmch() {
		return fhdwmch;
	}
	public void setFhdwmch(String fhdwmch) {
		this.fhdwmch = fhdwmch;
	}
	public String getKhbm() {
		return khbm;
	}
	public void setKhbm(String khbm) {
		this.khbm = khbm;
	}
	public String getFhdwdzh() {
		return fhdwdzh;
	}
	public void setFhdwdzh(String fhdwdzh) {
		this.fhdwdzh = fhdwdzh;
	}
	public String getFhdwyb() {
		return fhdwyb;
	}
	public void setFhdwyb(String fhdwyb) {
		this.fhdwyb = fhdwyb;
	}
	public String getFhkhhy() {
		return fhkhhy;
	}
	public void setFhkhhy(String fhkhhy) {
		this.fhkhhy = fhkhhy;
	}
	public String getShhrmch() {
		return shhrmch;
	}
	public void setShhrmch(String shhrmch) {
		this.shhrmch = shhrmch;
	}
	public String getShhryb() {
		return shhryb;
	}
	public void setShhryb(String shhryb) {
		this.shhryb = shhryb;
	}
	public String getShrProvinces() {
		return shrProvinces;
	}
	public void setShrProvinces(String shrProvinces) {
		this.shrProvinces = shrProvinces;
	}
	public String getShhrdzh() {
		return shhrdzh;
	}
	public void setShhrdzh(String shhrdzh) {
		this.shhrdzh = shhrdzh;
	}
	public String getFwfs() {
		return fwfs;
	}
	public void setFwfs(String fwfs) {
		this.fwfs = fwfs;
	}
	public String getYsfs() {
		return ysfs;
	}
	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}
	public Integer getDaodatianshu() {
		return daodatianshu;
	}
	public void setDaodatianshu(Integer daodatianshu) {
		this.daodatianshu = daodatianshu;
	}
	public String getIsfd() {
		return isfd;
	}
	public void setIsfd(String isfd) {
		this.isfd = isfd;
	}
	public String getFdyq() {
		return fdyq;
	}
	public void setFdyq(String fdyq) {
		this.fdyq = fdyq;
	}
	public Integer getDzfk() {
		return dzfk;
	}
	public Integer getFzfk() {
		return fzfk;
	}
	
	public void setZhuangxiefei(Double zhuangxiefei) {
		this.zhuangxiefei = zhuangxiefei;
	}
	
	public void setBandanfei(Double bandanfei) {
		this.bandanfei = bandanfei;
	}
	public String getKhdh() {
		return khdh;
	}
	public void setKhdh(String khdh) {
		this.khdh = khdh;
	}
	public Integer getYdxzh() {
		return ydxzh;
	}
	public void setYdxzh(Integer ydxzh) {
		this.ydxzh = ydxzh;
	}
	public String getPinming() {
		return pinming;
	}
	public void setPinming(String pinming) {
		this.pinming = pinming;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public Integer getJianshu() {
		return jianshu;
	}
	public void setJianshu(Integer jianshu) {
		this.jianshu = jianshu;
	}
	public String getBzh() {
		return bzh;
	}
	public void setBzh(String bzh) {
		this.bzh = bzh;
	}
	
	public Double getYunjia() {
		return yunjia!=null?yunjia:0.00;
	}
	public void setYunjia(Double yunjia) {
		this.yunjia = yunjia;
	}
	public String getFffs() {
		return fffs;
	}
	public void setFffs(String fffs) {
		/** 付费方式 :１　到付，０　发付*/
		this.fffs = fffs;
		switch (fffs) {
		case "到付":
			this.dzfk = 1 ;
			break;
		case "发付":
			this.fzfk = 1;
			break;
		}
	}
	
	public Double getZhl() {
		return zhl!=null?zhl:0.00;
	}
	public void setZhl(Double zhl) {
		this.zhl = zhl;
	}
	public Double getTiji() {
		return tiji!=null?tiji:0.00;
	}
	public void setTiji(Double tiji) {
		this.tiji = tiji;
	}
	public Double getTbje() {
		return tbje!=null?tbje:0.00;
	}
	public void setTbje(Double tbje) {
		this.tbje = tbje;
	}
	public void setWeightprice(Double weightprice) {
		this.weightprice = weightprice;
	}
	
	public void setLightprice(Double lightprice) {
		this.lightprice = lightprice;
	}
	
	public void setPiecework(Double piecework) {
		this.piecework = piecework;
	}
	
	public void setReceipt(Double receipt) {
		this.receipt = receipt;
	}
	
	public void setInvoice(Double invoice) {
		this.invoice = invoice;
	}
	
	public void setTohome(Double tohome) {
		this.tohome = tohome;
	}

	public void setDelivery(Double delivery) {
		this.delivery = delivery;
	}
	
	public void setOther(Double other) {
		this.other = other;
	}
	public void setDzfk(Integer dzfk) {
		this.dzfk = dzfk;
	}
	public void setFzfk(Integer fzfk) {
		this.fzfk = fzfk;
	}
	
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public  String getErrorField() {
		return errorField;
	}
	public void setErrorField( String errorField) {
		this.errorField = errorField;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getDzshhd() {
		return dzshhd;
	}
	public void setDzshhd(String dzshhd) {
		this.dzshhd = dzshhd;
	}
	public String getFhdwlxdh() {
		return fhdwlxdh;
	}
	public void setFhdwlxdh(String fhdwlxdh) {
		this.fhdwlxdh = fhdwlxdh;
	}
	public Double getBaoxianfei() {
		return baoxianfei!=null?baoxianfei:0.00;
	}
	public void setBaoxianfei(Double baoxianfei) {
		this.baoxianfei = baoxianfei;
	}
	public Double getBaozhuangfei() {
		return baozhuangfei!=null?baozhuangfei:0.00;
	}
	public void setBaozhuangfei(Double baozhuangfei) {
		this.baozhuangfei = baozhuangfei;
	}
	public Double getZhuangxiefei() {
		return zhuangxiefei!=null?zhuangxiefei:0.00;
	}
	public Double getBandanfei() {
		return bandanfei!=null?bandanfei:0.00;
	}
	public Double getWeightprice() {
		return weightprice!=null?weightprice:0.00;
	}
	public Double getLightprice() {
		return lightprice!=null?lightprice:0.00;
	}
	public Double getPiecework() {
		return piecework!=null?piecework:0.00;
	}
	public Double getReceipt() {
		return receipt!=null?receipt:0.00;
	}
	public Double getInvoice() {
		return invoice!=null?invoice:0.00;
	}
	public Double getTohome() {
		return tohome!=null?tohome:0.00;
	}
	public Double getDelivery() {
		return delivery!=null?delivery:0.00;
	}
	public Double getOther() {
		return other!=null?other:0.00;
	}
	public Double getCost() {
		return cost!=null?cost:0.00;
	}
	public String getIsReleaseWaiting() {
		return isReleaseWaiting;
	}
	public void setIsReleaseWaiting(String isReleaseWaiting) {
		this.isReleaseWaiting = isReleaseWaiting;
	}
}

