package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;
import java.util.List;

public class BundlePickHome implements Serializable{
	private DispatchCarPickGoods orderEntity;
	private List<DispatchCarPickGoodsDetail> goodsList;
	private List<DispatchCarPickGoodsDetailSecond> dispatchList;
	private Boolean canModifyGoodsDetail;
	private Boolean canModifyDispatchDetail;
	private Integer idnew;
	private String ver;
	private Integer openType;
	
	public Integer getOpenType() {
		return openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}

	public Integer getIdnew() {
		return idnew;
	}

	public void setIdnew(Integer idnew) {
		this.idnew = idnew;
	}
	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public Boolean getCanModifyGoodsDetail() {
		return canModifyGoodsDetail;
	}

	public void setCanModifyGoodsDetail(Boolean canModifyGoodsDetail) {
		this.canModifyGoodsDetail = canModifyGoodsDetail;
	}

	public Boolean getCanModifyDispatchDetail() {
		return canModifyDispatchDetail;
	}

	public void setCanModifyDispatchDetail(Boolean canModifyDispatchDetail) {
		this.canModifyDispatchDetail = canModifyDispatchDetail;
	}

	public List<DispatchCarPickGoodsDetailSecond> getDispatchList() {
		return dispatchList;
	}

	public void setDispatchList(List<DispatchCarPickGoodsDetailSecond> dispatchList) {
		this.dispatchList = dispatchList;
	}

	public List<DispatchCarPickGoodsDetail> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<DispatchCarPickGoodsDetail> goodsList) {
		this.goodsList = goodsList;
	}

	public DispatchCarPickGoods getOrderEntity() {
		return orderEntity;
	}

	public void setOrderEntity(DispatchCarPickGoods orderEntity) {
		this.orderEntity = orderEntity;
	}
}
