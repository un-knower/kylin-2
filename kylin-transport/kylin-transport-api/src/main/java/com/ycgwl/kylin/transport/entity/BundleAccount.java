package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 取货派车签收核算
 * @author ceshi
 *
 */
public class BundleAccount implements Serializable{
	private List<DispatchCarPickGoodsDetailThree> queryTCarDetailThreeList;
	private List<DispatchCarPickGoodsDetailFive> queryTCarDetailFiveList;
	private DispatchCarPickGoods tcarPick;
	private String pcid;
	private String gsid;
	private String userName;
	
	public String getPcid() {
		return pcid;
	}
	public void setPcid(String pcid) {
		this.pcid = pcid;
	}
	public String getGsid() {
		return gsid;
	}
	public void setGsid(String gsid) {
		this.gsid = gsid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<DispatchCarPickGoodsDetailThree> getQueryTCarDetailThreeList() {
		return queryTCarDetailThreeList;
	}
	public void setQueryTCarDetailThreeList(List<DispatchCarPickGoodsDetailThree> queryTCarDetailThreeList) {
		this.queryTCarDetailThreeList = queryTCarDetailThreeList;
	}
	public List<DispatchCarPickGoodsDetailFive> getQueryTCarDetailFiveList() {
		return queryTCarDetailFiveList;
	}
	public void setQueryTCarDetailFiveList(List<DispatchCarPickGoodsDetailFive> queryTCarDetailFiveList) {
		this.queryTCarDetailFiveList = queryTCarDetailFiveList;
	}
	public DispatchCarPickGoods getTcarPick() {
		return tcarPick;
	}
	public void setTcarPick(DispatchCarPickGoods tcarPick) {
		this.tcarPick = tcarPick;
	}
}
