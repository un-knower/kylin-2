package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 运单跟踪表    对应数据库  HWYD_ROUTE 表
 * @author chenxm
 *
 */
public class OnTheWay extends BaseEntity{

	private static final long serialVersionUID = -5298649990357087816L;
	
	public int id;
	
	/** 运单编号 */
	public String ydbhid;
	
	/** 时间  */
	public Date shijian;
	
	/** 操作类型  */
	public String cztype;
	
	/** 公司 */
	public String gs;
	
	/** 操作人姓名 */
	public String grid;
	
	/** 备注 */
	public String beizhu;
	
	/** 序号 */
	public int xuhao;
	
	/** */
	public int pushflag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public Date getShijian() {
		return shijian;
	}

	public void setShijian(Date shijian) {
		this.shijian = shijian;
	}

	public String getCztype() {
		return cztype;
	}

	public void setCztype(String cztype) {
		this.cztype = cztype;
	}

	public String getGs() {
		return gs;
	}

	public void setGs(String gs) {
		this.gs = gs;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public int getXuhao() {
		return xuhao;
	}

	public void setXuhao(int xuhao) {
		this.xuhao = xuhao;
	}

	public int getPushflag() {
		return pushflag;
	}

	public void setPushflag(int pushflag) {
		this.pushflag = pushflag;
	}
	

}
