package com.ycgwl.kylin.web.transport.util;

import java.util.Arrays;
import java.util.List;

public abstract class MessageUtil {
	public static final String SUCCESS_MESSAGE = "success_message";//成功的消息
	public static final String WARNING_MESSAGE = "warning_message";//警告消息
	public static final String ERROR_MESSAGE = "error_message";//错误消息
	
	/** 沱牌的品名下拉框 */
	public static final List<String> TUOPAIPINMINGLIST = Arrays.asList(
			"生态窖藏·舍得酒坊 500ml×6","天曲10年·沱牌曲酒 500ml×6","30年生态窖龄·沱牌特曲 500ml×6(普箱)",
			"品味·舍得 500ml×6","品味·舍得 500ml×4(品鉴专用品)","舍得1000ML×4(智慧讲堂）",
			"舍得1000ML×4(品鉴装）","舍之道500ML×6","舍之道1000ML×4（品鉴装）");
	
	
	public static final String NOTFOUNDCONVEY = "运单不存在";//运单不存在
	public static final String NOTBUNDLECONVEY = "运单未装载";//运单未装载
	public static final String NOTAUTHORITYCONVEY = "公司无权限操作此运单";//公司无权限操作此运单
	public static final String WRONGCHXHORDATE = "输入车号或装车日期有误！";//输入车号或装车日期有误
	
}
