package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 货运记录详情表   对应数据库 T_HYJL_MASTER  T_HYJL_DETAIL
 * @author Acer
 *
 */
public class FreightRecordDetail extends BaseEntity{

	private static final long serialVersionUID = 6246836155506668426L;
	
	public int masterid;
	
	public int xzh;
	
	/** 运单号码 */
	public String ydbhid;
	
	/** 车号 */
	public String chxh;
	
	/** 货物名称 */
	public String pinming;
	
	/** 起票件数 */
	public int jianshu;
	
	/** 是否破封 */
	public int sfqk;
	
	/** 附单未到 */
	public int fdwd;
	
	/** 被盗 */
	public int bd;
	
	/** 灭失 */
	public int ms;
	
	/** 短少 */
	public int ds;
	
	/** 破裂 */
	public int pl;
	
	/** 变形 */
	public int bx;
	
	/** 磨伤 */
	public int msh;
	
	/** 摔损 */
	public int shsh;
	
	/** 部件破损 */
	public int bjps;
	
	/** 湿损 */
	public int ssh;
	
	/** 腐烂霉变 */
	public int flmb;
	
	/** 植物枯死 */
	public int zwks;
	
	/** 污损 */
	public int wr;  
	
	/** 染毒 */
	public int rd;
	
	/** 异常货物状态/配载描述 */
	public String ychzht;
	
	/** 异常总数量 */
	public int exceptionCount;
	
	/** 预估损失*/
	public int estimatedLoss;

	public int getMasterid() {
		return masterid;
	}

	public void setMasterid(int masterid) {
		this.masterid = masterid;
	}

	public int getXzh() {
		return xzh;
	}

	public void setXzh(int xzh) {
		this.xzh = xzh;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public String getChxh() {
		return chxh;
	}

	public void setChxh(String chxh) {
		this.chxh = chxh;
	}

	public String getPinming() {
		return pinming;
	}

	public void setPinming(String pinming) {
		this.pinming = pinming;
	}

	public int getJianshu() {
		return jianshu;
	}

	public void setJianshu(int jianshu) {
		this.jianshu = jianshu;
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

	public String getYchzht() {
		return ychzht;
	}

	public void setYchzht(String ychzht) {
		this.ychzht = ychzht;
	}

	public int getEstimatedLoss() {
		return estimatedLoss;
	}

	public void setEstimatedLoss(int estimatedLoss) {
		this.estimatedLoss = estimatedLoss;
	}

	public int getExceptionCount() {
		return  wr+ssh+bjps+ds;
	}

	public void setExceptionCount(int exceptionCount) {
		this.exceptionCount = exceptionCount;
	}
	
	
	

}
