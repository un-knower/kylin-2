package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 在途情况实体
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月28日
 */
public class HwydRoute extends BaseEntity{
	
	private static final long serialVersionUID = -8986588279602488002L;
	
	private Integer id;
	
	private String ydbhid;
	
	private Date shiJian;
	
	private String cztype;
	
	private String gs;
	
	private String grid;
	
	private String beiZhu;
	
	private String rowGuid;
	
	private Integer xuHao;
	
	private Integer pushFlag;

	public Integer getId() {
		return id;
	}

	public HwydRoute setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public HwydRoute setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
		return this;
	}

	public Date getShiJian() {
		return shiJian;
	}

	public HwydRoute setShiJian(Date shiJian) {
		this.shiJian = shiJian;
		return this;
	}

	public String getCztype() {
		return cztype;
	}

	public HwydRoute setCztype(String cztype) {
		this.cztype = cztype;
		return this;
	}

	public String getGs() {
		return gs;
	}

	public HwydRoute setGs(String gs) {
		this.gs = gs;
		return this;
	}

	public String getGrid() {
		return grid;
	}

	public HwydRoute setGrid(String grid) {
		this.grid = grid;
		return this;
	}

	public String getBeiZhu() {
		return beiZhu;
	}

	public HwydRoute setBeiZhu(String beiZhu) {
		this.beiZhu = beiZhu;
		return this;
	}

	public String getRowGuid() {
		return rowGuid;
	}

	public HwydRoute setRowGuid(String rowGuid) {
		this.rowGuid = rowGuid;
		return this;
	}

	public Integer getXuHao() {
		return xuHao;
	}

	public HwydRoute setXuHao(Integer xuHao) {
		this.xuHao = xuHao;
		return this;
	}

	public Integer getPushFlag() {
		return pushFlag;
	}

	public HwydRoute setPushFlag(Integer pushFlag) {
		this.pushFlag = pushFlag;
		return this;
	}
	
}
