package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.dto.CustomerDto;
import com.ycgwl.kylin.transport.dto.RecurringCustomerDto;
import com.ycgwl.kylin.transport.vo.CustomerVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustomerMapper {
	
	/**
	 * 查询到站
	 * @param company
	 * @return
	 */
	List<String> selectDaoZhanBaseData(String company);

	/**
	 * 查询未到站
	 * @param company
	 * @return
	 */
	List<String> selectNoDaoZhanBaseData(String company);

	/**
	 * 跟新公司的到站信息
	 * @param split
	 * @return
	 */
	int saveDaoZhanBaseData(Map<String, Object> map);

	/**
	 * 删除到站信息
	 * @param company
	 */
	void removeDaoZhanBaseData(String company);

	/**
	 * 查询周期性结款客户信息
	 * @param customerDto
	 * @return
	 */
	List<CustomerVo> findRecurringCustomers(CustomerDto customerDto);


	/**
	 * 根据客户编码预以及客户名称来获取客户信息
	 * @param customerDto
	 * @return
	 */
	CustomerVo findRecurringCustomerByKhbmAndKhmc(CustomerDto customerDto);

	/**
	 * 修改周期性结款客户信息
	 * @param recurringCustomerDto
	 * @return
	 */
	int editRecurringCustomers(RecurringCustomerDto recurringCustomerDto);

}
