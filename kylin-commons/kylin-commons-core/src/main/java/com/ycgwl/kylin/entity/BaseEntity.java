package com.ycgwl.kylin.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
  * BaseEntity
  * @Description: 基础实体类
  * @author <a href="mailto:110686@ycgwl.com">dingXuefeng</a>
  * @date 2017年5月16日 上午8:58:02
  */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -6510350532201986247L;

	@Override
	public String toString() {
		try {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		} catch (Exception e) {
			return super.toString();
		}
	}
}
