package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 收钱和冲红
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @createDate 2017-10-25 09:21:30
 */
@Mapper
public interface FinanceReceiveMoneyMapper {
	
	
	/**
	 * <b>Description</b> 获取收钱打印信息
	 * <p>
	 * <b>Author</b> <a href="mailto:109668@ycgwl.com">lihuixia</a><br>
	 * <b>Date</b> 2017-10-25 
	 * <p>
	 */
	List<FinanceReceiveMoneyPrint> findReceiveMoneyPrint(@Param("xianlu")String xianlu, @Param("cwpzhbh")Long cwpzhbh, @Param("nf")String nf, @Param("type")int type);
	
	/**
	 * <b>Description</b> 查询财凭相关运单信息
	 * <p>
	 * <b>Author</b> <a href="mailto:109668@ycgwl.com">lihuixia</a><br>
	 * <b>Date</b> 2017-10-25 
	 * <p>
	 */
	@Select("SELECT xianlu,cwpzhbh,type,printtime,HWY_YDBHID as ydbh,isnull(isreport,0) as isreport,scsj from FIWT where xianlu=#{companyCode} and cwpzhbh=#{wealthNo} and nf=#{year} and type = #{type}")
	public FinanceTransport findTransportFinance(@Param("companyCode")String companyCode, @Param("wealthNo")Long wealthNo, @Param("year")String year,@Param("type")int type);
	
	/**
	 * <b>Description</b> 查询财凭相关运单信息
	 * <p>
	 * <b>Author</b> <a href="mailto:109668@ycgwl.com">lihuixia</a><br>
	 * <b>Date</b> 2017-10-25 
	 * <p>
	 */
	@Select("select xianlu,nf,cwpzhbh,type,printtime,HWY_YDBHID as ydbh,isnull(isreport,0) as isreport,scsj from fiwt where HWY_YDBHID = #{transportCode} and type = #{type}")
	public FinanceTransport findTransportFinanceByTransportCode(@Param("transportCode")String transportCode,@Param("type")int type);

	
	@Select("select chuna, zhipiao from fkfsh where xianlu=#{companyCode} and cwpzhbh=#{wealthNo} and nf=#{year} and type = #{type}")
	public HashMap<String,String> findChunaZhipiao(@Param("companyCode")String companyCode, @Param("wealthNo")Long wealthNo, @Param("year")String year,@Param("type")int type);
	
	/**
	 * <b>Description</b> 获取货物运单信息
	 * <p>
	 * <b>Author</b> <a href="mailto:109668@ycgwl.com">lihuixia</a><br>
	 * <b>Date</b> 2017-10-27 
	 * <p>
	 */
	@Select("select fkfsh,khbm from hwyd where ydbhid=#{ydbh}")
	HashMap<String, Object> findHwydByYdbhid(@Param(value = "ydbh") String ydbh);

	@Select("select ye from t_cus_record where khbm=#{khbm}")
	Double findTCusRecordByKhbm(@Param(value = "khbm") String khbm);
	
	@Update("update fiwt set sqshj = getdate() where xianlu = #{xianlu} and cwpzhbh = #{cwpzhbh} and nf=#{nianfen} and type = 0")
	void updateFiwtReceiveMoneyTime(@Param(value = "xianlu")String xianlu,@Param(value = "cwpzhbh")Long cwpzhbh,@Param(value = "nianfen")String nianfen);
	
	/**
	 * 检查是否已回款（改写存储过程usp_check_ydbhid_chc）
	 * return >0:已回款，=0：未回款
	 */
	@Select("select count(*) from StatementOfAccountDetail where receivableamount=amountreceived and financialcertificateno = #{wealthNo} and company =#{companyCode} and gendate =#{year} AND type = #{type}")
	int checkMoneyBack(@Param("companyCode")String companyCode, @Param("wealthNo")Long wealthNo, @Param("year")String year,@Param("type")int type);
	
	
	void insertFkfshLog(FinanceReceiveMoney receiveMoney);
	
