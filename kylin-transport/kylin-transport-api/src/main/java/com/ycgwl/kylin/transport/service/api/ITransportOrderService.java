package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.dto.ConsignorDeliveryInstructDto;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.ReleaseWaiting;
import com.ycgwl.kylin.transport.vo.PhotoVo;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 运单逻辑服务接口
 * <p>
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 09:21:30
 */
public interface ITransportOrderService {
	
	/**
	 * 多条件分页查询运单信息
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 09:12:57
	 * @param transportOrderSearch 查询条件
	 * @param pageNum  页数
	 * @param pageSize 每页的数量
	 * @return  分页查询结果包含分页信息
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException  业务逻辑处理有误时
	 */
	@Deprecated
	public PageInfo<TransportOrderResult> pageTransportOrderRevision1(TransportOrderSearch transportOrderSearch, int pageNum, int pageSize) throws ParameterException, BusinessException;
	
	/**
	 * 根据运单编号查询运单信息
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 09:14:01
	 * @param ydbhidCollection 运单编号集合
	 * @return 运单列表信息
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException  业务逻辑处理有误时
	 */
	public Collection<TransportOrder> listTransportOrderByYdbhids(Collection<String> ydbhidCollection) throws ParameterException, BusinessException;

	/**
	 * 根据运单编号查询运单信息
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 09:14:41
	 * @param ydbhid 运单编号
	 * @return 运单
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException  业务逻辑处理有误时
	 */
	public TransportOrder getTransportOrderByYdbhid(String ydbhid) throws ParameterException, BusinessException;

	/**
	 * 分页查询尚未装载的运单信息
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 09:15:30
	 * @param transportOrder 查询条件
	 * @param pageNum  当前页
	 * @param pageSize 每页数量
	 * @return 满足条件的运单和分页信息
	 * @throws ParameterException 参数有误时 
	 * @throws BusinessException  业务逻辑处理有误时
	 */
	public PageInfo<TransportOrder> pageTransportOrderNotBundle(TransportOrder transportOrder,int pageNum, int pageSize) throws ParameterException, BusinessException;

	/**
	 * 查询未装载的运单信息
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 09:19:43
	 * @param transportOrder 查询条件
	 * @return 满足条件的运单信息
	 * @throws ParameterException 参数有误时 
	 * @throws BusinessException  业务逻辑处理有误时
	 */
	public Collection<TransportOrder> listTransportOrderNotBundle(TransportOrder transportOrder) throws ParameterException, BusinessException;

	/**
	 * 根据运单号查询财凭信息
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-05-25 09:20:39
	 * @param ydbhid 运单编号
	 * @return 财凭信息
	 * @throws ParameterException 参数有误时 
	 * @throws BusinessException  业务逻辑处理有误时
	 */
	public FinanceCertify getFinanceCertifyByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 多条件分页查询运单信息（第一次修订版，不用mybatis分页控件实现）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年9月18日
	 * @param transportOrderSearch 查询条件
	 * @param pageNum  页数
	 * @param pageSize 每页的数量
	 * @return 分页查询结果包含分页信息
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	@Deprecated
	public PageInfo<TransportOrderResult> pageTransportOrder(TransportOrderSearch transportOrderSearch, int pageNum, int pageSize) throws ParameterException, BusinessException;

	/**
	 * 查询运单信息
	 * @param cancel
	 * @return
	 */
	JsonResult findCancelTransportOrder(TransportOrderCancelResult cancel, String curUserGrid);

	/**
	 * 作废货物运单
	 * @param cancel
	 * @return
	 */
	JsonResult saveCancelWaybill(TransportOrderCancelResult cancel, String curUserGrid,String userName);
	/** 单条的校验 
	 * @throws SQLServerException */
	public JsonResult checkOutConvey(RequestJsonEntity map)throws SQLException;

	/** 单条的插入操作 */
	public void saveConveyItemByItem(BatchTransportOrderEntity entity,FinanceStandardPrice price,String company,String account,String username) throws ParameterException,BusinessException,Exception ;
	
	/**
	 * 多条件分页查询运单信息（第二次修订版，分别查询明细及财凭信息）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年9月18日
	 * @param transportOrderSearch 查询条件
	 * @param pageNum  页数
	 * @param pageSize 每页的数量
	 * @return 分页查询结果包含分页信息
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public PageInfo<TransportOrderResult> pageTransportOrderSecondRevision(TransportOrderSearch transportOrderSearch, int pageNum, int pageSize) throws ParameterException, BusinessException;

	public boolean isTransportExistByCode(String transportCode);
	
	/**
	 * 运单导出查询
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年3月21日
	 * @param transportOrderSearch
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	List<Map<String, Object>> exportTransportOrderList(TransportOrderSearch transportOrderSearch) throws ParameterException, BusinessException;

	/**
	 * 查询等托运人发货指令 历史记录
	 * @param account 当前登录账户
	 * @param cdiDto 等托运人发货指令 查询参数dto
	 * @return
	 */
	public JsonResult queryHistoryRecords(String account, ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 查询
	 * @param account 当前登录账户
	 * @param cdiDto 等托运人发货指令 查询参数dto
	 */
	public JsonResult findWaitingForDelivery(String account, ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 修改通知放货或者等待放货 状态
	 * @param account
	 * @param cdiDto
	 * @return
	 */
	public int updateDeliveryStatus(String account, ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 修改通知放货或者等待放货 状态   日志
	 * @param account
	 * @param cdiDto
	 * @return
	 */
	public int saveDdfhLog(String account, ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 通知放货查询
	 * @param account
	 * @param cdiDto
	 * @return
	 */
	public JsonResult findNoticeDelivery(String account, ConsignorDeliveryInstructDto cdiDto);

	/** 
	 * 等待放货保存
	 * @param releaseWaiting
	 * @param ipAddr  ip
	 * @param currentAccount  工号
	 * @return
	 */
	public JsonResult saveWaitingForDelivery(ReleaseWaiting releaseWaiting, String currentAccount, String ipAddr);

	/**
	 * 查询返单方式
	 * @param waybillNum
	 * @return
	 */
	public String selectIsfdByYdbhid(String waybillNum);

	/**
	 * 校验回单图片是否存在运单
	 */
	public JsonResult checkReorder(List<PhotoVo> photoVolist);

	/**
	 * 保存回单信息
	 * @param account
	 * @param photoinfo
	 * @return
	 * @throws Exception 
	 */
	public JsonResult saveReturnImageInfo(String account, Collection<HashMap<String, String>> photoinfo) throws Exception;

	/**
	 * 查看回单图片
	 * @param ydbhid
	 * @return
	 */
	public JsonResult selectReceipt(String ydbhid);
}
