package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.BillOfdeliveryRequestEntity;

import java.util.HashMap;
import java.util.List;

public interface IDeliveryOperateService {
	/**生成提货单*/
	JsonResult getBillOfdeliveryByYdbhid(List<String> xuhaos,String account,String username,String company) throws ParameterException,BusinessException,Exception ;

	/**生成送货单*/
	JsonResult deliverydocuments(List<String> xuhaos,String account,String company) throws ParameterException,BusinessException, Exception;

	/** 提货单保存 */
	JsonResult saveBillOfdelivery(RequestJsonEntity entity)throws ParameterException,BusinessException,Exception ;

	/** 送车单保存 */
	JsonResult savedeliverydocuments(RequestJsonEntity entity)throws ParameterException,BusinessException,Exception ;
	
	/** 提货装载的查询 */
	JsonResult billOfdeliverymanage(BillOfdeliveryRequestEntity entity)throws ParameterException,BusinessException,Exception ;

	/**撤销提货单*/
	void cancelBillOfdelivery(RequestJsonEntity entity) throws ParameterException,Exception ;

	/**撤销送货单*/
	void canceldeliverydocuments(RequestJsonEntity entity)throws ParameterException,Exception ;
	
	/**送货单的查询*/
	List<HashMap<String,Object>> deliverydocumentsmanage(RequestJsonEntity entity) throws Exception;

	/**送货单的查询-->司机*/
	List<HashMap<String, Object>> deliverydocumentsmanageForDriver(RequestJsonEntity entity) throws ParameterException;

	/**提货签收单的单条查询*/
	JsonResult searchBillOfdelivery(RequestJsonEntity entity);

	/**提货签收单的修改*/
	JsonResult modifyBillOfdelivery(RequestJsonEntity entity);


}
