package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.FinanceReceiveMoney;
import com.ycgwl.kylin.transport.entity.FinanceReceiveMoneyForm;
import com.ycgwl.kylin.transport.entity.FinanceReceiveMoneyResult;

/**
 * 收钱逻辑和冲红逻辑
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 */
public interface FinanceReceiveMoneyService {
	
	/**
	 * 保存收钱信息.
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017-10-25 09:56:57
	 * @return 签收的提示编码
	 * @throws ParameterException  参数有误时
	 * @throws BusinessException   业务逻辑处理有误时
	 * <p>
	 * @throws Exception 
	 */
	FinanceReceiveMoneyResult saveReceviceMoney(FinanceReceiveMoneyForm formFkfsh,FinanceReceiveMoney session) throws ParameterException, BusinessException, Exception;
	
	/**
	 * 根据输入的受理单编号（财凭号）触发的查询方法.
	 * <p>
	 * @param dbFkfsh 
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017-10-25 09:56:57
	 * @return 页面信息
	 * @throws ParameterException  参数有误时
	 * @throws BusinessException   业务逻辑处理有误时
	 * <p>
	 */
	FinanceReceiveMoneyResult searchBeforeReceiveMoney(FinanceReceiveMoneyForm formFkfsh) throws ParameterException, BusinessException;
	
	/**
	 * 冲红（抵消财凭信息）
	 * <p>
	 * @param dbFkfsh 
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017-12-07 09:56:57
	 * @return 页面信息
	 * @throws ParameterException  参数有误时
	 * @throws BusinessException   业务逻辑处理有误时
	 * <p>
	 */
	String offsetWealthInfo(FinanceReceiveMoneyForm saveFkfsh) throws ParameterException, BusinessException;
	
	/**
	 * 月结收钱
	 * <p>
	 * @param dbFkfsh 
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017-12-20 09:56:57
	 * @return 页面信息
	 * @throws ParameterException  参数有误时
	 * @throws BusinessException   业务逻辑处理有误时
	 * <p>
	 * @throws Exception 
	 */
	FinanceReceiveMoneyResult saveMonthReceviceMoney(FinanceReceiveMoneyForm formFkfsh)
			throws ParameterException, BusinessException, Exception;

	/**
	 * 记录打印次数和最后打印时间 
	 * @param waybillNum
	 * @return
	 */
	JsonResult updatePrintCountAndDate(String waybillNum);

	/**
	 * 运输受理单打印
	 */
	JsonResult searchWealthPrint(FinanceReceiveMoneyForm formFkfsh) throws ParameterException, BusinessException;
	
	/**
	 * 查询冲红信息
	 * <p>
	 * @param dbFkfsh 
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017-12-06 09:56:57
	 * @return 页面信息
	 * @throws ParameterException  参数有误时
	 * @throws BusinessException   业务逻辑处理有误时
	 * <p>
	 */
	JsonResult searchOffsetWealthInfo(FinanceReceiveMoneyForm formFkfsh,Integer userright) throws ParameterException, BusinessException;

}
