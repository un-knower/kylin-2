package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.SendMessageLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface SendMessageLogMapper {
	void saveMessageLog(SendMessageLogEntity sendMessageLogEntity);
	List<SendMessageLogEntity> getMessageInfo();
    void updateMessageLog(String ydbhid);
    /**
     * 修改订单的发送成功标识
     * @param ydbhid
     * @param flag
     */
	void updateByYdbhid(@Param("ydbhid")String ydbhid, @Param("flag")int flag,@Param("errorMessage")String errorMessage);
	
	@Select("select mobile from t_sendmessage_mobile where company_name = #{companyName}")
	List<String> getMobileByCompanyName(@Param("companyName")String companyName);
}
