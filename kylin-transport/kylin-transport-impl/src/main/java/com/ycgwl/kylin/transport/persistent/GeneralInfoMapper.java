package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GeneralInfoMapper {

	@Select("select convert(varchar(10),min)+'-'+convert(varchar(10),max) as lc,min,max,shlc from  sys_shlc_parm group by min,max,shlc order by min,max")
	List<BundleArriveMileage> pickGoodsMileage();

	@Select("select shlc,convert(varchar(10),min)+'-'+convert(varchar(10),max) as lc,min,max,Mdd from sys_shlc_parm WHERE ( gs =#{companyName} ) order by min,max")
	List<BundleArriveMileage> pickGoodsAddress(@Param("companyName") String companyName);

	@Select("select key_name as keyName,isnull(key_value_str,key_value) as keyValue from transport_basic_data where name = #{cnName}")
	List<TransportBasicData> queryBasicInfoByName(@Param("cnName") String cnName);
	
	/**
	 * 查询司机信息（根据状态公司和编码）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
	 * @param zt
	 * @param gs
	 * @param bm
	 * @return
	 */
	@Select("SELECT xm,bm,grid,zt,gs FROM ygzl WHERE zt = #{zt} AND gs = #{gs} AND bm = #{bm} ")
	List<Driver> queryDriverByGsAndBm(@Param("zt") Integer zt,@Param("gs") String gs,@Param("bm") String bm);
	
	/**
	 * 查询派车提成趟次
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
	 * @return
	 */
	@Select("select TC_ID as id,TC_NANE as name from T_CAR_OUT_BASE_TC")
	List<CarOutBaseTc> queryCarOutBaseTc();
	
	/**
	 * 查询派车考核对象
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
	 * @return
	 */
	@Select("select KH_CODE as code,KH_NAME as name from T_CAR_OUT_BASE_KH")
	List<CarOutBaseKh> querCarOutBaseKh();

	/**
	 * 基础数据获取
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2018年2月3日
	 * @return
	 */
	@Select("select key_name as keyName,key_value as keyValue,name,key_value_str as keyValueStr from transport_basic_data where parent_name = #{parentname}")
	List<TransportBasicData> queryBasicInfoByParentName(String parentname);

	/**
	 * 基础数据获取
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2018年2月3日
	 * @return
	 */
	@Select("select key_name as keyName,key_value as keyValue,name,key_value_str as keyValueStr from transport_basic_data")
	List<TransportBasicData> queryAllBasicInfo();

	/**
	 * 公司目的站
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2018年2月3日
	 * @return
	 */
	@Select("SELECT id,name,bh FROM gongsi WHERE IsDaoZhan = 1")
	List<CompanyInfo> destinationStation();

	
	/**
	 * 到站网点
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2018年2月3日
	 * @return
	 */
	@Select("SELECT T_GONGSI_SHHD.shhd,GONGSI.NAME FROM T_GONGSI_SHHD,GONGSI WHERE(T_GONGSI_SHHD.GSBH=GONGSI.BH) AND  GONGSI.BH = #{companyCode}")
	List<DestinationNetPoint> destinationPointStation(String companyCode);

	
	/**
	 * 到站网点
	 * <p>
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2018年2月3日
	 * @return
	 */
	@Select("SELECT T_GONGSI_SHHD.shhd,GONGSI.NAME FROM T_GONGSI_SHHD,GONGSI WHERE(T_GONGSI_SHHD.GSBH=GONGSI.BH) AND  GONGSI.NAME = #{conpanyName}")
	List<DestinationNetPoint> destinationPointStationByConpanyName(String conpanyName);
}
