package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;

import java.util.List;
import java.util.Map;

public interface IBundleArriveService {


	/**查询装载清单*/
	List<BundleArriveEntity> findBundleArriveList(Map<String, Object> map);
	/**
	 * 发送短信
	 * @param param
	 * @return
	 */
	Map<String, Object> checkSendMessage(Map<String, Object> param) throws ParameterException, BusinessException;
	/**
	 * 保存录入取货指令
	 * @param map
	 * @return 
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	JsonResult savePickFormHomeOrder(BundlePickHome bundlePickHome, UserModel userModel) throws ParameterException, BusinessException;
	
	/**
	 * 查询录入取货指令
	 */
	JsonResult searchPickFormHomeOrder(Map<String, Object> map, int openType)
			throws ParameterException, BusinessException;
	/**
	 * 取货派车处理查询
	 * @param map
	 * @return
	 */
	List<DispatchCarPickGoods> searchDeliveryCarHandling(Map<String, Object> map);
	
	
	/**
	 *取货派车签收核算查询
	 * @param map
	 * @return
	 */
	JsonResult searchPickGoodsAccount(Map<String, Object> map) throws ParameterException, BusinessException;
	
	
	/**
	 * 保存取货派车签收核算
	 * @param map
	 * @return 
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void savePickGoodsAccount(BundleAccount bundleAccount) throws ParameterException, BusinessException;
	/**
	 * 取货派车单的单条查询
	 * TODO Add description
	 * <p>
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-03-08 13:45:01
	 * @param entity
	 * @return
	 */
	JsonResult getT_Car_InForPrint(RequestJsonEntity entity) throws BusinessException,ParameterException;
	
}
