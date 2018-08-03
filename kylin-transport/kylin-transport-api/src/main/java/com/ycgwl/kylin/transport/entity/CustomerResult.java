package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;
/**
 * 结果集
 * 客户基本信息<br> 
 * @author zdl
 * @version [V1.0, 2018年5月14日]
 */
public class CustomerResult implements Serializable{

	/**
	 */
	private static final long serialVersionUID = 1L;
	/********客户基本信息*************/
    private String khmc;//客户名称
    private String khlxr;//联系人
    private String hwmch;//货物名称
    private String khbm;//客户编码
    private String khtxdz;//通讯地址
    private String isfd;//是否返单
    private String khdh;//电话
    private String zxshj;//开始合作时间zxshj_b
    private String fkfs;//付款方式
    private String hylb;//行业类别 hwlx
    private String fdyq; //返单要求
    private String hwysfs; //运输方式
    private String khzw;//职务
    private String khlx;//客户性质 khxz
    private int ywjdId;//业务接单号
    

	/********新增客户基本信息*************/
	//客户区域
	private String khchsh;
	//(录入客户价格)职务
	private String lxrzw;
	//货物名称
	private String pinming;
	//税号
	private String swzh;
	//客户类型
	private String khlxbh;
	
	//客户公司
	private String company;
	
	
	
	   /***操作要求参数***/
    //结款方式
    private String clearingcode;
   /**2018年05月24号新增start**/
    private String fkxf;//现付(结款方式)
     //到付(结款方式)  
    private String fkdf;
    //返单(结款方式)
    private String fkfd;
    //周期(结款方式)
    private String fkzq;
    //其他(结款方式) 
    private String fkqt;
   /**2018年05月24号新增end**/
    
    //有无合同
    private Integer ywht;
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
    private Integer khxz;//1.新客户 2.老客户发新线 3.老客户调价 4.流失客户
    //有效期
    private String yxq;
    //操作标准
    private String czbb;
    //其他特殊操作说明
    private String czsm;
    //20180607
    private String fdnr; //返单名称
    private String fdlc; //返单联次
    private int lskh; //流失客户个数
    
    
    /**2018年05月24号新增客户价格-客户基本信息***/
    private String  kuaijian; //快件(业务类型) yw_ky
    private String  qiyun; //汽运(业务类型)  yw_qy
    private String  xinbao;// 行包(业务类型)  yw_xb
    private String  wuding; //五定(业务类型)  yw_wd
    private String  xinyou;//  行邮(业务类型)  yw_xy
    private String  hangkong; //航空(业务类型) yw_hk
    private String  jizhuangxian; //集装箱(业务类型) yw_jzx
    private String  citysend; //城际配送(业务类型)  yw_cj
    private String  other;    //其他 (业务类型)   yw_qt
    
    
    
