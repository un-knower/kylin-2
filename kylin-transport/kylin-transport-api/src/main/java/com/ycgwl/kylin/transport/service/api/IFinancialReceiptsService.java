package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 分理收据服务
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月11日
 */
public interface IFinancialReceiptsService {
	
	/**
	 * 查询分理收据主表信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月11日
	 * @param id
	 * @param gs
	 * @param ydbhid
	 * @return
	 */
	FinancialReceiptsMaster queryFinancialReceiptsMasterByYdbhidAndGs(Integer id, String gs, String ydbhid) throws ParameterException, BusinessException;

	/**
	 * 查询分理收据明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月11日
	 * @param sjid
	 * @param gs
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	List<FinancialReceiptsDetail> queryFinancialReceiptsDetailByYdbhidAndGs(Integer sjid, String gs, String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 根据公司和运单编号查询分理收据主表信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月11日
	 * @param gs
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	FinancialReceiptsMaster queryFinancialReceiptsMasterByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 跳转分理收据页面并判断校验权限
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月11日
	 * @param financialReceiptsQueryEntity
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	FinancialReceiptsResult financialReceiptsInformation(FinancialReceiptsQueryEntity financialReceiptsQueryEntity) throws ParameterException, BusinessException;
	
	/**
	 * 根据公司和年份生成分理收据单号
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月16日
	 * @param map
	 */
	Map<String,Object> udtGetCwsjid(Map<String,Object> map) throws ParameterException, BusinessException;
	
	/**
	 * 新增分理收据主表信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月17日
	 * @param financialReceiptsMaster
	 */
	void addFinancialReceiptsMaster(FinancialReceiptsMaster financialReceiptsMaster);
	
	/**
	 * 批量新增分理收据明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月17日
	 * @param financialReceiptsDetails
	 */
	void batchAddFinancialReceiptsDetail(List<FinancialReceiptsDetail> financialReceiptsDetails);
	
	/**
	 * 保存分理收据信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月17日
	 * @param financialReceiptsResult
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void saveFinancialReceipts(FinancialReceiptsMaster financialReceiptsMaster,List<FinancialReceiptsDetail> financialReceiptsDetailList,
			FinancialReceiptsQueryEntity financialReceiptsQueryEntity) throws ParameterException, BusinessException;
	
	/**
	 * 跳转至分理收据交钱页面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月17日
	 * @param financialReceiptsQueryEntity
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	FinancialReceiptsResult financialReceiptsPayMoney(FinancialReceiptsQueryEntity financialReceiptsQueryEntity) throws ParameterException, BusinessException;
	
	/**
	 * 根据收据号查询分理收据信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月19日
	 * @param id
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	FinancialReceiptsMaster queryFinancialReceiptsMasterById(Integer id, String gs) throws ParameterException, BusinessException;
	
	/**
	 * 根据收据号和公司更新分理收据信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月19日
	 * @param financialReceiptsMaster
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void modifyFinancialReceiptsMasterByIdAndGs(FinancialReceiptsMaster financialReceiptsMaster) throws ParameterException, BusinessException;
	
	/**
	 * 根据收据号、公司和细则号更新分理收据明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月19日
	 * @param financialReceiptsDetail
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void modifyFinancialReceiptsDetailBySjidAndGsAndXzh(FinancialReceiptsDetail financialReceiptsDetail) throws ParameterException, BusinessException;
	
	/**
	 * 分理收据交钱
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月19日
	 * @param financialReceiptsMaster
	 * @param financialReceiptsDetailList
	 * @param financialReceiptsQueryEntity
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void savefinancialReceiptsPayMoney(FinancialReceiptsMaster financialReceiptsMaster,List<FinancialReceiptsDetail> financialReceiptsDetailList,
			FinancialReceiptsQueryEntity financialReceiptsQueryEntity) throws ParameterException, BusinessException;

	/**
	 * 根据收据号或者运单编号查询分理收据明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月22日
	 * @param sjid
	 * @param gs
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	List<FinancialReceiptsDetail> queryFinancialReceiptsDetailByYdbhidOrGs(Integer sjid, String gs, String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 查询分理收据冲红信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月22日
	 * @param financialReceiptsQueryEntity
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	FinancialReceiptsResult searchOffsetFinancialReceipts(FinancialReceiptsQueryEntity financialReceiptsQueryEntity) throws ParameterException, BusinessException;
	
	/**
	 * 分理收据冲红
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月23日
	 * @param sjid
	 * @param offsetReason
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	Integer offsetFinancialReceipts(OffsetFinancialReceiptsQueryEntity offsetFinancialReceiptsQueryEntity) throws ParameterException, BusinessException;
	
	/**
	 * 分理收据冲红
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月23日
	 * @param map
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	Map<String,Object> offsetFinancialReceipts(Map<String,Object> map) throws ParameterException, BusinessException;
	
	/**
	 * 判断是否生成分理收据
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年2月9日
	 * @param financialReceiptsQueryEntity
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	@Deprecated
	FinancialReceiptsResult isGenerateFinancialReceipts(FinancialReceiptsQueryEntity financialReceiptsQueryEntity) throws ParameterException, BusinessException;
}
