package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Collection;

public class TransportOrderEntry extends BaseEntity{

	private static final long serialVersionUID = 6428992255829015180L;
	
	/** 运单信息 */
	private TransportOrder order;
	
	/**
	 * 标准单价（叉车单价、重货、轻货单价、保率来源）
	 */
	FinanceStandardPrice price;
	
	/** 运单明细信息 */
	private Collection<TransportOrderDetail> details;
	
	/** 财凭信息 */
	private FinanceCertify certify;
	
	/** 省市区 */
	private String shrProvinces;
	
	/** 等托运人指令发货 */
	private Integer releaseWaiting;
	
	private Integer saveLogo;
	
	/** 修改运单标识（0.未修改运单信息  1.已修改运单信息） */
	private Integer modifyOrderIdentification;
	
	/** 补录财凭标识（0.未补录财凭信息  1.已补录财凭信息） */
	private Integer modifyCertifyIdentification;
	
	public TransportOrderEntry() {
		super();
		details = new ArrayList<TransportOrderDetail>();
	}
	
	public FinanceStandardPrice getPrice() {
		return price;
	}
	
	public void setPrice(FinanceStandardPrice price) {
		this.price = price;
	}

	/**
	 * getter method
	 * @return the order
	 */
	public TransportOrder getOrder() {
		return order;
	}

	/**
	 * setter method
	 * @param order the order to set
	 */
	public void setOrder(TransportOrder order) {
		this.order = order;
	}

	/**
	 * getter method
	 * @return the details
	 */
	public Collection<TransportOrderDetail> getDetails() {
		return details;
	}

	/**
	 * setter method
	 * @param details the details to set
	 */
	public void setDetails(Collection<TransportOrderDetail> details) {
		this.details = details;
	}

	/**
	 * getter method
	 * @return the certify
	 */
	public FinanceCertify getCertify() {
		return certify;
	}

	/**
	 * setter method
	 * @param certify the certify to set
	 */
	public void setCertify(FinanceCertify certify) {
		this.certify = certify;
	}

	public String getShrProvinces() {
		shrProvinces = "";
		if(StringUtils.isNotBlank(order.getDhShengfen())){
			shrProvinces += order.getDhShengfen();
		}
		if(StringUtils.isNotBlank(order.getDhChengsi())){
			if(shrProvinces.length() > 0){
				shrProvinces += "-";
			}
			shrProvinces += order.getDhChengsi();
		}
		if(StringUtils.isNotBlank(order.getDhAddr())){
			if(shrProvinces.length() > 0){
				shrProvinces += "-";
			}
			shrProvinces += order.getDhAddr();
		}
		return shrProvinces;
	}

	public void setShrProvinces(String shrProvinces) {
		if(StringUtils.isNotBlank(shrProvinces)){
			String[] addr = shrProvinces.split("-");
			if(addr.length == 1){
				order.setDhAddr(addr[0].trim());
			}else if(addr.length == 2){
				order.setDhChengsi(addr[0].trim());
				order.setDhAddr(addr[1].trim());
			}else{
				order.setDhShengfen(addr[0].trim());
				order.setDhChengsi(addr[1].trim());
				order.setDhAddr(addr[2].trim());
			}
		}
	}

	public String getFhshj() {
		if(order.getFhshj() != null){
			return DateFormatUtils.format(order.getFhshj(), "yyyy-MM-dd HH:mm");
		}
		return "";
	}

	public String getFzhchyrq() {
		if(order.getFzhchyrq() != null){
			return DateFormatUtils.format(order.getFzhchyrq(), "yyyy-MM-dd");
		}
		return "";
	}

	public Integer getReleaseWaiting() {
		return releaseWaiting;
	}

	public void setReleaseWaiting(Integer releaseWaiting) {
		this.releaseWaiting = releaseWaiting;
	}

	public Integer getSaveLogo() {
		return saveLogo;
	}

	public void setSaveLogo(Integer saveLogo) {
		this.saveLogo = saveLogo;
	}

	public String getConveyKey() {
		return certify.getConveyKey();
	}

	public Integer getModifyOrderIdentification() {
		return modifyOrderIdentification;
	}

	public void setModifyOrderIdentification(Integer modifyOrderIdentification) {
		this.modifyOrderIdentification = modifyOrderIdentification;
	}

	public Integer getModifyCertifyIdentification() {
		return modifyCertifyIdentification;
	}

	public void setModifyCertifyIdentification(Integer modifyCertifyIdentification) {
		this.modifyCertifyIdentification = modifyCertifyIdentification;
	}
	
	
	
}
