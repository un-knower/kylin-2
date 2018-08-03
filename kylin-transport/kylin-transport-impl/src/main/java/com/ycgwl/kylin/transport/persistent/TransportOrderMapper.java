
package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.entity.Page;
import com.ycgwl.kylin.transport.dto.ConsignorDeliveryInstructDto;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.ReleaseWaiting;
import com.ycgwl.kylin.transport.vo.PhotoUrlAndNameVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <b>Description</b> TODO Add description
 * <p>
 * <b>Author</b> <a href="mailto:110686@ycgwl.com">dingxf</a><br>
 * <b>Date</b> 2017-05-24 21:25:48<br> 
 * <p>
 * @param <OnTheWay>
 */
@Mapper
public interface TransportOrderMapper {
	
	
	@Select("select fazhan,daozhan from HWYD where ydbhid = #{ydbhid}")
	Map<String, String> getTransportFazhanDaozhanByYdbhid(@Param("ydbhid")String ydbhid);
	
	/**
	 * <b>Description</b> 根据运单号查询运单明细
	 */
	List<TransportOrderDetail> getTransportOrderDetail(String ydbhid);
	
	
	/**
	 * <b>Description</b> 根据运单号查询该运单
	 * <p>
	 * <b>Author</b> <a href="mailto:108252@ycgwl.com">wanyujie</a><br>
	 * <b>Date</b> 2017-09-12 21:26:03
	 * <p>
	 */
    TransportOrder getTransportOrderByYdbhid(String ydbhid);

    /**
     * <b>Description</b>批量查询运单号
     * <p>
	 * <b>Author</b> <a href="mailto:108252@ycgwl.com">wanyujie</a><br>
	 * <b>Date</b> 2017-09-12 21:26:03
	 * <p>
	 */
    List<TransportOrder> getTransportOrderByYdbhids(@Param("ydbhids")String[] ydbhids);
	
    /**
     * <b>Description</b>批量查询运单细则信息
     * <p>
	 * <b>Author</b> <a href="mailto:108252@ycgwl.com">wanyujie</a><br>
	 * <b>Date</b> 2017-09-12 21:26:03
	 * <p>
	 */
    List<TransportOrderDetail> getTransportOrderDetailByYdbhids(@Param("ydbhids") String[] ydbhids);
	

	/**
	 * <b>Description</b> 根据运单编号查询运单信息
	 * <p>
	 * <b>Author</b> <a href="mailto:110686@ycgwl.com">dingxf</a><br>
	 * <b>Date</b> 2017-09-12 21:26:03
	 * <p>
	 * @param ydbhid 运单编号
	 * @return 运单信息
	 * <p>
	 */
	public List<TransportOrder> get(String ydbhid);
	

	/**
	 * <b>Description</b> 新增运单一条记录
	 * <p>
	 * <b>Author</b> <a href="mailto:110686@ycgwl.com">dingxf</a><br>
	 * <b>Date</b> 2017-05-24 21:29:47
	 * <p>
	 * @param transportOrder 运单信息
	 * <p>
	 */
	public void insert(TransportOrder transportOrder);

	/**
	 * <b>Description</b> 分页查询运单列表
	 * <p>
	 * <b>Author</b> <a href="mailto:110686@ycgwl.com">dingxf</a><br>
	 * <b>Date</b> 2017-05-24 21:30:16
	 * <p>
	 * @param transportOrderSearch 查询条件
	 * @param rowBounds 分页信息
	 * @return 查询结果和分页信息
	 * <p>
	 */
	@Deprecated
	public Page<TransportOrderResult> queryTransportOrderPage(TransportOrderSearch transportOrderSearch, RowBounds rowBounds);
	

	/**
	 * <b>Description</b> 根据多个运单编号查询对应的运单信息
	 * <p>
	 * <b>Author</b> <a href="mailto:110686@ycgwl.com">dingxf</a><br>
	 * <b>Date</b> 2017-05-24 21:31:10
	 * <p>
	 * @param ydbhidList 运单编号集合
	 * @return 查询到的运单集合
	 * <p>
	 */
	public Collection<TransportOrder> queryTransportOrderByYdbhids(Collection<String> ydbhidList);

	/**
	 * <b>Description</b> 查询未装载的运单信息
	 * <p>
	 * <b>Author</b> <a href="mailto:110686@ycgwl.com">dingxf</a><br>
	 * <b>Date</b> 2017-05-24 21:34:34
	 * <p>
	 * @param transportOrder 查询条件
	 * @return 满足条件并且没有装载的运单信息
	 * <p>
	 */
	public Collection<TransportOrder> queryTransportOrderNotBundle(TransportOrder transportOrder);

	/**
	 * <b>Description</b> 分页查询尚未装载的运单信息
	 * <p>
	 * <b>Author</b> <a href="mailto:110686@ycgwl.com">dingxf</a><br>
	 * <b>Date</b> 2017-05-24 21:35:17
	 * <p>
	 * @param transportOrder 查询条件
	 * @param bounds 分页信息
	 * @return 满足条件并且没有装载的运单信息以及分页信息
	 * <p>
	 */
	public Page<TransportOrder> queryTransportOrderNotBundle(TransportOrder transportOrder, RowBounds bounds);
	
