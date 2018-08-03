package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.dto.CustomerPriceDto;
import com.ycgwl.kylin.transport.entity.adjunct.CustomerPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerPriceMapper {
   
	/**
	 * 
	 * 录入客户价格: <br>
	 * 根据公司,客户编码
	 * 查询客户价格信息
	 * @version [V1.0, 2018年5月16日]
	 * @param Gs
	 * @param Khbm
	 * @return
	 */
	List<CustomerPrice> queryCustomerPriceByGsandKhbm(@Param("Gs") String Gs,@Param("Khbm") String Khbm);
    
	/**
	 * 
	 * 录入价格: <br>
	 * 单条保存价格信息
	 * @version [V1.0, 2018年5月21日]
	 * @param PriceList
	 * @return
	 */
	int insertCustomerPriceInfo(CustomerPriceDto customerPriceDto);
	
	/**
	 * 
	 * 录入价格: <br>
	 * 批量保存价格信息
	 * @version [V1.0, 2018年5月21日]
	 * @param PriceList
	 * @return
	 */
	int batchCustomerPriceInfo(@Param("list") List<CustomerPrice> PriceList);
	
	
	/**
	 * 
	 * 根据登录账号: <br>
	 * 查询录入价格权限
	 * @version [V1.0, 2018年5月21日]
	 * @param Account --登录账号
	 * @return
	 */
	Integer RigthNum(@Param("Account") String Account);
	
	
	/**
	 * 
	 * 查询到站信息: <br>
	 * 根据公司
	 * @version [V1.0, 2018年6月11日]
	 * @param Company
	 * @return
	 */
	List<String>  daozhanByCompany(CustomerPriceDto customerPriceDto);
	
	

	/**
	 * 
	 * 删除价格信息: <br>
	 * 根据公司
	 * @version [V1.0, 2018年6月11日]
	 * @param daozhan
	 *  @param khbm
	 * @return
	 */
	Integer deleteCustomerPriceInfo(CustomerPriceDto customerPriceDto);
	
	
	/**
	 * 
	 * 录入客户价格: <br>
	 * 批量更新收费标准
	 * @version [V1.0, 2018年6月12日]
	 * @param PriceList
	 * @return
	 */
	int updateCustomerPriceInfo(@Param("list") List<CustomerPrice> PriceList);
	
	
	/**
	 * 
	 * 录入客户价格: <br>
	 * 移除收费标准
	 * @version [V1.0, 2018年6月12日]
	 * @param PriceList
	 * @return
	 */
	void removePriceInfo(@Param("Company") String Company,@Param("khbm") String khbm);
	
}
