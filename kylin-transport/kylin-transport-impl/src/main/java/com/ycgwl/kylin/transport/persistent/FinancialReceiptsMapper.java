package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.FinancialReceiptsDetail;
import com.ycgwl.kylin.transport.entity.FinancialReceiptsMaster;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 分理收据持久层
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月10日
 */
public interface FinancialReceiptsMapper {

	/**
	 *	查询分理收据主表信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月10日
	 * @return
	 */
	FinancialReceiptsMaster selectFinancialReceiptsMasterByYdbhidAndGs(@Param("id")Integer id, @Param("gs")String gs,@Param("ydbhid")String ydbhid);
	
	/**
	 * 查询分理收据明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月10日
	 * @param sjid
	 * @param gs
	 * @param ydbhid
	 * @return
	 */
	List<FinancialReceiptsDetail> selectFinancialReceiptsDetailByYdbhidAndGs(@Param("sjid")Integer sjid, @Param("gs")String gs,@Param("ydbhid")String ydbhid);
	
	/**
	 * 根据公司和运单编号查询分理收据主表信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月11日
	 * @param gs
	 * @param ydbhid
	 * @return
	 */
	FinancialReceiptsMaster selectFinancialReceiptsMasterByYdbhid(@Param("ydbhid")String ydbhid);
	
	/**
	 * 根据公司和年份生成分理收据单号
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月16日
	 * @param gs
	 * @param nf
	 * @return
	 */
	void udtGetCwsjid(Map<String,Object> map);
	
	/**
	 * 新增分理收据主表信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月17日
	 * @param financialReceiptsMaster
	 */
	void insertFinancialReceiptsMaster(FinancialReceiptsMaster financialReceiptsMaster);
	
	/**
	 * 批量新增分理收据明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月17日
	 * @param financialReceiptsDetails
	 */
	void batchInsertFinancialReceiptsDetail(List<FinancialReceiptsDetail> financialReceiptsDetails);
	
	/**
	 * 根据id查询分理收据主表信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月18日
	 * @param id
	 * @return
	 */
	FinancialReceiptsMaster selectFinancialReceiptsMasterById(@Param("id")Integer id, @Param("gs")String gs);
	
	/**
	 * 根据收据号和公司更新分理收据信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月19日
	 * @param financialReceiptsMaster
	 */
	void updateFinancialReceiptsMasterByIdAndGs(FinancialReceiptsMaster financialReceiptsMaster);
	
	/**
	 * 根据收据号、公司和细则号更新分理收据明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月19日
	 * @param financialReceiptsDetail
	 */
	void updateFinancialReceiptsDetailBySjidAndGsAndXzh(FinancialReceiptsDetail financialReceiptsDetail);
	
	/**
	 *  根据公司和运单号查询分理收据明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月22日
	 * @param gs
	 * @param ydbhid
	 * @return
	 */
	List<FinancialReceiptsDetail> selectFinancialReceiptsDetailByYdbhid(@Param("gs")String gs,@Param("ydbhid")String ydbhid);
	
	/**
	 *  根据公司和收据号查询分理收据明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月22日
	 * @param gs
	 * @param sjid
	 * @return
	 */
	List<FinancialReceiptsDetail> selectFinancialReceiptsDetailBySjid(@Param("gs")String gs,@Param("sjid")Integer sjid);
	
	/**
	 * 分理收据冲红
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月23日
	 * @param map
	 */
	void offsetFinancialReceipts(Map<String,Object> map);
}
