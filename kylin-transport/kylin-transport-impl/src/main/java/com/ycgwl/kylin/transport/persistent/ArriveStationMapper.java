package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.BundleReceipt;
import com.ycgwl.kylin.transport.entity.FenliKucunEntity;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *到货分理库存底层
 * <p>
 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2018-04-07 16:59:00
 */
@Mapper
public interface ArriveStationMapper {
	/**
	 * 通过运单号、公司名称、细则号统计分理库存数量
	 */
	@Select("SELECT count(*) FROM T_fenlikucun where YDBHID=#{ydbhid} AND gs = #{gs} AND ydxzh = #{ydxzh}")
	Integer countFenliKucunBySerialCondition(@Param("ydbhid")String ydbhid, @Param("gs")String gs,@Param("ydxzh")Short ydxzh);
	
	@Select("SELECT count(*) FROM T_fenlikucun where xuhao = #{xuhao}")
	Integer countFenliKucunByXuhao(@Param("xuhao")Integer xuhao);
	
	/**
	 * 更新分离库存表
	 */
	void updateFenLiKuCun(Map<String, Object> paramMap);
	
	/**
	 * 查找分离库存表数据
	 */
	@Select("select * from T_fenlikucun where xuhao = #{xuhao}")
	List<FenliKucunEntity> queryFenlikucunByXuhao(@Param("xuhao")Integer xuhao);
	/**
	 * 查找分离库存表数据
	 */
	List<FenliKucunEntity> queryFenliKucunEntity(@Param("ydbhid")String ydbhid, @Param("ydxzh") Integer ydxzh,  @Param("gs")String gs);
	
	@Update("UPDATE HCZZQD_source SET dhgrid = #{grid}, dhsj=#{dhsj} ,ydzh=#{ydzh} ,auto_arrive =#{autoArrive} WHERE xuhao = #{xuhao}")
	void updateArriveStateByXuhao(@Param("grid")String grid,@Param("dhsj")String dhsj,@Param("ydzh")Integer ydzh,@Param("xuhao")Integer xuhao,@Param("autoArrive")Integer autoArrive);
	
	/**
	 * 更新分离库存表
	 */
	void updateFenLiKuCunByXuhao(Map<String, Object> paramMap);
	
	/**
	 * 往分离库存表中插入数据
	 */
	void insertTFenLiKuCun(Map<String, Object> map);

	@Delete("delete t_fenlikucun where xuhao=#{xuhao}")
	void deleteGoodArriveFenLiKucunByXuhao(@Param("xuhao")Integer xuhao);

	Integer countFenliKucunEntity(@Param("ydbhid")String ydbhid, @Param("intValue")int intValue, @Param("fazhan")String fazhan);

	@Select("select count(*) from t_fenlikucun where ydbhid = #{ydbhid} and ydxzh = #{ydxzh} and gs=#{gs}")
	Integer countFenliKucun(@Param("ydbhid")String ydbhid, @Param("gs")String gs, @Param("ydxzh")Integer ydxzh);
	
	@Update("UPDATE HCZZQD_source SET dhgrid = '', dhsj=null ,ydzh=0,auto_arrive=0 WHERE xuhao = #{xuhao} ")
	void deleteGoodArriveStatesByXuhao(@Param("xuhao")Integer xuhao);

	@Update("UPDATE t_fenlikucun SET JIANSHU = #{jianshu} , TIJI = #{tiji}  , ZHL= #{zhl} WHERE xuhao = #{xuhao}")
	void updateBundleFenLiKuCunByXuhao(HashMap<String, Object> orderDetail);

	List<FenliKucunEntity> queryFenlikucunByXuhaos(@Param("xuhaos")Integer[] xuhaos);

	@Select("select * from T_fenlikucun where gs in (#{fazhan},#{daozhan}) order by dhsj desc")
	List<FenliKucunEntity> getLastFenlikucunByFazhanOrDaozhan(@Param("ydbhid")String ydbhid, @Param("ydxzh")Integer ydxzh,@Param("fazhan")String fazhan,@Param("daozhan")String daozhan);

	@Update("update t_fenlikucun SET jianshu = jianshu - #{receipt.jianshu} ,zhl = zhl-#{receipt.zhl} ,tiji = tiji-#{receipt.tiji} "
			+ "where ydbhid = #{receipt.ydbhid} and ydxzh = #{receipt.ydxzh} and gs =#{companyName}")
	void recoverFenlikucun(@Param("receipt")BundleReceipt receipt,@Param("companyName")String companyName);

	@Select("select count(*) from t_fenlikucun where ydbhid = #{ydbhid} and ydxzh = #{ydxzh} and gs=#{company} and (tiji>0 or zhl>0 or jianshu>0)")
	Integer countFenliKucunIsNotZero(@Param("ydbhid")String ydbhid, @Param("company")String company, @Param("ydxzh")Integer ydxzh);

	@Update("update t_fenlikucun SET jianshu = jianshu - #{receipt.jianshu} ,zhl = zhl-#{receipt.zhl} ,tiji = tiji-#{receipt.tiji} "
			+ "where ydbhid = #{receipt.ydbhid} and ydxzh = #{receipt.ydxzh} and gs =#{company} and convert(varchar(20),dhsj,120) >= #{zhchrq}")
	void recoverFenlikucunByZhchrq(@Param("receipt")BundleReceipt receipt, @Param("company")String company, @Param("zhchrq")String zhchrq);
}
