package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * TransportOrderGoodException
 * 
 * @Description: 货物运单异常录入（货物异常录入表 t_hshc 的实体类）
 * @author <a href="mailto:62337@ycgwl.com">chenxinjiang</a>
 * @date 2017年12月11日
 */
public class TransportOrderGoodException extends BaseEntity{

	private static final long serialVersionUID = -8674574844455993668L;
	
	private String company;
	private String ydbhid;
	private String faZhan;//发站
	private String daoZhan;
	private String driveName;//司机姓名
	private String driveTele;//司机电话号码
	private String carNo;//车牌号
	private String scene;//发生地点
	private Date happenDate;//发生日期
	private String customerName;//客户名称
	private String customerNo;//客户单号
	private String packing;//包装
	private String grid;//录入人
	private String jiaoJie;//交接人（工号）
	private String jieShou;//接收人（工号）
	private String hjbh;//环节编号
	private String chxh;//车厢号
	private String pinMing;//品名
	private int jianShu;//件数
	private Date shj;//录入时间(保存时间，不用填）
	private int sfqk;
	private int fdwd;
	private int bd;//被盗
	/*
	 * 运输执行将来查询时 短少=灭失+短少+内物短少
	 */
	private int ms;//灭失//
	private int ds;//短少//与当前需求一致
	private int nwds;//内物短少
	/*
	 * 运输执行将来查询时 破损=破裂+摔损+变形+磨伤+部件破损
	 */
	private int pl;//破裂//对应当前的破损
	private int bx;//变形
	private int msh;//磨伤
	private int shsh;//摔损
	private int bjps;//部件破损
	
	private int ssh;//湿损//与当前需求一致
	/*
	 * 运输执行将来查询时 变质=腐烂霉变+植物枯死
	 */
	private int flmb;//腐烂霉变//对应当前的变质
	private int zwks;//植物枯死
		
	private int delayed;//延时 运输执行新增字段
	private int chuanhuo;//串货
	private int excepquantity;//异常数量//运输执行新增
	private int salvage;//残值数量//运输执行新增
	private int frozenhurt;//冻损//运输执行新增
	/*
	 * 污损和染毒 运输执行没有
	 */
	private int wr;//污损
	private int rd;//染毒
	
	private int other;//其他
	
	private int bs;
	private String bz;//备注
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public String getDaoZhan() {
		return daoZhan;
	}
	public void setDaoZhan(String daoZhan) {
		this.daoZhan = daoZhan;
	}
	public String getGrid() {
		return grid;
	}
	public void setGrid(String grid) {
		this.grid = grid;
	}
	public String getJiaoJie() {
		return jiaoJie;
	}
	public void setJiaoJie(String jiaoJie) {
		this.jiaoJie = jiaoJie;
	}
	public String getJieShou() {
		return jieShou;
	}
	public void setJieShou(String jieShou) {
		this.jieShou = jieShou;
	}
	public String getHjbh() {
		return hjbh;
	}
	public void setHjbh(String hjbh) {
		this.hjbh = hjbh;
	}
	public String getChxh() {
		return chxh;
	}
	public void setChxh(String chxh) {
		this.chxh = chxh;
	}
	public String getPinMing() {
		return pinMing;
	}
	public void setPinMing(String pinMing) {
		this.pinMing = pinMing;
	}
	public int getJianShu() {
		return jianShu;
	}
	public void setJianShu(int jianShu) {
		this.jianShu = jianShu;
	}
	public Date getShj() {
		return shj;
	}
	public void setShj(Date shj) {
		this.shj = shj;
	}
	public int getSfqk() {
		return sfqk;
	}
	public void setSfqk(int sfqk) {
		this.sfqk = sfqk;
	}
	public int getFdwd() {
		return fdwd;
	}
	public void setFdwd(int fdwd) {
		this.fdwd = fdwd;
	}
	public int getBd() {
		return bd;
	}
	public void setBd(int bd) {
		this.bd = bd;
	}
	public int getMs() {
		return ms;
	}
	public void setMs(int ms) {
		this.ms = ms;
	}
	public int getDs() {
		return ds;
	}
	public void setDs(int ds) {
		this.ds = ds;
	}
	public int getNwds() {
		return nwds;
	}
	public void setNwds(int nwds) {
		this.nwds = nwds;
	}
	public int getPl() {
		return pl;
	}
	public void setPl(int pl) {
		this.pl = pl;
	}
	public int getBx() {
		return bx;
	}
	public void setBx(int bx) {
		this.bx = bx;
	}
	public int getMsh() {
		return msh;
	}
	public void setMsh(int msh) {
		this.msh = msh;
	}
	public int getShsh() {
		return shsh;
	}
	public void setShsh(int shsh) {
		this.shsh = shsh;
	}
	public int getBjps() {
		return bjps;
	}
	public void setBjps(int bjps) {
		this.bjps = bjps;
	}
	public int getSsh() {
		return ssh;
	}
	public void setSsh(int ssh) {
		this.ssh = ssh;
	}
	public int getFlmb() {
		return flmb;
	}
	public void setFlmb(int flmb) {
		this.flmb = flmb;
	}
	public int getZwks() {
		return zwks;
	}
	public void setZwks(int zwks) {
		this.zwks = zwks;
	}
	public int getWr() {
		return wr;
	}
	public void setWr(int wr) {
		this.wr = wr;
	}
	public int getRd() {
		return rd;
	}
	public void setRd(int rd) {
		this.rd = rd;
	}
	public int getBs() {
		return bs;
	}
	public void setBs(int bs) {
		this.bs = bs;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getDelayed() {
		return delayed;
	}
	public void setDelayed(int delayed) {
		this.delayed = delayed;
	}
	public int getChuanhuo() {
		return chuanhuo;
	}
	public void setChuanhuo(int chuanhuo) {
		this.chuanhuo = chuanhuo;
	}
	public int getExcepquantity() {
		return excepquantity;
	}
	public void setExcepquantity(int excepquantity) {
		this.excepquantity = excepquantity;
	}
	public int getSalvage() {
		return salvage;
	}
	public void setSalvage(int salvage) {
		this.salvage = salvage;
	}
	public int getFrozenhurt() {
		return frozenhurt;
	}
	public void setFrozenhurt(int frozenhurt) {
		this.frozenhurt = frozenhurt;
	}
	public String getFaZhan() {
		return faZhan;
	}
	public void setFaZhan(String faZhan) {
		this.faZhan = faZhan;
	}
	public String getDriveName() {
		return driveName;
	}
	public void setDriveName(String driveName) {
		this.driveName = driveName;
	}
	public String getDriveTele() {
		return driveTele;
	}
	public void setDriveTele(String driveTele) {
		this.driveTele = driveTele;
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
	}
	public Date getHappenDate() {
		return happenDate;
	}
	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	public int getOther() {
		return other;
	}
	public void setOther(int other) {
		this.other = other;
	}
	
	
}
