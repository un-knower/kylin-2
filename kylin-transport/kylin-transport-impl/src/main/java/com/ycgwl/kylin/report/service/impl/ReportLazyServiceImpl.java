package com.ycgwl.kylin.report.service.impl;
/**
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2018/3/28
 */

import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.report.persistent.ReportLazyMapper;
import com.ycgwl.kylin.report.service.api.ReportLazyService;
import com.ycgwl.kylin.transport.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

@Service("kylin.transport.dubbo.local.reportLazyService")
public class ReportLazyServiceImpl implements ReportLazyService {

    @Resource
    ReportLazyMapper reportLazyMapper;

    public Collection<Integer> integerCollection(String source) {
        if(StringUtils.isNotBlank(source)) {
            String[] arrays = source.split("[^0-9]+");
            if(arrays != null && arrays.length > 0) {
                Collection<Integer> collection = new ArrayList<Integer>(arrays.length);
                for (int i = 0; i < arrays.length; i++) {
                    collection.add(Integer.parseInt(arrays[i]));
                }
                return collection;
            }
        }
        return null;
    }

    /** 报表查询条件*/
    @Override
    public Collection<ReportQuerySomething> listByReportKey(String account, Integer reportKey) throws ParameterException, BusinessException {
        ReportConfig reportConfig = reportLazyMapper.getConfigByKey(reportKey);
        if(reportConfig == null){
            throw new ParameterException("reportKey", reportKey ,"指定的报表配置不存在");
        }
        if(StringUtils.isBlank(reportConfig.getReportQueryKeys())){
            throw new ParameterException("指定的报表没有配置查询条件");
        }
        Collection<Integer> reportQueryKeys = integerCollection(reportConfig.getReportQueryKeys());
        if(CollectionUtils.isEmpty(reportQueryKeys)){
            throw new ParameterException("指定的报表没有配置查询条件");
        }
        Collection<ReportQuery> querys = reportLazyMapper.listQueryByKeys(reportQueryKeys);
        Collection<ReportQuerySomething> somethings = new ArrayList<ReportQuerySomething>(querys.size());
        for (ReportQuery query : querys) {
            somethings.add(convert(account, query));
        }
        return somethings;
    }      

    /** 查询报表数据 */
    @Override
    public Report loadReportByKey(String account, Integer reportKey, Map<String, String> parmars) throws ParameterException, BusinessException{
    	ReportConfig reportConfig = reportLazyMapper.getConfigByKey(reportKey);
        if(reportConfig == null){
            throw new ParameterException("reportKey", reportKey ,"指定的报表配置不存在");
        }
        Report report = new Report();
        String reportHeaderColumn[] = reportConfig.getReportHeaders().replaceAll("\\s*", "").split(",");
        report.setSumReportKeys(reportConfig.getReportSumKey());
        report.setHeaders(reportHeaderColumn);
        String reportHeaderName[] = reportConfig.getReportHeaderNames().replaceAll("\\s*", "").split(",");
        report.setReportName(reportConfig.getReportName());
        report.setReportHeadNames(reportConfig.getReportHeaderNames());
        String dataBase = reportConfig.getDataBaseKey();
        StringBuffer buffer = new StringBuffer();
        String sql = reportConfig.getReportSelectSql();
        buffer.append("select * from ( ");
        StringBuffer parameterSql = new StringBuffer();
        //buffer.append(sql);       
        
        for (Map.Entry<String,String> stringStringEntry : parmars.entrySet()) {
        	String key = stringStringEntry.getKey();
        	String value = stringStringEntry.getValue();
        	if(!key.equals("reportKey")&&!key.equals("pageSize")&&!key.equals("pageNum")&&!key.equals("company")&&!key.equals("export")){
    			if(StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)){
					if(value.split("-").length>1){
						try {
							String start = value.substring(0, 10);
							String end = value.substring(13, 23);
							parameterSql.append(" and " + key + ">='" + start + " 00:00:00'");
							parameterSql.append(" and " + key + "<='" + end + " 23:59:59'");
						} catch (Exception e) {
							parameterSql.append(" and " + key + " like '%" + value.trim() + "%'");
						}
    						
    					}else {
    		    	        // 发运预警天数  no_fy   签收预警天数   no_qs   签收超时天数   qs_cs   返单预警天数   no_fd    返单超时天数   fd_cs
    		    	        String[] arr  = new String[] {"no_fy","no_qs","qs_cs","no_fd","fd_cs"};
    		    	        if (Arrays.asList(arr).contains(key) && "3".equals(value)){
    		    	        	parameterSql.append(" and " + key + " >= '" + value + "'");
    		    	        } else {
    		    	        	Pattern pattern = Pattern.compile("[0-9]*");
    		    	        	boolean isNumber = pattern.matcher(value).matches();   
    		    	        	//如果查询条件传入的是数字,如签收状态为"完好签收"--传入值value=11时,则判断为数字
    		    	        	if(isNumber){
    		    	        	   parameterSql.append(" and " + key + "='" + value.trim()+ "'");
    		    	        	} else {
    		    	        	  //否则传入的字符串	
    							   parameterSql.append(" and " + key + " like '%" + value.trim() + "%'");
    		    	        	}
    		    	         
    		    	        }
    					}
    			}
        	}        	
        }
       
