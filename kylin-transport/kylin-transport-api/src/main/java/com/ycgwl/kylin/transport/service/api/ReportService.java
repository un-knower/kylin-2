package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表服务
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2018/3/28
 */
public interface ReportService {

    Collection<ReportQuerySomething> listByReportKey(String account, Integer reportKey) throws ParameterException, BusinessException;

    Report loadReportByKey(String account, Integer reportKey, Map<String, String> parmars) throws ParameterException, BusinessException;

    /** 根据报表id查询配置*/
 	public ReportConfig findReportbyKey(Integer reportKey);

 	/** 添加报表配置*/
 	public void insertReportConfig(ReportConfig reportConfig);

 	/** 批量添加报表查询条件*/
 	public void insertReportQueryList(List<ReportQuery> list);

 	/** 添加报表*/
 	public JsonResult saveOrUptReportNew(ReportConfig rConfig, List<ReportQuery> rQueryList) throws ParameterException;
 	
 	/** 删除报表*/
 	public void deleteReport(ReportConfig rConfig);
 	
 	/** 合计报表金额*/
 	public List<Map<String,Object>> sumReportByKey(String company, Map<String, String> parmars);
 	
 	/** 删除报表查询条件*/
 	public JsonResult deleteReportQuery(ReportQuery Query);
 	
 	/** 添加报表*/
 	public void saveOrUptReport(ReportConfig rConfig);
 	
 	/** 查询所有报表*/
 	public Collection<ReportConfig> listConfig();

 	/** 动态获取存储过程的结果*/
	Collection<HashMap<String, Object>> getDynamicProcedure(HashMap<String, Object> params);
	
	/**设置报表锁定状态**/
 	public JsonResult lockReport(ReportConfig rConfig);

 	/**根据父报表和子报表id查询子报表 **/
	ReportSubReport getSubReportByParentIdSubId(Integer parentKey, Integer subReportKey);
	
	/** 删除子报表*/
	void deleteSubReport(ReportSubReport sub);
 		
}
