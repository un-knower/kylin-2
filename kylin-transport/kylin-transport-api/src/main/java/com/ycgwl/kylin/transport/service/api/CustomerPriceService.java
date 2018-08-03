package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.dto.CustomerPriceDto;
import com.ycgwl.kylin.transport.entity.CustomerPriceResult;

/**
 * 
 * 录入客户价格登记<br> 
 * @author zdl
 * @version [V1.0, 2018年5月16日]
 */
public interface CustomerPriceService {
     
	/**
	 * 根据公司和客户编码
	 * 查询客户信息
	 * @author zdl
	 * @Date 2018-05-14
	 * @param Gs --当前登录公司
	 * @param khbm--客户编码
	 */
	JsonResult customerInfoByKhBMandGs(CustomerPriceDto customerPriceDto) throws ParameterException;
 
	/**
	 * 
	 * 批量保存客户价格信息<br>
	 * @version [V1.0, 2018年5月15日]
	 * @param customerPriceResult
	 * @return
	 * @throws ParameterException
	 */
	JsonResult batchCustomerPriceInfo(CustomerPriceResult customerPriceResult) throws ParameterException;
 
	/**
	 * 
	 * 查询到站信息<br>
	 * @version [V1.0, 2018年6月11日]
	 * @param customerPriceResult
	 * @return
	 * @throws ParameterException
	 */
	JsonResult daozhanByCompany(CustomerPriceDto customerPriceDto) throws ParameterException;
	
	/**
	 * 
	 * 删除收费标准<br>
	 * @version [V1.0, 2018年6月11日]
	 * @param daozhan--到站
	 * @param khbm--客户编码
	 * @return
	 * @throws ParameterException
	 */
	JsonResult deleteCustomerPriceInfo(CustomerPriceDto customerPriceDto) throws ParameterException;
	
	
	
	
	
}