        if (StringUtils.isNotEmpty(account)&&!account.equals("总公司") && !account.equals("所有公司") ){
        	if (sql.indexOf("返单超时3天") != -1) {
        	}else {
        		if (null != dataBase && !"".equals(dataBase)) {
        			parameterSql.append(" and "+dataBase+"='"+account +"'");
        		}
        		
        	}
        	
        }
        sql=this.replaceSql(parameterSql.toString(), sql, "coulum1");
        buffer.append(sql);
        if (StringUtils.isNotEmpty(reportConfig.getGroupByKey()) && null != reportConfig.getIsListGroup()){
        	buffer.append(" group by " +reportConfig.getGroupByKey());
        }

       
        buffer.append(" ) ttt ");       
        Map<String, Object> count = reportLazyMapper.findReportCount(buffer.toString());
        List<Map<String, Object>> collection = reportLazyMapper.listReportBySql(
        		jointOrderBySql(reportConfig.getOrderByKey(),buffer.toString(),parmars.get("pageSize"),parmars.get("pageNum"),parmars.get("export")));       
        
        report.setCount((int) count.get("counts"));
        
        if(collection.size()>0) {
        	List<Map<String, Object>> sumList = sumReportByKey(account, parmars);
            if(sumList!=null&&!sumList.isEmpty()) {
            	Map<String,Object> sumObject = !sumList.isEmpty()?sumList.get(1):null;
                Map<String,Object> headerMap = new LinkedHashMap<String,Object>();
                for(int i=0;i<reportHeaderColumn.length;i++) {
                	headerMap.put(reportHeaderColumn[i], reportHeaderName[i]);//字段名称和中文列名
                }
                for(String key : headerMap.keySet()){
                	headerMap.put(key,sumObject.get(headerMap.get(key)));
                }
                collection.add(0, headerMap);
            }
        }
        report.setDataCollection(collection);
        return report;
    }

    /** 处理报表查询条件结果*/
    private ReportQuerySomething convert(String account, ReportQuery reportQuery){
        ReportQuerySomething something = new ReportQuerySomething();
        something.setQueryName(reportQuery.getQueryName());
        something.setQueryKey(reportQuery.getQueryKey());
        something.setQueryType(reportQuery.getQueryType());
        something.setQueryDefaultValue(reportQuery.getQueryDefaultValue());
        something.setQueryId(reportQuery.getQueryId());
        something.setQuerySrcType(reportQuery.getQuerySrcType());
        something.setQuerySrc(reportQuery.getQuerySrc());
        return something;
    }

    /** 根据公司账号*/
    @SuppressWarnings("unused")
	private Map<String, String> company(String account){
        //TODO 根据当前用户账号查询 分公司数据, 将分公司数据对象转成 map  ID作为key,分公司名称作为value
        return null;
    }

    /** 同一分页拼接sql */
    public String jointOrderBySql(String orderByKey, String sql,String pageSize,String pageNum,String export) {
    	StringBuffer sqlBuf = new StringBuffer();
    	int size=Integer.valueOf(pageSize);
    	int num=Integer.valueOf(pageNum);
    	sqlBuf.append(" SELECT * FROM(SELECT ROW_NUMBER () OVER ");
    	sqlBuf.append("(ORDER BY Bf1."+ orderByKey +" DESC) RowNumber ,* FROM ");
    	sqlBuf.append("( "+ sql +" ) Bf1");
    	sqlBuf.append(" ) Af1 WHERE 1=1 ");
    	//if (StringUtils.isEmpty(export)) {
    		sqlBuf.append(" and Af1.RowNumber BETWEEN "+ ((num-1) * size+1) + " and " + num * size);
    	//}
    	return sqlBuf.toString();
    }
	
    String getReportQueryKeys(String reportquery, String reportName) {
		String queryKey="";
		if(StringUtils.isNotEmpty(reportName)) {
			List<Map<String,Object>> list = reportLazyMapper.listReportQueryByName(reportName);
			if(CollectionUtils.isNotEmpty(list)){
				String reportId = "";
				for(Map<String,Object> map:list) {
					if(StringUtils.isEmpty(reportquery)){
						reportId = map.get("query_id")+"";
					}else{
					  reportId +=","+map.get("query_id");
					}
				}
				queryKey=reportId;
			}
		}
		return queryKey;
	}

	/** 合计报表数据*/
	@Override
	public List<Map<String, Object>> sumReportByKey(String company, Map<String, String> parmars) {
		List<Map<String, Object>> listMap = null;
			if(StringUtils.isNotEmpty(company) && null != parmars){
				ReportConfig reportConfig = reportLazyMapper.getConfigByKey(Integer.valueOf(parmars.get("reportKey")));
				if (StringUtils.isNotEmpty(reportConfig.getReportSumKey()) && StringUtils.isNotEmpty(reportConfig.getReportSumSql())) {
					StringBuffer buffer = new StringBuffer();
					String dataBase = reportConfig.getDataBaseKey();
					String sql = reportConfig.getReportSumSql();
					StringBuffer parameterSql = new StringBuffer();
			
					for (Map.Entry<String,String> stringStringEntry : parmars.entrySet()) {
			        	String key = stringStringEntry.getKey();
			        	String value = stringStringEntry.getValue();
		    			if(StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value) && !key.equals("reportKey")&&!key.equals("company")&&!key.equals("pageSize")&&!key.equals("pageNum")&&!key.equals("export")){
		    					//目前检测时间的方法
		    					if(value.split("-").length>1){
		    						try {
		        						String start = value.substring(0, 10);
		        						String end = value.substring(13, 23);
		        						parameterSql.append(" and " + key + ">='" + start + " 00:00:00.0'");
		        						parameterSql.append(" and " + key + "<='" + end + " 23:59:59.999'");
									} catch (Exception e) {
										parameterSql.append(" and " + key + " like '%" + value.trim() + "%'");
									}
		    						
		    					}else {
		    						parameterSql.append(" and " + key + " like '%" + value.trim() + "%'");
		    					}    					
		    			}
			        	
			        }
			        // 暂时解决客户标签报表的查询sql 后续再改
			        if (StringUtils.isNotEmpty(company) && !company.equals("总公司") && !company.equals("所有公司") ) {
		        		if (StringUtils.isNotEmpty(dataBase)) {
		        			parameterSql.append(" and "+dataBase+"='"+company + "'");
		        		}
			        }
			        if (StringUtils.isNotEmpty(reportConfig.getGroupByKey())){
			        	buffer.append(" group by " +reportConfig.getGroupByKey());
			        };
			
			        // 拼接替换掉sql查询里面预留的字段
					sql=this.replaceSql(parameterSql.toString(), sql, "coulum1");
			        if(StringUtils.isNotEmpty(reportConfig.getReplaceSql())){
			        	sql = this.replaceSql(sql, reportConfig.getReplaceSql(),"?");
			        }
			        
			        
			       // 针对查询有分组，合计不需要分组进行判断
					if(StringUtils.isNotEmpty(reportConfig.getReportSumSql()) && StringUtils.isNotEmpty(reportConfig.getGroupByKey())){
						listMap= reportLazyMapper.listReportBySql(sql.toString());
					}else{
						buffer.append(sql);
						listMap= reportLazyMapper.listReportBySql(buffer.toString());
					}
			        
			        
			        if (CollectionUtils.isNotEmpty(listMap)) {
			        	Map<String, Object> map = new HashMap<String, Object>();
				        map.put("reportSumKey",reportConfig.getReportSumKey().replaceAll("\\s*", ""));
				        listMap.add(0, map);
			        }			        
				}
			}		
		return listMap;
	}
	
	String replaceSql(String sql,String replaceStr,String symbolStr){
		String str = null;
		if (StringUtils.isNotEmpty(replaceStr) && StringUtils.isNotEmpty(sql) && StringUtils.isNotEmpty(symbolStr)){
			if(replaceStr.indexOf(symbolStr)>-1){
				str=replaceStr.replace(symbolStr, sql);
			}else {
				str = replaceStr + sql;
			}
		} 
		
		return str;
	}
	
    /** 根据报表id查询配置*/
	@Override
	public ReportConfig findReportbyKey(Integer reportKey) {
		ReportConfig report = reportLazyMapper.getConfigByKey(reportKey);
		List<ReportSubReport> subList = reportLazyMapper.getSubReportByKey(reportKey);
        report.setSubList(subList);
		return report;
	}

	@Override
	public ReportSubReport getSubReportByParentIdSubId(Integer reportConfigId, Integer subReportId) {
		return reportLazyMapper.getSubReportByParentIdSubId(reportConfigId,subReportId);
	}
}
