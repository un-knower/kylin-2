package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 派车提成趟次
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
 */
public class CarOutBaseTc extends BaseEntity{

	private static final long serialVersionUID = -2425611159932883151L;

	private Integer id;
	
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
