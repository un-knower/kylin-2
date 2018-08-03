package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;

/**
 * 运输执行基础数据
 */
public class TransportBasicData implements Serializable{
	private String keyName;
	private String keyValue;
	private String keyValueStr;
	private String name;
	private String code;
	private String parentCode;
	private String parentName;
	private String desc;
	public String getKeyValueStr() {
		return keyValueStr;
	}
	public void setKeyValueStr(String keyValueStr) {
		this.keyValueStr = keyValueStr;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
