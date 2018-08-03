package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.entity.Page;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.transport.entity.BillOfdeliveryEntity;
import com.ycgwl.kylin.transport.entity.BillOfdeliveryRequestEntity;
import com.ycgwl.kylin.transport.entity.DeliveryOperateEntity;
import com.ycgwl.kylin.transport.entity.TransportOrderDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DeliveryOperateMapper {

	@Select("SELECT	TOP 1 thqshdid FROM	THQSD WHERE	YDBHID = #{ydbhid} AND YDXZH = #{ydxzh}")
	Integer getthqshdidByYdbhidAndYdxzh(@Param("ydbhid")String ydbhid,@Param("ydxzh")String ydxzh);
	
	@Select("SELECT	TOP 1 thqshdid FROM	THQSD WHERE	YDBHID = #{ydbhid}")
	Integer top1thqshdid(String ydbhid);
	
	
	@Select("SELECT	TOP 1 id FROM T_CAR_OUT_DETAIL1 WHERE YDBHID =#{ydbhid} AND YDXZ =#{ydxzh} ")
	Integer getCarOutDetail1IdByYdbhidAndYdxzh(@Param("ydbhid")String ydbhid,@Param("ydxzh")String ydxzh);
	
	@Select("SELECT	TOP 1 id FROM T_CAR_OUT_DETAIL1 WHERE YDBHID =#{ydbhid} ")
	Integer top1tCarOut(String ydbhid);
	
	@Select("select ddfhzt,tzfhzt from HWYD_DDFH where ydbhid = #{ydbhid}")
	HashMap<String,Short> getdispatchAdvice(String ydbhid);

	@Select("SELECT	isnull(COUNT(*), 0) FROM T_FHTZD_MASTER" + 
			"	INNER JOIN T_FHTZD_DETAIL ON T_FHTZD_MASTER.id = T_FHTZD_DETAIL.masterid" + 
			"	WHERE T_FHTZD_MASTER.ispass = 1	AND T_FHTZD_DETAIL.ydbhid =#{ydbhid}")
	Integer getUnpaidByYdbhid(String ydbhid);
	
	void excuteCheckfwfsandcar(Map<String,Object> map);

	/**货物收据ID*/
	@Select("select id  from T_CWSJ_MASTER where Fiwt_ydbhid = #{ydbhid} and type =0")
	Integer getT_CWSJ_MASTERId(String ydbhid);

	@Select("Select yxfhsj  From t_qs Where ydbhid = #{ydbhid}")
	Date getyxfhsjfromt_qs(String ydbhid);
	
	@Select("select hdfk ,dsk from V_SHQSD where HWY_YDBHID = #{ydbhid} ")
	Map<String,BigDecimal> getHdfkAndDskByYdbhid(String ydbhid);

	/**更新装载表的已提字段*/
	@Update("update hczzqd_source set yiti=#{yiti} where xuhao=#{xuhao}")
	void modifyHczzqdSourceYitiByXuhao(@Param("xuhao")String xuhao,@Param("yiti")Integer yiti);
	
	/**更新签收状态*/
	@Update("update t_qs set cx_flag=#{flag} where  ydbhid=#{ydbhid}")
	void modifyT_QSFlag(@Param("ydbhid")String ydbhid,@Param("flag")String flag);

	/**删除在途跟踪*/
	@Update("delete HWYD_ROUTE where ydbhid = #{ydbhid} and cztype='已撤销签收'")
	void deleteHWYD_ROUTEByYdbhid(String ydbhid);

	/**更新分离库存的已提状态*/
	void modifyT_fenlikucun(RequestJsonEntity entity);

	/**提货单保存*/
	void insertIntoTHQSD(RequestJsonEntity entity);

	/**获取最大的提货单id并加一,作为下个提货单的id*/
	@Select("select isnull(max(thqshdid),0)+1 from thqsd where gs= #{company}")
	Integer getMaxThqsdByCompany(String company);

	@Select("select top 1 cangwei from t_fenlikucun where ydbhid = #{ydbhid}  and gs=#{company} and daozhan= #{company}")
	String getCangweiByYdbhid(@Param("ydbhid")String ydbhid, @Param("company")String company);
	
	@Select("select isnull(max(id),0) +1  from T_CAR_OUT where gs=#{company}")
	Integer getMaxT_CAR_OUTByCompany(String company);

	void insertIntoT_CAR_OUT(RequestJsonEntity entity);

	void insertIntoT_CAR_OUT_Detail2(RequestJsonEntity entity);

	void insertIntoT_CAR_OUT_DETAIL1(RequestJsonEntity entity);
	
	

	/**
	 * 通过派车单号和公司编号查询派车单数量
	 * @param dispatchCarNo companyCode
	 * @return
	 */
	@Select("select count(*) from T_CAR_IN WHERE id=#{dispatchCarNo} and gsid=#{companyCode}")
	int countTcarinById(Map<String,Object> map);
	

	/**
	 * 查询到货城市信息
	 * @param hwdaozhan dhShengfen dhChengsi
	 * @return is_daofukuan is_fandan is_daisoukuan
	 */
	@Select("SELECT is_daofukuan,is_fandan,is_daisoukuan FROM T_dhuo_chengsi where gongsi = #{hwdaozhan} and dh_shengfen = #{dhShengfen} and dh_chengsi = #{dhChengsi}")
	Map<String, Object> findTDhuoChengsi(@Param(value="hwdaozhan")String hwdaozhan,@Param(value="dhShengfen")String dhShengfen,@Param(value="dhChengsi")String dhChengsi);

	@Select("select isnull(max(id),0) +1 as idnew from t_car_in where gsid=#{gsid}")
	int findTcarIn(Object gsid);
	
	/** 从V_SHQSD视图中获取数据拼备注字段 */
	@Select("Select xianlu,cwpzhbh,hjje,dzfk,fzfk,hdfk,xianjin,yhshr,yshzhk,yshk,dsk  From V_SHQSD Where hwy_ydbhid = #{ydbhid}")
	Map<String, Object> getSomethingFromV_SHQSDByYdbhid(String ydbhid);

	Page<BillOfdeliveryEntity> pagebillOfdeliverymanage(BillOfdeliveryRequestEntity entity, RowBounds rowBounds);

	void deleteArriveLoadingHWYD_ROUTEByYdbhid(@Param("ydbhid")String ydbhid,@Param("gs") String gs);

	void modifyT_fenlikucunByTransportOrderDetail(@Param("detail")TransportOrderDetail detail,@Param("gs") String gs);
	/** 撤销时,修改装载表的已提字段*/
	@Update("update hczzqd_source set yiti=#{yiti} where ydbhid=#{ydbhid} and daozhan = #{gs}")
	void modifyHczzqdSourceYitiByYdbhidAndGs(@Param("ydbhid")String ydbhid,
			@Param("gs")String gs, @Param("yiti")Integer yiti);

	/**撤销时,修改运单的库存状态 */
	@Update("update HWYD set yxzt='到站库存' where ydbhid=#{ydbhid}")
	void modifyHwydYxzt(String ydbhid);

	/**复原签收表*/
	@Update("update t_qs set lrr=null,lrrgrid=null,lrtime=null,gs=null,qszt=0,qsr=null,qstime=null,shlc=null,comm=null,cx_flag='1' " + 
			" where ydbhid=#{ydbhid} ")
	void modifyT_QSInit(String ydbhid);

	@Delete("delete from THQSD where gs=#{gs} and YDBHID=#{ydbhid}")
	void deleteTHQSDByGsAndYdbhid(@Param("ydbhid") String ydbhid,@Param("gs") String gs);

	/**查询送货单*/
	List<DeliveryOperateEntity> pagedeliverydocumentsmanage(RequestJsonEntity entity);
	/**查询送货单-->司机*/
	List<HashMap<String,Object>> pagedeliverydocumentsmanageForDriver(RequestJsonEntity entity);
	
	@Select("SELECT	YSFS FROM HWYD WHERE YDBHID = #{ydbhid}")
	String getYsfsByydbhid(String ydbhid);
	
	@Delete("delete from THQSD where ydbhid = #{ydbhid} and gs = #{gs}")
	void deleteTHQSDByYdbhidAndGs(@Param("ydbhid")String ydbhid, @Param("gs")String gs);
	
	/**查询是否有送货的记录*/
	@Select("select top 1 ydbhid from T_CAR_OUT where id = #{id} and gsid = #{gsid}")
	String getYDBHIDFromT_CAR_OUTByIdAndGsid(@Param("id")String id, @Param("gsid")String gsid);

	@Delete("delete from t_car_out where id = #{id} and gsid = #{gsid}")
	void deleteT_CAR_OUTByIdAndGsid(@Param("id")String id, @Param("gsid")String gsid);

	@Delete("delete from T_CAR_OUT_DETAIL1 where id = #{id} and gsid = #{gsid}")
	void deleteT_CAR_OUTDetail1ByIdAndGsid(@Param("id")String id, @Param("gsid")String gsid);
	@Delete("delete from T_CAR_OUT_DETAIL2 where id = #{id} and gsid = #{gsid}")
	void deleteT_CAR_OUTDetail2ByIdAndGsid(@Param("id")String id, @Param("gsid")String gsid);
	@Delete("delete from T_CAR_OUT_DETAIL3 where id = #{id} and gsid = #{gsid}")
	void deleteT_CAR_OUTDetail3ByIdAndGsid(@Param("id")String id, @Param("gsid")String gsid);
	@Delete("delete from T_CAR_OUT_DETAIL4 where id = #{id} and gsid = #{gsid}")
	void deleteT_CAR_OUTDetail4ByIdAndGsid(@Param("id")String id, @Param("gsid")String gsid);
	@Delete("delete from T_CAR_OUT_DETAIL5 where id = #{id} and gsid = #{gsid}")
	void deleteT_CAR_OUTDetail5ByIdAndGsid(@Param("id")String id, @Param("gsid")String gsid);

	@Select("SELECT top 1  T_COST as cost from T_INCOME_COST_H where INS_NO = (SELECT TOP 1 INS_NO from T_INCOME_COST_D where xuhao = #{xuhao})")
	BigDecimal getTCostFromTIncomeDByXuhao(String xuhao);

	List<HashMap<String,Object>> searchBillOfdelivery(RequestJsonEntity entity);

	@Select("SELECT cx_flag FROM T_QS where ydbhid = #{ydbhid}")
	Integer getCxFlagFromT_QSByYdbhid(String ydbhid);

	@Update("update t_qs set old_qstime = #{oldtime} where ydbhid = #{ydbhid}")
	void updateT_QSoldTime(@Param("ydbhid")String ydbhid,@Param("oldtime") Date oldtime) ;
	
	
	void modifyTHQSD(RequestJsonEntity entity);

	@Select("select top 1 id from T_CWSJ_MASTER where Fiwt_ydbhid = #{ydbhid}")
	Integer getT_CWSJ_MASTERIdByydbhid(String ydbhid);

	
}
