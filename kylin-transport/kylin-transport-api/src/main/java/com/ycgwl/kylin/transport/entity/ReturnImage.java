package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;

/**
 * 回单实体类
 */
public class ReturnImage extends BaseEntity {

	private static final long serialVersionUID = -6836895286236644859L;
	
	public int id;
	public String img_address;
	public String img_name;
	public String ydbhid;
	public String description;
	public String create_by;
	public Date create_time;
	public String last_update_by;
	public Date last_update_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImg_address() {
		return img_address;
	}
	public void setImg_address(String img_address) {
		this.img_address = img_address;
	}
	public String getImg_name() {
		return img_name;
	}
	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getLast_update_by() {
		return last_update_by;
	}
	public void setLast_update_by(String last_update_by) {
		this.last_update_by = last_update_by;
	}
	public Date getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}
	
	

}
