package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;

import java.util.List;

/**
 * 派车签收单服务
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
 */
public interface ITransportCarOutService {
	
	/**
	 * 根据公司id和派车单号查询派车签收信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月27日
	 * @param gsid
	 * @param id
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	TransportCarOut queryTransportCarOutByIdAndGs(String gsid,Integer id) throws ParameterException, BusinessException;
	
	/**
	 * 根据公司id和派车单号查询派车签收货物信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月27日
	 * @param gsid
	 * @param id
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	List<TransportCarOutGoodsDetail> queryTransportCarOutGoodsDetailByIdAndGs(String gsid,Integer id) throws ParameterException, BusinessException;
	
	/**
	 * 根据公司id和派车单号查询送货派车单车辆明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月27日
	 * @param gsid
	 * @param id
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	List<TransportCarOutVehicleDetail> queryTransportCarOutVehicleDetaillByIdAndGs(String gsid,Integer id) throws ParameterException, BusinessException;
	
	/**
	 * 查询并校验派车签收单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param DeliveryQueryEntity
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	DeliverySignResult checkDelivery(DeliveryQueryEntity deliveryQueryEntity) throws ParameterException, BusinessException;
	
	/**
	 * 保存送货派车签收单
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param deliverySignResult
	 * @param userModel
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	DeliverySignResult deliverySeconded(DeliverySignResult deliverySignResult,UserModel userModel) throws ParameterException, BusinessException; 
	
	/**
	 * 批量新增送货派车单车辆明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param transportCarOutVehicleDetails
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void batchAddTransportCarOutVehicleDetail(List<TransportCarOutVehicleDetail> transportCarOutVehicleDetails) throws ParameterException, BusinessException;

	/**
	 * 更新送货派车单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param transportCarOut
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void modifyTransportCarOutByIdAndGs(TransportCarOut transportCarOut) throws ParameterException, BusinessException;
	
	/**
	 * 更新送货派车单车辆明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param transportCarOutVehicleDetail
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void modifyTransportCarOutByDetailId(TransportCarOutVehicleDetail transportCarOutVehicleDetail) throws ParameterException, BusinessException;
	
	/**
	 * 如果存在分理费计算装载清单则派车起始地不能选择本部
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param gsid
	 * @param id
	 * @return
	 */
	Integer checkqsd(String gs,Integer id);
	
	/**
	 * 判断起始地在不在公司分理标准价格信息表中
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param mdd
	 * @param gs
	 * @return
	 */
	Integer checkshd(String mdd,String gs);

	/**
	 * 删除送货派车单车辆明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 */
	void removeTransportCarOutVehicleDetailById(String gsid,Integer id) throws ParameterException, BusinessException;
	
	/**
	 * 批量新增派车签收单明细四
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param transportCarOutDetailFourths
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void batchAddTransportCarOutDetailFourth(List<TransportCarOutDetailFourth> transportCarOutDetailFourths) throws ParameterException, BusinessException;
	
	/**
	 * 批量新增派车签收单明细五
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param transportCarOutDetailFifths
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void batchAddTransportCarOutDetailFifth(List<TransportCarOutDetailFifth> transportCarOutDetailFifths) throws ParameterException, BusinessException;
	
	/**
	 * 删除送货派车单明细四
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void removeTransportCarOutDetailFourthById(String gsid,Integer id) throws ParameterException, BusinessException;
	
	/**
	 * 删除送货派车单明细五
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void removeTransportCarOutDetailFifthById(String gsid,Integer id) throws ParameterException, BusinessException;
	
	/**
	 * 根据公司id和派车单号查询派车单明细二
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	List<TransportCarOutDetailSecond> queryTtransportCarOutDetailSecondByIdAndGs(String gsid,Integer id) throws ParameterException, BusinessException;
	
	/**
	 * 根据公司id和派车单号查询派车单明细四
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	List<TransportCarOutDetailFourth> queryTransportCarOutDetailFourthByIdAndGs(String gsid,Integer id) throws ParameterException, BusinessException;
	
	/**
	 * 根据公司id和派车单号查询派车单明细五
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	List<TransportCarOutDetailFifth> queryTransportCarOutDetailFifthByIdAndGs(String gsid,Integer id) throws ParameterException, BusinessException;
	
	/**
	 * 派车签收核算查询
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param deliveryQueryEntity
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	DeliveryAccountingResult deliveryAccountingSearch(DeliveryQueryEntity deliveryQueryEntity) throws ParameterException, BusinessException;
	
	/**
	 * 派车签收核算查询
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月31日
	 * @param deliveryAccountingResult
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	DeliveryAccountingResult deliveryAccountingSave(DeliveryAccountingResult deliveryAccountingResult,UserModel userModel) throws ParameterException, BusinessException;
}
