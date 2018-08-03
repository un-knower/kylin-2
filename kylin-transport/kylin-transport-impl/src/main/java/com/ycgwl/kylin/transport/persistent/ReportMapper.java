package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.ReportConfig;
import com.ycgwl.kylin.transport.entity.ReportQuery;
import com.ycgwl.kylin.transport.entity.ReportSubReport;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2018/3/28
 */
public interface ReportMapper {

    Collection<ReportConfig> listConfig();

    /** 查询报表所需配置  */
    ReportConfig getConfigByKey(Integer reportId);
    
    /** 根据名称查询报表所需配置  */
    ReportConfig getConfigByName(String reportName);

    /** 页面查询条件 */
    Collection<ReportQuery> listQueryByKeys(Collection<Integer> keys);
    
    /** 根据报表名称查询报表查询条件*/
    List<Map<String, Object>> listReportQueryByName(String reportName); 

    /** 根据指定sql查询数据 */
    List<Map<String, Object>> listReportBySql(@Param("reportSql")String reportSql);
    
    /** 查询报表总数 */
    Map<String, Object> findReportCount(@Param("reportSql")String reportSql);
    
    /** 添加报表配置 */
    void insertReportConfig(ReportConfig reportConfig);
    
    /** 添加报表配置查询条件 */
    void insertReportQueryList(List<ReportQuery> list);
    
    void insertReportQuery(ReportQuery reportQuery);
    
    /** 修改报表配置*/
    void updateReportConfig(ReportConfig reportConfig);
    
    /** 修改报表查询条件*/
    void updateReportQuery(ReportQuery reportQuery);
    
    /** 删除报表配置*/
    void deleteReportConfig(Integer id);
    
    /** 删除报表条件*/
    void deleteReportQuery(Integer id);
    
    /** 批量删除报表条件*/
    void deleteListReportQuery(List<String> list);

    /** 调用存储过程 
     * @param parmars */
	List<Map<String, Object>> callStoredProcedure(Map<String, String> parmars);

	/**动态的调用存储过程**/
	
	Collection<HashMap<String,Object>> getDynamicProcedure(@Param("dynamicProcedure")String dynamicProcedure);
	
	/**保存报表条件**/
	Integer saveReportQuery(ReportQuery query);
	
	/**修改报表条件**/
	void BatchUpdtReportQuery(@Param("list") List<ReportQuery> list);
	
	/**获取reportConfig表的最大Report_id**/
	int getReportId();
	
	/**判断条件是否被占用**/
    List<ReportConfig> queryCondionUsed(@Param("reportQueryId") String reportQueryId);
    
    /**判断queryid是否重复**/
    int selectQueryId(@Param("queryId") Integer queryId);
    
    /**删除成功后更新掉reportConfig**/
    void updateReportfgQueryKeys(ReportConfig reportConfig);
    
    /**更新报表锁定状态**/
    int updateLockstaus(ReportConfig reportConfig);

    /**根据主报表ID获取子报表按钮跳转信息**/
	List<ReportSubReport> getSubReportByKey(Integer reportKey);
	
	/**保存子报表**/
	void saveSubReport(ReportSubReport sub);
	
	/**更新子报表**/
	void updateSubReport(ReportSubReport sub);
	
	/**删除子报表**/
	void deleteSubReport(ReportSubReport sub);
	

	/**根据父报表和子报表id查询子报表 **/
	ReportSubReport getSubReportByParentIdSubId(@Param("reportConfigId")Integer reportConfigId, @Param("subReportId")Integer subReportId);
	
}
