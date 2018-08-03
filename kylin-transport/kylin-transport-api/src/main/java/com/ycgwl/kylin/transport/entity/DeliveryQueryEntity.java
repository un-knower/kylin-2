package com.ycgwl.kylin.transport.entity;

/**
 * 送货派车查询实体
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
 */
public class DeliveryQueryEntity extends UserModel{
	
	private static final long serialVersionUID = 8550225091029028258L;
	
	/** 派车单id */
	private Integer pcdid;

	public Integer getPcdid() {
		return pcdid;
	}

	public void setPcdid(Integer pcdid) {
		this.pcdid = pcdid;
	}
	
}
