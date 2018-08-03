package com.ycgwl.kylin.transport.persistent;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UndoLoadingMapper {
	

	/** 这里查询的是 该序号对应的成本_D表中的ins_no有几条 */
	Integer countIncome_Dbyxuhao(String xuhao);
	
	
	void deleteHczzqdSourceByXuhao(@Param("xuhao")String xuhao);


	void deleteHczzqdBeizhuByXuhao(@Param("xuhao")String xuhao);


	void deleteHwydRoute(@Param("xuhao")Integer xuhao,@Param("ydbhid")String ydbhid);


	void deleteIncome_DByxuhao(String xuhao);


	void deleteIncome_HByxuhao(String xuhao);

	@Select("SELECT count(*) FROM THQSD  where ydbhid = #{ydbhid}")
	Integer CountTiHuodan(String ydbhid);

	@Select("SELECT count(*) FROM t_car_out  where ydbhid = #{ydbhid}")
	Integer CountSonghuoHuodan(String ydbhid);

}
