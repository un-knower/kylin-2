
package com.ycgwl.kylin.security.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Collection;

/**
  * Resource   测试
  * @Description: 资源实体
  * @author <a href="mailto:110686@ycgwl.com">dingXuefeng</a>
  * @date 2017年5月15日 下午5:00:22
  * litao
  */
public class Resource extends BaseEntity {

	private static final long serialVersionUID = 2785776387580898335L;
	
	/**
	  * resourceId : 资源编号，具有唯一性
	  */
	private Integer resourceId;
	/**
	  * text : 资源名称
	  */
	private String text;
	/**
	  * type : 资源类型，1:菜单 2:页面元素
	  */
	private Integer type;
	/**
	  * resurl : 资源标示url
	  */
	private String resurl;
	/**
	  * url : 资源url
	  */
	private String url;
	/**
	  * parentId : 上一级资源编号
	  */
	private Integer parentId;
	/**
	  * enable : 资源是否可用,true:可用,false:不可用
	  */
	private Boolean enable;
	
	/*业务字段，不持久化*/
	/**
	  * children : 所有下一级资源
	  */
	private Collection<Resource> children;
	private Integer roleId;
	
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getParentId() {
		if(parentId == null){
			parentId = 0;
		}
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Collection<Resource> getChildren() {
		return children;
	}
	public void setChildren(Collection<Resource> children) {
		this.children = children;
	}
	public String getResurl() {
		return resurl;
	}
	public void setResurl(String resurl) {
		this.resurl = resurl;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
