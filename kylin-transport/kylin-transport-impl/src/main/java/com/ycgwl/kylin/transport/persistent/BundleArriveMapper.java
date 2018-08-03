package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BundleArriveMapper {

	/**获取最大的提货单id并加一,作为下个提货单的id*/
	@Select("select isnull(max(thqshdid),0)+1 from thqsd where gs= #{company}")
	Integer getMaxThqsdByCompany(String company);
	
	/**
	 * 获取到货装载查询页面数据
	 * @param entity
	 * @return
	 */
	public List<BundleArriveEntity> findBundleArriveList(Map<String,Object> map);

	/**
	 * 获取网点信息
	 * @param gsbh,wdname
	 * @return thdmc,address,tel
	 */
	@Select("select name,address,tel FROM thd_info WHERE item IN (SELECT TOP 1 address_item from wdinfo "
			+ "WHERE gsbh = #{companyCode} and wdname = #{wdname})") 
	Map<String, Object> findNetworkPointInfo(Map<String, Object> param);

	/**
	 * 检查是否有财凭
	 * @param transportCode
	 * @return xianlu,cwpzhbh,nf,type
	 */
	@Select("select xianlu,cwpzhbh,nf,type from fiwt where hwy_ydbhid=#{transportCode} and type=0")
	Map<String, Object> hasFiwt(Map<String, Object> param);
	
	/**
	 * 检查是否收钱
	 * @param xianlu,cwpzhbh,nf
	 * @return yshzhk_b
	 */
	@Select("select yshzhk_b From fkfsh Where xianlu = #{xianlu} And cwpzhbh = #{cwpzhbh} and nf=#{nf} And type = 0")
	Map<String, Object> hasReceiveMoney(Map<String, Object> param);

	/**
	 * 检查等托运人指令放货
	 * @param transportCode
	 * @return tzfhzt
	 */
	@Select("select tzfhzt from hwyd_ddfh where ydbhid=#{transportCode}")
	Map<String, Object> checkWaitOrderStatus(Map<String, Object> param);
	
	/**
	 * 通过派车单号和公司编号查询派车信息
	 * @param dispatchCarNo companyCode
	 * @return
	 */
	DispatchCarPickGoods findTcarinEntity(@Param(value="gsid")String gsid,@Param(value="pcid")Integer pcid);
	
	/**
	 * 通过派车单号和公司编号查询派车信息细则
	 * @param dispatchCarNo companyCode
	 * @return
	 */
	List<DispatchCarPickGoodsDetail> findTcarinGoodsList(@Param(value="gsid")String gsid,@Param(value="pcid")Integer pcid);
	
	/**
	 * 通过派车单号和公司编号查询派车信息细则2
	 * @param dispatchCarNo companyCode
	 * @return
	 */
	@Select("SELECT gsid,id,detailid,clxz,ch,cx,sj FROM T_CAR_IN_DETAIL2 where gsid=#{gsid} and id=#{pcid}")
	List<DispatchCarPickGoodsDetailSecond> findTCarInDetail2(@Param(value="gsid")String gsid,@Param(value="pcid")Integer pcid);

	/**
	 * 查询到货城市信息
	 * @param hwdaozhan dhShengfen dhChengsi
	 * @return is_daofukuan is_fandan is_daisoukuan
	 */
	@Select("SELECT is_daofukuan,is_fandan,is_daisoukuan FROM T_dhuo_chengsi where gongsi = #{hwdaozhan} and dh_shengfen = #{dhShengfen} and dh_chengsi = #{dhChengsi}")
	Map<String, Object> findTDhuoChengsi(@Param(value="hwdaozhan")String hwdaozhan,@Param(value="dhShengfen")String dhShengfen,@Param(value="dhChengsi")String dhChengsi);

	@Select("select isnull(max(id),0) +1 as idnew from t_car_in where gsid=#{gsid}")
	int findNewIdForTcarIn(Object gsid);

	/**
	 * 保存运单信息
	 */
	void saveTCarInEntity(DispatchCarPickGoods goods);

	/**
	 * 保存货物运单明细
	 */
	void saveGoodsDetail(DispatchCarPickGoodsDetail dispatchCarPickGoodsDetail);

	/**
	 * 修改货物运单明细
	 */
	void updateGoodsDetail(DispatchCarPickGoodsDetail detail);

	/**
	 * 更新运单信息
	 */
	void updateTCarInEntity(DispatchCarPickGoods transportInfo);

	void updateTCarIn(DispatchCarPickGoods transportInfo);

	/**
	 * 查询取货派车信息
	 * @param gsid
	 * @param pcyes
	 * @param xdtimeStart
	 * @param xdtimeEnd
	 * @return
	 */
	List<DispatchCarPickGoods> searchDeliveryCarHandling(@Param(value="gsid")String gsid,@Param(value="pcyes")Integer pcyes,@Param(value="xdtimeStart")String xdtimeStart,@Param(value="xdtimeEnd")String xdtimeEnd,@Param(value="id")Integer id);
	
	@Select("SELECT * from T_CAR_IN_DETAIL3 where gsid=#{gsid} and id=#{pcid}")
	List<DispatchCarPickGoodsDetailThree> queryTCarDetailThree(@Param(value="gsid")String gsid,@Param(value="pcid")Integer pcid);

	@Select("SELECT * FROM T_CAR_IN_DETAIL5 where gsid=#{companyCode} and id=#{pcid}")
	List<DispatchCarPickGoodsDetailFive> queryTCarDetailFive(@Param(value="companyCode")String companyCode, @Param(value="pcid")Integer pcid);
	
	@Insert("INSERT INTO T_CAR_IN_DETAIL3 ( gsid, id, ch, ddlmbs, tzlqf, tzfk, tzwxf, tzlmbs, sj ,cftime ,ddtime ,cflmbs ,tzjyss ,tzjyje) VALUES ( #{gsid}, #{id}, #{ch}, #{ddlmbs}, #{tzlqf}, #{tzfk}, #{tzwxf}, #{tzlmbs}, #{sj} ,#{cftime} ,#{ddtime} ,#{cflmbs} ,#{tzjyss} ,#{tzjyje})")
	void saveGoodsDetailThree(DispatchCarPickGoodsDetailThree dispatchCarPickGoodsDetailThree);

	@Insert("INSERT INTO T_CAR_IN_DETAIL5 ( gsid, id, clxz, ch, yfbz, yfjs, tc ,qsdfhtime ) VALUES (#{gsid},#{id},#{clxz}, #{ch}, #{yfbz}, #{yfjs}, #{tc} ,#{qsdfhtime})")
	void saveGoodsDetailFive(DispatchCarPickGoodsDetailFive dispatchCarPickGoodsDetailFive);

	void updateTCarInAboutAccount(DispatchCarPickGoods tcarPick);

	@Delete("delete from T_CAR_IN_DETAIL3 where id = #{pcid} and gsid = #{gsid}")
	void deleteGoodsDetailThree(@Param(value="pcid")Integer pcid, @Param(value="gsid")String gsid);
	
	@Delete("delete from T_CAR_IN_DETAIL5 where id = #{pcid} and gsid = #{gsid}")
	void deleteGoodsDetailFive(@Param(value="pcid")Integer pcid, @Param(value="gsid")String gsid);

	@Delete("delete from T_CAR_IN_DETAIL2 where id = #{pcid} and gsid = #{gsid}")
	void deleteGoodsDetailTwo(@Param(value="gsid")String gsid, @Param(value="pcid")Integer pcid);

	@Insert("INSERT INTO T_CAR_IN_DETAIL2 ( gsid, id, clxz, ch, cx, sj ) VALUES ( #{gsid}, #{id}, #{clxz}, #{ch}, #{cx}, #{sj} )")
	void saveDispatchDetailTwo(DispatchCarPickGoodsDetailSecond dispatch);

	@Update("UPDATE T_CAR_IN_DETAIL2 SET gsid = #{gsid}, id = #{id}, clxz = #{clxz},cx=#{cx},sj=#{sj} WHERE gsid = #{gsid} AND id = #{id} AND detailid = #{detailid}")
	void updateDispatchDetailTwo(DispatchCarPickGoodsDetailSecond dispatch);

	List<BundleArriveEntity> findBundleArriveListByTransportCode(Map<String, Object> map);
	
	@Select("select tzfhzt,ydbhid from hwyd_ddfh where ydbhid in (${transportCodes})")
	List<BundleArriveEntity> findTzfhztByTransportCodes(@Param(value="transportCodes")String transportCodes);

	List<HashMap<String,Object>> getT_Car_InByIdAndGsId(@Param("id")String id,@Param("gsid") String gsid);
	
}
