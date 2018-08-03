/**
 * kylin-transport-api
 * com.ycgwl.kylin.transport.service.api
 */
package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.BundleReceipt;
import com.ycgwl.kylin.transport.entity.BundleReceiptHomePageEntity;
import com.ycgwl.kylin.transport.entity.BundleReceiptSearch;
import com.ycgwl.kylin.transport.entity.TransportOrderDetail;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 装载清单
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-04-26 17:08:35
 */
public interface BundleReceiptService {

	/**
	  * getBundleReceipt
	  * @Description: 查询一车的装载信息
	  * @param chxh 车牌号
	  * @param fchrq 装载日期
	  * @return  返回一车的装载信息
	  * @exception
	  */
	BundleReceipt getBundleReceipt(String chxh, Date fchrq) throws Exception ;

	/**
	 * 
	  * @Description: 按照发车时间降序排列运单的装载信息
	  * @param ydbhid
	  * @return
	  * @exception
	 */
	List<BundleReceipt> queryBundleReceiptByYdbhidDesc(String ydbhid);
	
	/**
	 * 根据运单号获取最后一单装载信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月31日
	 * @param ydbhid
	 * @return
	 */
	List<BundleReceipt> getLastBundleReceiptByYdbhid(String ydbhid);

	Collection<TransportOrderDetail> queryTransportOrderDetailByYdbhid(String ydbhid);

	List<BundleReceipt> queryLastBundleReceiptByYdbhid(String ydbhid);

	/**
	 * 保存装载
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-01 09:22:20
	 * @param map
	 * @return
	 */
	JsonResult saveBundle(RequestJsonEntity map)throws ParameterException,BusinessException,ParseException;
	/**
	 * 查询要装载的运单号
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-06 09:16:12
	 * @param ydbhid
	 * @param iType
	 * @param companyName
	 */
	JsonResult queryBundleConvey(String ydbhid, String iType, String companyName);


	BundleReceipt getBundleReceiptByXuhao(String xuhao);
	
	
	public PageInfo<BundleReceiptHomePageEntity> searchBundleReceiptV2(BundleReceiptSearch receiptSearch, int pageNum, int pageSize) throws ParameterException, BusinessException;

	public JsonResult modifyIncome(RequestJsonEntity entity)throws Exception;

	JsonResult createIncome(RequestJsonEntity entity) throws ParseException ;

	List<BundleReceipt> queryBundleReceiptByYdbhidTime(String ydbhid, String zhchrq, String chxh);

	List<HashMap<String,Object>> searchBundleReceiptForPrint(BundleReceiptSearch receiptsearch);

	BundleReceipt findBundleReceiptByXuhao(Integer xuhao);

	/**
	 * 查看是否已经存在相同的车牌号和日期
	 * @param chxh
	 * @param zhchrq
	 * @return
	 */
	boolean isExistChxhAndZhchrq(String chxh, String zhchrq);

	/**
	 * 撤销到货物入库操作
	 * @param account  当前用户账户
	 * @param ydbhid   运单编号
	 * @param company  当前等陆人公司
	 * @param userName 用户姓名
	 * @return
	 */
    JsonResult undoCargoStorage(String company, String userName, String account, String ydbhid);
}