	/**
	 * <b>Description</b> 检查是否可以月结（存储过程）
	 * <p>
	 * <b>Author</b> <a href="mailto:109668@ycgwl.com">lihuixia</a><br>
	 * <b>Date</b> 2017-10-25 
	 * <p>
	 */
	void checkMonthReceive(FinanceReceiveMoneyCheckProcedure checkProcedure);
	
	@Select("select yxfhsj from t_qs where ydbhid=#{ydbhid}")
	String findTqsYxfhsj(@Param(value = "ydbhid")String ydbhid);

	@Update("update hczzqd_source set beizhu = #{ls_jishi} where ydbhid = #{ydbhid}")
	void updateHczzqdSourceBeizhu(@Param(value = "ydbhid")String ydbhid, @Param(value = "ls_jishi")String ls_jishi);
	
	@Update("update t_qs set yxfhsj=getdate() where ydbhid = #{ydbhid}")
	void updateTqsYxfhsj(@Param(value = "ydbhid")String ydbhid);
	
	/**
	 * <b>Description</b> 页面默认查询的内容
	 * <p>
	 * <b>Author</b> <a href="mailto:109668@ycgwl.com">lihuixia</a><br>
	 * <b>Date</b> 2017-10-30
	 * <p>
	 */
	FinanceReceiveMoney findReceiveMoney(@Param(value = "xianlu")String xianlu, @Param(value = "cwpzhbh")Long cwpzhbh, @Param(value = "nf")String nf);
	
	@Select("SELECT isnull(count(*),0) as li_count FROM HCZZQD_source where YDBHID=#{ls_ydbh} and (yiti=1 or yiti=2)")
	int countHczzqdYiti(@Param(value = "ls_ydbh")String ls_ydbh);

	/**
	 * <b>Description</b>更新收钱月结等信息
	 * <p>
	 * <b>Author</b> <a href="mailto:109668@ycgwl.com">lihuixia</a><br>
	 * <b>Date</b> 2017-11-2
	 * <p>
	 */
	void updateFkfsh(FinanceReceiveMoney receive);

	/**
	 * <b>Description</b>查询提货签收表
	 * <p>
	 * <b>Author</b> <a href="mailto:109668@ycgwl.com">lihuixia</a><br>
	 * <b>Date</b> 2017-12-06
	 * <p>
	 */
	@Select("SELECT TOP 1 thqshdid,shjhm FROM thqsd WHERE ydbhid=#{ydbhid} and thqsdxz=#{thqsdxz}")
	Map<String, Integer> findThqsd(@Param(value = "ydbhid")String ydbhid, @Param(value = "thqsdxz")int thqsdxz);

	@Select("SELECT TOP 1 id, shjhm FROM t_car_out WHERE ydbhid=#{ydbhid}")
	Map<String, Integer> findTcarOut(String ydbhid);
	
	/**
	 * <b>Description</b>查询冲红
	 * <p>
	 * <b>Author</b> <a href="mailto:109668@ycgwl.com">lihuixia</a><br>
	 * <b>Date</b> 2017-12-07
	 * <p>
	 * @return 
	 */
	Double searchOffsetWealthInfo(FinanceReceiveMoneyCheckProcedure produre);

	/**
	 * <b>Description</b>调用冲红的存储过程进行冲红
	 * <p>
	 * <b>Author</b> <a href="mailto:109668@ycgwl.com">lihuixia</a><br>
	 * <b>Date</b> 2017-12-07
	 * <p>
	 * @return 
	 */
	Long offsetWealthInfo(FinanceChonghongProcedure produre);

	@Update("update T_fenlikucun set beizhu = #{description} where ydbhid = #{transportCode}")
	void updateFenliKucunBeizhu(@Param(value = "transportCode")String transportCode, @Param(value = "description")String description);

	/**
	 * 记录打印次数和最后打印时间 
	 * @param waybillNum
	 * @return
	 */
	int updatePrintCountAndDate(String waybillNum);
		
}
