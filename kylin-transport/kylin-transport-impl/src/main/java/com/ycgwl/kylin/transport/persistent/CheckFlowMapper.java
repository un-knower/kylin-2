package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.BundleCostDetail;
import com.ycgwl.kylin.transport.entity.BundleCostTopic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface CheckFlowMapper {
	
	
	
	
	
	@Select("select sum(d_cost) from T_INCOME_COST_D where ydbhid in (${transportCodes})")
	public Double getIncomeCostDByTransportCodes(@Param(value="transportCodes") String transportCodes);

	@Select("select xuhao from HCZZQD_SOURCE where ydbhid in (${transportCodes})")
	public List<String> getBundleXuhaoByTransportCodes(@Param(value="transportCodes") String transportCodes);
	
	//根据运单编号查询运输号码
	@Select("select yshm,ydbhid from T_INCOME_COST_D where ydbhid in (${transportCodes})")
    public List<String> getBundleYsHMByTransportCodes(@Param(value="transportCodes") String transportCodes);
	
	
	//根据运单号和件数查询成本
	@Select("SELECT"
			 + "v_income_cost_d.INS_NO ,"
			 + "v_income_cost_d.YDBHID,"
			 + "v_income_cost_d.YDXZH,"
			 + "v_income_cost_d.YDBHID ,"
			 + "isnull(v_income_cost_d.D_INCOME,0) as D_INCOME ,"
             + "isnull(v_income_cost_d.D_COST,0) as D_COST ,"
             + "isnull(v_income_cost_d.T_COST_SALE,0) as T_COST_SALE," 
             + "isnull(v_income_cost_d.T_COST_ELSE,0) as T_COST_ELSE," 
             + "isnull(v_income_cost_d.hdhkje,0) as hdhkje"
             +"FROM v_income_cost_d"
             +"where v_income_cost_d.YDBHID ="+" (${ydbhId})"
             +"and v_income_cost_d.YDXZH =" +"${number}" )
	public BundleCostDetail getBundleNumberByYdBhId(@Param(value="ydbhId") String ydbhId, @Param("number") int number);
    
	//根据运单号查询明细成本
	@Select("select isnull(D_COST,0) as D_COST , isnull(T_COST_SALE,0) as T_COST_SALE,isnull(T_COST_ELSE,0) as T_COST_ELSE from T_INCOME_COST_D where YDBHID=(${OrderId})")
	public BundleCostDetail getD_CostByOrderId(@Param(value="OrderId") String OrderId);
	
	//根据运单号查询表头成本
	@Select("SELECT isnull(D_COST,0) as D_COST , isnull(T_COST_SALE,0) as T_COST_SALE,isnull(T_COST_ELSE,0) as T_COST_ELSE from T_INCOME_COST_H where YSHM=(${OrderId})")
	public BundleCostTopic getH_CostByOrderId(@Param(value="OrderId") String OrderId);
	
	public BundleCostTopic getoldCostByOrderId(@Param(value="OrderId") String OrderId);

	
	
	
	
	//修改发货时间
	@Update("UPDATE hwyd SET FHSHJ=#{fhshjStr} where ydbhid = #{ydbhid}")
	public void updateFhshjDate(@Param(value="ydbhid") String ydbhid, @Param(value="fhshjStr") String fhshjStr); 
	
	//修改生成财凭时间和收钱时间
	@Update("UPDATE fiwt SET scsj=#{createFinanceStr},sqshj=#{receiveMoneyStr} where hwy_ydbhid = #{ydbhid}")
	public void updateFiwtDate(@Param(value="ydbhid")String ydbhid, @Param(value="createFinanceStr") String createFinanceStr, @Param(value="receiveMoneyStr")String receiveMoneyStr);

	//修改装载时间和到货时间
	@Update("UPDATE HCZZQD_source SET FCHRQ=#{fchrq},ZHCHRQ=#{fchrq},lrsj=#{fchrq},dhsj=#{arriveStr} where ydbhid = #{ydbhid}")
	public void updateHczzqdDate(@Param(value="ydbhid")String ydbhid, @Param(value="arriveStr")String arriveStr,@Param(value="fchrq")String fchrq);

	//修改到货时间
	@Update("UPDATE T_fenlikucun set dhsj = #{arriveStr},zhchrq=#{fchrq} where ydbhid = #{ydbhid}")
	public void updateArriveDate(@Param(value="ydbhid")String ydbhid, @Param(value="arriveStr")String arriveStr,@Param(value="fchrq")String fchrq); 
	
	//修改签收时间
	@Update("UPDATE t_qs SET qstime=#{signTime},lrtime=#{signTime},yxfhsj=#{signTime},old_qstime=#{signTime},record_qstime=#{signTime} where ydbhid = #{ydbhid}")
	public void updateSignDate(@Param(value="ydbhid")String ydbhid, @Param(value="signTime")String signTime);

	//修改统计表时间
	@Update("UPDATE T_HWYD_STATISTICS SET fhshj=#{fhshj},FCHRQ=#{loadtime},qstime=#{qstime} where ydbhid = #{ydbhid}")
	public void updateStatisDate(@Param(value="ydbhid")String ydbhid, @Param(value="fhshj")String fhshj,@Param(value="loadtime")String loadtime,@Param(value="qstime")String qstime);

	//修改在途跟踪签收时间
	@Update("UPDATE HWYD_ROUTE SET shijian=#{signStr},beizhu=#{signStr} WHERE ydbhid=#{ydbhid} AND cztype='签收成功' ")
	public void updateHwydRouteSignTime(@Param(value="ydbhid")String ydbhid,@Param(value="signStr")String signStr);
	
	//修改在途跟踪入库时间
	@Update("UPDATE HWYD_ROUTE SET shijian=#{fhshj} WHERE ydbhid=#{ydbhid} AND cztype='货物入库'")
	public void updateHwydRouteFhshjTime(@Param(value="ydbhid")String ydbhid,@Param(value="fhshj")String fhshj);
	
	//修改在途跟踪运输途中时间
	@Update("UPDATE HWYD_ROUTE SET shijian=#{loadtime} WHERE ydbhid=#{ydbhid} AND cztype='运输途中'")
	public void updateHwydRouteLoadTime(@Param(value="ydbhid")String ydbhid,@Param(value="loadtime")String loadtime);
	
	//修改在途跟踪到站时间
	@Update("UPDATE HWYD_ROUTE SET shijian=#{arrivetime} WHERE ydbhid=#{ydbhid} AND cztype='到站库存'")
	public void updateHwydRouteArriveTime(@Param(value="ydbhid")String ydbhid,@Param(value="arrivetime")String arrivetime);
	
	//修改出纳日报表时间
	@Update("UPDATE chnrbb_new SET bzsj=#{receiveMoneyStr},sqshj=#{receiveMoneyStr} WHERE ydbhid=#{ydbhid}")
	public void updateChnrbbNewTime(@Param(value="ydbhid")String ydbhid,@Param(value="receiveMoneyStr")String receiveMoneyStr);

	//修改在途跟踪提货时间
	@Update("UPDATE HWYD_ROUTE SET shijian=#{signStr} WHERE ydbhid=#{ydbhid} AND cztype='提货'")
	public void updateHwydRouteTihuoTime(@Param(value="ydbhid")String ydbhid,@Param(value="signStr") String signStr);

	/**
	 * 修改客户信息
	 */
	@Update("update HCZZQD_source set fhdwmch = #{customerName} where ydbhid = #{ydbhid}")
	public void updateHczzqdSourceFhdwmch(@Param(value="ydbhid")String ydbhid, @Param(value="customerName")String customerName);

	/**
	 * 更新运单检查日期
	 */
	@Update("update T_HWYD_CHECK_DAY set FHDWMCH=#{customerName} where ydbhid = #{ydbhid}")
	public void updateHwydCheckDay(@Param(value="ydbhid")String ydbhid, @Param(value="customerName")String customerName);
	
	/**
	 * 更新统计表
	 */
	@Update("update T_HWYD_STATISTICS set FHDWMCH = #{customerName} where ydbhid = #{ydbhid}")
	public void updateStatis(@Param(value="ydbhid")String ydbhid, @Param(value="customerName")String customerName);
	
	/**
	 * 更新统计表new
	 */
	@Update("update T_HWYD_STATISTICS_new set FHDWMCH = #{customerName} where ydbhid = #{ydbhid}")
	public void updateStatisNew(@Param(value="ydbhid")String ydbhid, @Param(value="customerName")String customerName);

	@Update("update HWYD set FHDWMCH  = #{customerName},khbm=#{customerCode} where ydbhid = #{ydbhid}")
	public void updateHwydCustomerName(@Param(value="ydbhid")String ydbhid, @Param(value="customerName")String customerName, @Param(value="customerCode")String customerCode);

	@Update("update t_fljsl set fhsj = #{fhshjStr},qslrsj = #{signStr} ,qssj =#{signStr}  where ydbhid=#{ydbhid}")
	public void updateTfljsl(@Param(value="ydbhid")String ydbhid, @Param(value="fhshjStr")String fhshjStr, @Param(value="signStr")String signStr);

	
	/**
	 * 修改公司名称.
	 */
	@Update("update hczzqd_beizhu set fazhan = #{newCompanyName} where fazhan = #{oldCompanyName}")
	public void updateHczzqdBeizhuFazhan(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update HCZZQD_source set company = #{newCompanyName} where company = #{oldCompanyName}")
	public void updateHczzqdCompany(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update HCZZQD_source set fazhan = #{newCompanyName} where fazhan = #{oldCompanyName}")
	public void updateHczzqdFazhan(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update hczzqd_beizhu set daozhan = #{newCompanyName} where daozhan = #{oldCompanyName}")
	public void updateHczzqdDaozhan(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_fenlikucun set daozhan = #{newCompanyName} where daozhan = #{oldCompanyName}")
	public void updateTFenlikucunDaozhan(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_fenlikucun set fazhan = #{newCompanyName} where fazhan= #{oldCompanyName}")
	public void updateTfenlikucunFazhan(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_fenlikucun set GS = #{newCompanyName} where GS= #{oldCompanyName}")
	public void updateTfenlikucunGs(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_fenlikucun set MuDiZhan = #{newCompanyName} where MuDiZhan= #{oldCompanyName}")
	public void updateTfenlikucunMudizhan(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_HWYD_CHECK_DAY set fazhan = #{newCompanyName} where fazhan= #{oldCompanyName}")
	public void updatetThwydCheckDay(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_HWYD_STATISTICS set fazhan = #{newCompanyName} where fazhan= #{oldCompanyName}")
	public void updatetTHwydStatictics(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_HWYD_STATISTICS_new set fazhan = #{newCompanyName} where fazhan= #{oldCompanyName}")
	public void updatetTHwydStaticticsNew(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update t_fljsl set gs = #{newCompanyName} where gs= #{oldCompanyName}")
	public void updatetTfljsl(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_HWYDSX set daozhan = #{newCompanyName} where daozhan= #{oldCompanyName}")
	public void updatetTHWYDSXDaozhan(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_HWYDSX set fazhan = #{newCompanyName} where fazhan= #{oldCompanyName}")
	public void updateTINCOME_COST_D(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_HWYDSX set fazhan = #{newCompanyName} where fazhan= #{oldCompanyName}")
	public void updatetTHWYDSXFazhan(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_INCOME_COST_D set FAZHAN = #{newCompanyName} where FAZHAN= #{oldCompanyName}")
	public void updatetTIncomeCostD(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_INCOME_COST_H set CREATE_GS = #{newCompanyName} where CREATE_GS= #{oldCompanyName}")
	public void updatetTIncomeCostH(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_INCOME_COST_H set FAZHAN = #{newCompanyName} where FAZHAN= #{oldCompanyName}")
	public void updatetTIncomeCostHFazhan(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update t_kpihz set gs = #{newCompanyName} where gs= #{oldCompanyName}")
	public void updateTkpihz(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_LOGON_LOG set gs = #{newCompanyName} where gs= #{oldCompanyName}")
	public void updateTLoginLog(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_QianShouKH set FAZHAN = #{newCompanyName} where FAZHAN= #{oldCompanyName}")
	public void updateTqianShouKHFAZHAN(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_QianShouKH set ZCDAOZHAN = #{newCompanyName} where ZCDAOZHAN= #{oldCompanyName}")
	public void updateTqianShouKHZCDAOZHAN(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_QS set gs = #{newCompanyName} where gs= #{oldCompanyName}")
	public void updateT_QS(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_QS_log set gs = #{newCompanyName} where gs= #{oldCompanyName}")
	public void updateT_QS_log(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_SendEdi_CUSTOMER set gs = #{newCompanyName} where gs= #{oldCompanyName}")
	public void updateT_SendEdi_CUSTOMER(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_USER_GS set gs = #{newCompanyName} where gs= #{oldCompanyName}")
	public void updateT_USER_GS(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update chnrbb_new set gs = #{newCompanyName} where gs= #{oldCompanyName}")
	public void updateChnrbb_new(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update CUSTOMER set gs = #{newCompanyName} where gs= #{oldCompanyName}")
	public void updateCUSTOMER(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update CUSTOMER_Ysk_Log set gs = #{newCompanyName} where gs= #{oldCompanyName}")
	public void updateCUSTOMERYskLog(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update exception_log set operator_company = #{newCompanyName} where operator_company= #{oldCompanyName}")
	public void updateException_log(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update T_Customer_more_industry set CUSTGS = #{newCompanyName} where CUSTGS= #{oldCompanyName}")
	public void updateTCustomerMoreIndustry(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update HWYD_ROUTE set gs = #{newCompanyName} where gs= #{oldCompanyName}")
	public void updateHwydRoute(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update HWYD_DDFH set fazhan = #{newCompanyName} where fazhan= #{oldCompanyName}")
	public void updateHwydDDFH(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update HWYD set DAOZHAN = #{newCompanyName} where DAOZHAN= #{oldCompanyName}")
	public void updateHwydDaozhan(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update HWYD set FAZHAN = #{newCompanyName} where FAZHAN= #{oldCompanyName}")
	public void updateHwydFazhan(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update HWYD_ROUTE set beizhu = #{newCompanyName} where beizhu= #{oldCompanyName}")
	public void updateHwydRouteBeizhu(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);
	
	@Update("update route_infor set lrgs = #{newCompanyName} where lrgs= #{oldCompanyName}")
	public void updateRouteInfor(@Param(value="oldCompanyName")String oldCompanyName, @Param(value="newCompanyName")String newCompanyName);

	@Select("select yshhm from hwyd where ydbhid = #{ydbhid}")
	public String findHwydYshhm(String ydbhid);
	
	@Update("update hwyd set yshhm =#{yshhm} where ydbhid = #{ydbhid}")
	public void updateHwydYshhm(@Param(value="ydbhid")String ydbhid,@Param(value="yshhm")String yshhm);

	@Update("update T_HWYD_CHECK_DAY set fhshj =#{fhshjStr},qstime=#{signStr} where ydbhid = #{ydbhid}")
	public void updateTHwydCheckDAY1(@Param(value="ydbhid")String ydbhid, @Param(value="fhshjStr")String fhshjStr, @Param(value="signStr")String signStr);
	
	@Update("update T_HWYD_CHECK_DAY set fdqstime=#{fdSignStr},fchrq=#{loadStr} where ydbhid = #{ydbhid}")
	public void updateTHwydCheckDAY2(@Param(value="ydbhid")String ydbhid, 
			@Param(value="loadStr")String loadStr, @Param(value="fdSignStr")String fdSignStr);

	/**
	 * 25号更新的数据
	 */
	@Select("select ydbhid,convert(varchar(20),writeordertime,120) as writeordertime,convert(varchar(20),loadtime,120) as loadtime,convert(varchar(20),arrivetime,120) as arrivetime,convert(varchar(20),financialcertificatetime,120) as financialcertificatetime,convert(varchar(20),collectmoneytime,120) as collectmoneytime,convert(varchar(20),signtime,120) as signtime from t_change_time_20180625")
	public List<Map<String, String>> findTchangeTime20180625();

	@Update("update HWYD set FHDWMCH  = #{customerName} where ydbhid = #{ydbhid}")
	public void updateHwydCustomerName2(@Param(value="ydbhid")String ydbhid, @Param(value="customerName")String customerName);
	
	//update gongsi set isjsgsname = #{newCompanyName} where isjsgsname= #{oldCompanyName}
	//update T_USERRIGHT set gs = #{newCompanyName} where gs= #{oldCompanyName}
	//update T_YGKHJL set gs = #{newCompanyName} where gs= #{oldCompanyName}
	//SELECT * FROM xianlu where gs = '佰之源'
	//update XIANLU set GS = #{newCompanyName} where GS= #{oldCompanyName}	
	//update ygzl set gs = #{newCompanyName} where gs= #{oldCompanyName}
	//update gongsi set name = #{newCompanyName} where name= #{oldCompanyName}
	
}
