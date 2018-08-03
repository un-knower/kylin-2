package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.TransportOrderCancelResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TransportOrderCancelMapper {
	/**
	 *  更新作废表
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年11月29日
	 * @param TransportOrderCancelResult 查询条件
	 * @return 查询结果
	 */
	@Update("update HWYD_ZUOFEI set xgrgonghao=#{xgrgonghao},xgrjiqi=#{gs_host} where ydbhid=#{ls_ydbhid}")
	void cancelTransportOrder(@Param("xgrgonghao")String xgrgonghao, @Param("gs_host")String gs_host,@Param("ls_ydbhid")String ls_ydbhid);

	/**
	 * 查询该运单是否存在
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年11月29日
	 * @param ls_ydbhid
	 * @param companyName
	 * @return
	 */
	@Select("select count(*) from HWYD where ydbhid = #{code} and fazhan = #{companyName}")
	int countTransportOrderByCompanyName(@Param("code")String code, @Param("companyName")String companyName);

	/**
	 * 查询该运单是否存在
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年11月29日 
	 * @param ls_ydbhid
	 * @param companyName
	 * @return
	 */
	@Select("select count(*) from HWYD where ydbhid = #{code}")
	int countTransportOrderByCode(String code);
	
	/**
	 * 通过运单号查询装载清单个数
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年11月29日
	 * @param ls_ydbhid
	 * @param companyName
	 * @return
	 */
	@Select("select count(*) from HCZZQD_source where YDBHID = #{transportCode}")
	int countBundleReceiptByTransportCode(String transportCode);

	/**
	 * 查询该运单号是否生成财凭
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年11月29日
	 * @param ls_ydbhid
	 * @param companyName
	 * @return
	 */
	@Select("select count(*) from FIWT where HWY_YDBHID = #{transportCode} and type=0")
	int countTransportFinanceByTransportCode(String transportCode);

	/**
	 * 查询货物运单作废信息
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年11月29日
	 * @param 运单号
	 * @return 运单作废信息
	 */
	@Select("SELECT ydbhid as transportCode,fazhan as origStation,zuofei as cancelStatus,beizhu as description,czygh,xgrgonghao,xgrjiqi FROM hwyd_zuofei WHERE ydbhid=#{transportCode}")
	List<TransportOrderCancelResult> findTransportOrderCancel(String transportCode);

	/**
	 * 查询货物运单作废信息
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年11月29日
	 * @param 运单号,公司名称
	 * @return 运单作废信息
	 */
	@Select("SELECT ydbhid as transportCode,fazhan as origStation,zuofei as cancelStatus,beizhu as description,czygh,xgrgonghao,xgrjiqi FROM hwyd_zuofei where ydbhid=#{transportCode} and fazhan=#{companyName}")
	List<TransportOrderCancelResult> findTransportOrderCancelByCompany(@Param("transportCode")String transportCode, @Param("companyName")String companyName);

	/**
	 * 查询运单的发站信息
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年11月29日
	 * @param 运单号,公司名称
	 * @return 运单作废信息
	 */
	@Select("select fazhan from hwyd where ydbhid = #{transportCode}")
	String findFazhanByTransportCode(String transportCode);

	/**
	 * 废除运单/取消废除运单
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年11月29日
	 * @param 运单号,公司名称
	 * @return 运单作废信息
	 */
	@Update("update hwyd_zuofei set fazhan=#{origStation},zuofei=#{cancelStatus},beizhu=#{description},czygh=#{czygh},czshijian=getDate() where ydbhid = #{transportCode}")
	void updateCancelTransportOrder(TransportOrderCancelResult cancel);
	
	/**
	 * 废除运单/取消废除运单
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年11月29日
	 * @param 运单号,公司名称
	 * @return 运单作废信息
	 */
	@Update("insert into hwyd_zuofei (fazhan,zuofei,beizhu,czygh,czshijian,ydbhid) values (#{origStation},#{cancelStatus},#{description},#{czygh},getDate(),#{transportCode})")
	void saveCancelTransportOrder(TransportOrderCancelResult cancel);
	
	/**
	 * 废除运单/取消废除运单
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017年11月29日
	 * @param 运单号,公司名称
	 * @return 运单作废信息
	 */
	@Update("insert into hwyd_zuofei_log (fazhan,zuofei,beizhu,czygh,czshijian,ydbhid) values (#{origStation},#{cancelStatus},#{description},#{czygh},getDate(),#{transportCode})")
	void saveCancelTransportOrderLog(TransportOrderCancelResult cancel);

	@Select("select count(*) from Fiwt where HWY_YDBHID=#{ydbhid} and xianlu=#{xianlu}  and type>0")
	int countFiwtChonghong(@Param("ydbhid")String ydbhid,@Param("xianlu")String xianlu);

	@Update("update HWYD set cancel_status = #{cancelStatus} where YDBHID = #{transportCode}")
	void updateTransportCancelStatus(@Param("transportCode")String transportCode, @Param("cancelStatus")int cancelStatus);
	
}
