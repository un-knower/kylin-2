package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 收费项目实体
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月20日
 */
public class ChargingProjects extends BaseEntity{

	private static final long serialVersionUID = -9087806114101574374L;

	private Integer id;
	
	private String shfxm;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShfxm() {
		return shfxm;
	}

	public void setShfxm(String shfxm) {
		this.shfxm = shfxm;
	}
	
}