	/**
	 * 分页查询运单列表（不用mybatis分页控件）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年9月18日
	 * @param transportOrderSearch  查询条件
	 * @return 查询结果
	 */
	public Collection<TransportOrderResult> queryTransportOrderPages(TransportOrderSearch transportOrderSearch);
	
	/**
	 *  分页查询运单列表个数（不用mybatis分页控件）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年9月18日
	 * @param transportOrderSearch 查询条件
	 * @return 查询结果
	 */
	public Long queryTransportOrderPagesCount(TransportOrderSearch transportOrderSearch);

	/**
	 * 更新运单表的ysfs字段
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-04 14:47:08
	 * @param ydbhid
	 */
	void updateYsfs(@Param("ysfs")String ysfs,@Param("ydbhid")String ydbhid);

	/**
	 * 查询冲红信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月13日
	 * @param ydbhid
	 * @return
	 */
	List<FiwtResult> selectFinanceReceiveMoneyByYdbhid(String ydbhid);
	
	/**
	 * 更新运单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月14日
	 * @param transportOrder
	 */
	void updateTransportOrderByYdbhid(TransportOrder transportOrder);
	
	/**
	 * 分页查询运单列表（不用mybatis分页控件,分别查询明细和财凭）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月29日
	 * @param transportOrderSearch
	 * @return
	 */
	public Collection<TransportOrderResult> listTransportOrderPages(TransportOrderSearch transportOrderSearch);
	
	/**先查看运单的发站和到站（签收里需要判断该运单登录公司发站或到站才能签收*/
	List<String> selectTransportWaybill(@Param("ydbhids")List<String> ydbhids, @Param("company")String company);
	
	public Integer countTransportCode(String transportCode);
	
	/**
	 * 运单导出查询
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年3月21日
	 * @param transportOrderSearch
	 * @return
	 */
	List<Map<String,Object>> exportTransportOrderList(TransportOrderSearch transportOrderSearch);

	/**
	 * 查询等托运人发货指令 历史记录
	 * @param cdiDto
	 * @return
	 */
	List<Map<String, Object>> queryHistoryRecords(ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 查询作废状态
	 * @param waybillNum  运单号
	 * @return  int 状态码
	 */
	int findInvalidatedState(String waybillNum);

	/**
	 * 查询运单数量
	 * @param cdiDto 
	 * @return
	 */
	int findWayBillCount(ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 查询提货签收单表此运单的数量
	 * @param cdiDto
	 * @return
	 */
	int findDeliveryCount(ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 查询 送货派车单表 的此运单数量
	 * @param cdiDto
	 * @return
	 */
	int findSendCarCount(ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 查询需要设置等待发货的运单
	 * @param cdiDto
	 * @return
	 */
	List<ReleaseWaiting> findWaitingForDelivery(ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 查询货物运单表的发站信息
	 * @param cdiDto
	 * @return 
	 */
	String findStation(ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 修改通知放货或者等待放货 状态
	 * @param cdiDto
	 * @return
	 */
	int updateDeliveryStatus(ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 修改通知放货或者等待放货 状态 日志
	 * @param cdiDto
	 * @return
	 */
	int saveDdfhLog(ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 查看是否 已设置等待放货
	 * @param waybillNum
	 * @return
	 */
	int findEditDelivery(String waybillNum);

	/**
	 * 通知放货查询
	 * @param cdiDto
	 * @return
	 */
	List<ReleaseWaiting> findNoticeDelivery(ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 查询作废状态
	 * @param waybillNum
	 * @return
	 */
	int findCancelState(String waybillNum);

	/**
	 * 在途跟踪查询
	 * @param wayBillNum 运单号
	 */
	List<OnTheWay> selectTrackInfo(String wayBillNum);

	/**
	 * 等待放货保存
	 * @param releaseWaiting
	 * @return
	 */
	int saveWaitingForDelivery(ReleaseWaiting releaseWaiting);

	/**
	 * 查询是否有等待放货表中是否有数据
	 * @param ydbhid
	 * @return
	 */
	int findDdfhCount(String ydbhid);

	/**
	 * 更新等待放货
	 * @param releaseWaiting
	 * @return
	 */
	int updateWaitingForDelivery(ReleaseWaiting releaseWaiting);

	/**
	 * 查询返单方式
	 * @param waybillNum
	 * @return  String  2：电子返单
	 */
	String selectIsfdByYdbhid(String waybillNum);

	/**
	 * 查询送货签收单表此运单的数量
	 * @param cdiDto
	 * @return
	 */
	int findSendCount(ConsignorDeliveryInstructDto cdiDto);

	/**
	 * 查询发站到站的简称
	 * @param fazhan
	 * @return
	 */
	String selectXianLuByStation(String station);

	/**
	 * 校验回单图片是否存在运单
	 * @param waybillNum
	 * @return
	 */
	int checkReorder(String waybillNum);

	/**
	 * 保存回单信息
	 * @param map
	 */
	void saveReturnImageInfo(HashMap<String, Object> map);

	/**
	 * 修改回单的上传状态
	 * @param ss
	 */
	void updateReceiptStatus(@Param("ss") Collection<HashMap<String, String>> ss);

	/**
	 * 查看回单图片
	 * @param ydbhid
	 * @return
	 */
	List<PhotoUrlAndNameVo> selectReceipt(String ydbhid);


}
