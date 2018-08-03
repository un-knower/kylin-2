package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.BundleReceipt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Descrption 到货相关逻辑
 * @email <a href="109668@ycgwl.com">lihuixia</a>
 * @date 2018年04月17日 上午09:39:00
 */
public interface ArriveStationService {
	
	/**
	 * 自动到站逻辑（签收的时候不是干线中转可以自动到站）
	 * @param autoArriveParam ：chbh,gs（公司）,ydbhid（运单号）,grid（工号） 
	 */
	void autoArriveStation(HashMap<String, Object> autoArriveParam);
	
	/**
	 * 撤销签收的时候,撤销自动的到货
	 * @param deliveryNumber
	 * @param ydbhid
	 */
	void undoSignArriveStation(Integer deliveryNumber, String ydbhid) throws ParameterException;


	/**
	 * 检查是否已经装载完毕（如果存在分装的情况会出现未装完就到货，防止这样的情况发生）
	 * @param ydbhid
	 * @return
	 */
	boolean hasBundleFinish(String ydbhid);

	/**
	 * 获取最后一次装载的所有记录
	 * @param ydbhid
	 * @return
	 */
	List<BundleReceipt> getLastBundleReceipt(String ydbhid);

	/**
	 * 校验到货时间
	 * @param bundle
	 * @param newDhsjDate
	 * @throws ParameterException
	 */
	void checkArriveTime(BundleReceipt bundle, Date newDhsjDate) throws ParameterException;
}
