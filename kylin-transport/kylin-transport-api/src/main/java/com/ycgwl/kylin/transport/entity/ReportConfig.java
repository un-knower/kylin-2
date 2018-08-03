package com.ycgwl.kylin.transport.entity;/**
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2018/3/28
 */

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.List;

/**
 * 报表配置
 * <p>
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2018/3/28
 */
public class ReportConfig extends BaseEntity {

	private static final long serialVersionUID = -145654L;
	private Integer reportId;//报表主键
    private String reportName;//报表名称
    private String reportHeaders;//报表表头
    private String reportHeaderNames;//报表头名称
    private String orderByKey;//排序字段
	private String reportSelectSql;//报表查询语句，查询字段和表头一一对应
    private String reportQueryKeys;//报表查询条件(多条件用非数字隔开)
    private String reportSumSql;//报表合计sql
    private String reportSumKey;//报表合计key
    private String dataBaseKey;
    private String storedProcedureName;
    private String groupByKey; 
    private Integer isListGroup;
    private String replaceSql;
    
    private Integer lockFlag;//报表锁定状态(1.是 0.否)
    
    private List<ReportSubReport> subList;
    
    public List<ReportSubReport> getSubList() {
		return subList;
	}

	public void setSubList(List<ReportSubReport> subList) {
		this.subList = subList;
	}

    public String getReplaceSql() {
		return replaceSql;
	}

	public void setReplaceSql(String replaceSql) {
		this.replaceSql = replaceSql;
	}

	public Integer getIsListGroup() {
		return isListGroup;
	}

	public void setIsListGroup(Integer isListGroup) {
		this.isListGroup = isListGroup;
	}

	public String getGroupByKey() {
		return groupByKey;
	}

	public void setGroupByKey(String groupByKey) {
		this.groupByKey = groupByKey;
	}

	public String getDataBaseKey() {
		return dataBaseKey;
	}

	public void setDataBaseKey(String dataBaseKey) {
		this.dataBaseKey = dataBaseKey;
	}

	public String getOrderByKey() {
		return orderByKey;
	}

	public String getReportSumSql() {
		return reportSumSql;
	}

	public void setReportSumSql(String reportSumSql) {
		this.reportSumSql = reportSumSql;
	}

	public String getReportSumKey() {
		return reportSumKey;
	}

	public void setReportSumKey(String reportSumKey) {
		this.reportSumKey = reportSumKey;
	}

	public void setOrderByKey(String orderByKey) {
		this.orderByKey = orderByKey;
	}

	public String getReportHeaderNames() {
		return reportHeaderNames;
	}

	public void setReportHeaderNames(String reportHeaderNames) {
		this.reportHeaderNames = reportHeaderNames;
	}

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportHeaders() {
        return reportHeaders;
    }

    public void setReportHeaders(String reportHeaders) {
        this.reportHeaders = reportHeaders;
    }

    public String getReportSelectSql() {
        return reportSelectSql;
    }

    public void setReportSelectSql(String reportSelectSql) {
        this.reportSelectSql = reportSelectSql;
    }

    public String getReportQueryKeys() {
        return reportQueryKeys;
    }

    public void setReportQueryKeys(String reportQueryKeys) {
        this.reportQueryKeys = reportQueryKeys;
    }

	public String getStoredProcedureName() {
		return storedProcedureName;
	}

	public void setStoredProcedureName(String storedProcedureName) {
		this.storedProcedureName = storedProcedureName;
	}
	
	public Integer getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}
}
