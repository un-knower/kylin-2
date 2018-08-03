package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.ExceptionLog;
import com.ycgwl.kylin.transport.entity.ExceptionLogResult;
import com.ycgwl.kylin.transport.entity.ExceptionLogSearch;

/**
 * 异常日志服务
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
 */
public interface IExceptionLogService {

	/**
	 * 新增异常日志信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
	 * @param exceptionLog
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void addExceptionLog(ExceptionLog exceptionLog) throws ParameterException, BusinessException;
	
	/**
	 * 分页查询异常日志信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
	 * @param exceptionLogSearch
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	PageInfo<ExceptionLogResult> pageTransportOrder(ExceptionLogSearch exceptionLogSearch, int pageNum, int pageSize) throws ParameterException, BusinessException;

	void CancelLogInsert(RequestJsonEntity entity) throws Exception;
}
