package com.ycgwl.kylin.entity;

public class RequestEntity extends BaseEntity {
	
	private static final long serialVersionUID = -1033910905210340704L;
	
	private Integer num;
	private Integer size;
	
	public Integer getNum() {
		if(num == null || num <= 0)
			num = 1;
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getSize() {
		if(size == null || size <= 0)
			size = 10;
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
}
