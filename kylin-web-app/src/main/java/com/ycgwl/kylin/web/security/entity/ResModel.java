package com.ycgwl.kylin.web.security.entity;

import com.ycgwl.kylin.entity.RequestEntity;
import com.ycgwl.kylin.security.entity.Resource;

import java.util.Collection;

/**
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-04-28 14:49:12
 */
public class ResModel extends RequestEntity {
	
	private static final long serialVersionUID = 8029189740492653629L;
	private Resource res;

	public ResModel() {
		super();
		res = new Resource();
	}
	
	public int getEnable() {
		if(res.getEnable() == null){
			return 0;
		}
		return res.getEnable() ? 1 : 2;
	}
	public void setEnable(int enable) {
		if(enable == 0){
			res.setEnable(null);
		} else if(enable == 1){
			res.setEnable(true);
		}else {
			res.setEnable(false);
		}
	}
	
	public Resource getRes() {
		return res;
	}
	public void setRes(Resource res) {
		this.res = res;
	}
	public Integer getResourceId() {
		return res.getResourceId();
	}
	public String getText() {
		return res.getText();
	}
	public Integer getType() {
		return res.getType();
	}
	public String getUrl() {
		return res.getUrl();
	}
	public Integer getParentId() {
		return res.getParentId();
	}
	public Collection<Resource> getChildren() {
		return res.getChildren();
	}
	public String getResurl() {
		return res.getResurl();
	}
}
