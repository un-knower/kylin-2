package com.ycgwl.kylin.transport.entity;/**
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2018/3/28
 */

import com.ycgwl.kylin.entity.BaseEntity;


/**
 * 报表查询条件
 * <p>
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2018/3/28
 */
public class ReportQuerySomething extends BaseEntity {

	private static final long serialVersionUID = -4354351L;
	private String queryName;//查询条件名称,回显示在页面上
    private String queryKey;//提交时参数名
    private String queryDefaultValue;//查询条件默认值
    private String queryType;//查询条件类型(select, input, radio, checkbox, date)
    
    /**20180625新增**/
	private Integer queryId;//查询条件主键
    private String querySrcType;//查询条件数值来源(空值表示固定数据, company:分公司,从数据库查)
    private String querySrc;//查询条件数值
  
    public Integer getQueryId() {
		return queryId;
	}

	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}

	public String getQuerySrcType() {
		return querySrcType;
	}

	public void setQuerySrcType(String querySrcType) {
		this.querySrcType = querySrcType;
	}

	public String getQuerySrc() {
		return querySrc;
	}

	public void setQuerySrc(String querySrc) {
		this.querySrc = querySrc;
	}

	private String [] listData;

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getQueryKey() {
        return queryKey;
    }

    public void setQueryKey(String queryKey) {
        this.queryKey = queryKey;
    }

    public String getQueryDefaultValue() {
        return queryDefaultValue;
    }

    public void setQueryDefaultValue(String queryDefaultValue) {
        this.queryDefaultValue = queryDefaultValue;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String [] getListData() {
        return listData;
    }

    public void setListData(String [] listData) {
        this.listData = listData;
    }
}
