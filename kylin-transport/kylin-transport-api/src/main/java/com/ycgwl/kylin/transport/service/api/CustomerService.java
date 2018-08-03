package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.dto.CustomerDto;
import com.ycgwl.kylin.transport.dto.CustomerInfoDto;
import com.ycgwl.kylin.transport.dto.DaoZhanDto;
import com.ycgwl.kylin.transport.dto.RecurringCustomerDto;


/**
 * 用户基础数据表
 * @author Acer
 *
 */
public interface CustomerService {

	/**
	 * 查询公司的到站  和  没有到站的信息
	 * @param company
	 * @return
	 */
	JsonResult selectDaoZhanBaseData(String company);

	/**
	 * 跟新公司的到站信息
	 * @param company
	 * @param daoZhanDto
	 * @return
	 */
	JsonResult editDaoZhanBaseData(String account, String company, DaoZhanDto daoZhanDto);

	/**
	 * 查询周期性结款客户信息
	 * @param account
	 * @param daoZhanDto
	 * @return
	 */
	JsonResult findRecurringCustomers(String account, CustomerDto customerDto);

	/**
	 * 修改周期性结款客户信息
	 * @param account
	 * @param recurringCustomerDto
	 * @return
	 */
	JsonResult editRecurringCustomers(String account, RecurringCustomerDto recurringCustomerDto);
	
	/**
	 * 根据公司和业务单号
	 * 查询客户信息
	 * @author zdl
	 * @Date 2018-05-14
	 * @param company --当前登录公司
	 * @param ywjdId--业务接单号
	 */
	JsonResult customerInfoByYwdhIdandGs(CustomerInfoDto customerInfoDto) throws ParameterException;
    
	/**
	 * 
	 * 保存客户基本信息: <br>
	 * @version [V1.0, 2018年5月15日]
	 * @param customerInfoDto
	 * @return
	 * @throws ParameterException
	 */
	JsonResult saveCustomerInfo(CustomerInfoDto customerInfoDto) throws ParameterException;

}