	public String getFkxf() {
		return fkxf;
	}
	public void setFkxf(String fkxf) {
		this.fkxf = fkxf;
	}
	public String getFkdf() {
		return fkdf;
	}
	public void setFkdf(String fkdf) {
		this.fkdf = fkdf;
	}
	public String getFkfd() {
		return fkfd;
	}
	public void setFkfd(String fkfd) {
		this.fkfd = fkfd;
	}
	public String getFkzq() {
		return fkzq;
	}
	public void setFkzq(String fkzq) {
		this.fkzq = fkzq;
	}
	public String getFkqt() {
		return fkqt;
	}
	public void setFkqt(String fkqt) {
		this.fkqt = fkqt;
	}
	public String getCzbb() {
		return czbb;
	}
	public void setCzbb(String czbb) {
		this.czbb = czbb;
	}
	public String getKuaijian() {
		return kuaijian;
	}
	public void setKuaijian(String kuaijian) {
		this.kuaijian = kuaijian;
	}
	public String getQiyun() {
		return qiyun;
	}
	public void setQiyun(String qiyun) {
		this.qiyun = qiyun;
	}
	public String getXinbao() {
		return xinbao;
	}
	public void setXinbao(String xinbao) {
		this.xinbao = xinbao;
	}
	public String getWuding() {
		return wuding;
	}
	public void setWuding(String wuding) {
		this.wuding = wuding;
	}
	public String getXinyou() {
		return xinyou;
	}
	public void setXinyou(String xinyou) {
		this.xinyou = xinyou;
	}
	public String getHangkong() {
		return hangkong;
	}
	public void setHangkong(String hangkong) {
		this.hangkong = hangkong;
	}
	public String getJizhuangxian() {
		return jizhuangxian;
	}
	public void setJizhuangxian(String jizhuangxian) {
		this.jizhuangxian = jizhuangxian;
	}
	public String getCitysend() {
		return citysend;
	}
	public void setCitysend(String citysend) {
		this.citysend = citysend;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public String getKhlxr() {
		return khlxr;
	}
	public void setKhlxr(String khlxr) {
		this.khlxr = khlxr;
	}
	public String getHwmch() {
		return hwmch;
	}
	public void setHwmch(String hwmch) {
		this.hwmch = hwmch;
	}
	public String getKhbm() {
		return khbm;
	}
	public void setKhbm(String khbm) {
		this.khbm = khbm;
	}
	public String getKhtxdz() {
		return khtxdz;
	}
	public void setKhtxdz(String khtxdz) {
		this.khtxdz = khtxdz;
	}
	public String getIsfd() {
		return isfd;
	}
	public void setIsfd(String isfd) {
		this.isfd = isfd;
	}
	public String getKhdh() {
		return khdh;
	}
	public void setKhdh(String khdh) {
		this.khdh = khdh;
	}
	public String getZxshj() {
		return zxshj;
	}
	public void setZxshj(String zxshj) {
		this.zxshj = zxshj;
	}
	public String getFkfs() {
		return fkfs;
	}
	public void setFkfs(String fkfs) {
		this.fkfs = fkfs;
	}
	public String getHylb() {
		return hylb;
	}
	public void setHylb(String hylb) {
		this.hylb = hylb;
	}
	public String getFdyq() {
		return fdyq;
	}
	public void setFdyq(String fdyq) {
		this.fdyq = fdyq;
	}
	public String getHwysfs() {
		return hwysfs;
	}
	public void setHwysfs(String hwysfs) {
		this.hwysfs = hwysfs;
	}
	public String getKhzw() {
		return khzw;
	}
	public void setKhzw(String khzw) {
		this.khzw = khzw;
	}
	public String getKhlx() {
		return khlx;
	}
	public void setKhlx(String khlx) {
		this.khlx = khlx;
	}
	public int getYwjdId() {
		return ywjdId;
	}
	public void setYwjdId(int ywjdId) {
		this.ywjdId = ywjdId;
	}
	public String getKhchsh() {
		return khchsh;
	}
	public void setKhchsh(String khchsh) {
		this.khchsh = khchsh;
	}
	public String getLxrzw() {
		return lxrzw;
	}
	public void setLxrzw(String lxrzw) {
		this.lxrzw = lxrzw;
	}
	public String getPinming() {
		return pinming;
	}
	public void setPinming(String pinming) {
		this.pinming = pinming;
	}
	public String getSwzh() {
		return swzh;
	}
	public void setSwzh(String swzh) {
		this.swzh = swzh;
	}
	public String getKhlxbh() {
		return khlxbh;
	}
	public void setKhlxbh(String khlxbh) {
		this.khlxbh = khlxbh;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
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
	public String getYxq() {
		return yxq;
	}
	public void setYxq(String yxq) {
		this.yxq = yxq;
	}
	public String getCzsm() {
		return czsm;
	}
	public void setCzsm(String czsm) {
		this.czsm = czsm;
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
	public int getLskh() {
		return lskh;
	}
	public void setLskh(int lskh) {
		this.lskh = lskh;
	}
	
	
    
	
    
    

}
