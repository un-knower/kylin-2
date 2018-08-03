package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.ExceptionLog;
import com.ycgwl.kylin.transport.entity.ExceptionLogSearch;

import java.util.Collection;

/**
 * 异常日志持久层
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
 */
public interface ExceptionLogMapper {
	
	/**
	 * 新增异常日志信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
	 * @param exceptionLog
	 */
	void insertExceptionLog(ExceptionLog exceptionLog);
	
	/**
	 * 分页查询异常日志信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
	 * @param exceptionLogSearch
	 * @return
	 */
	Collection<ExceptionLog> queryExceptionLogPages(ExceptionLogSearch exceptionLogSearch);
	
	/**
	 * 查询总条数
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
	 * @param exceptionLogSearch
	 * @return
	 */
	Long queryExceptionLogPagesCount(ExceptionLogSearch exceptionLogSearch);
}
