package com.ycgwl.kylin.web.transport.controller;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.service.api.IGoodArriveService;
import com.ycgwl.kylin.transport.service.api.ITransportOrderService;
import com.ycgwl.kylin.web.transport.BaseController;
import com.ycgwl.kylin.web.transport.entity.GoodArriveModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/transport/goodArrive")
public class GoodArriveController extends BaseController {

	@Resource
	private ITransportOrderService orderService;
	
	@Resource
	private IGoodArriveService goodArriveService;
	
	/**
	 * @Description: 到货的保存 
	 * @param map 接受前台传递的json数据 
	 * @return @exception
	 */
	@RequestMapping("/arrive/save")
	@ResponseBody
	public JsonResult saveGoodArrive(@RequestBody RequestJsonEntity map) {
		User user = ContextHepler.getCurrentUser();
		logger.debug("用户操作行为日志:操作->到货;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		try {
			map.put("grid", user.getAccount());
			map.put("gs", user.getCompany());
			return goodArriveService.saveGoodArrive(map);
		} catch (ParameterException pe) {
			return JsonResult.getConveyResult("400", pe.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "到货异常,请重新操作");
		}
	}

	/**
	 * @Description: 查询需要到货的运单/车辆 @return @exception
	 */
	@RequestMapping("/arrive/search")
	@ResponseBody
	public JsonResult searchConvey(@RequestBody GoodArriveModel arriveModel) {
		User user = ContextHepler.getCurrentUser();
		logger.debug("用户操作行为日志:操作->查询到货;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		String type = arriveModel.getType();
		String company = user.getCompany();
		try {
			if ("单票".equals(type)) {
				return goodArriveService.searchGoodArriveByYdbhid(arriveModel.getYdbhid(), company,user.getAccount());
			}
			return goodArriveService.searchGoodArriveByChxh(arriveModel.getFchrq(), arriveModel.getChxh(), company,user.getAccount());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "输入条件有误");
		}
	}
	
	/**
	 * @Description: 页面跳转到到货页面 @return @exception
	 */
	@RequestMapping("/arrive")
	public String toGoodArrive() {
		return "transport/convey/arrive";
	}
	
	/**
	 * 撤销到货
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-15 15:34:37
	 * @param map
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult deleteGoodArrive(@RequestBody RequestJsonEntity map) {
		User user = ContextHepler.getCurrentUser();
		logger.debug("用户操作行为日志:操作->撤销到货;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		map.put("company", user.getCompany());
		map.put("grid", user.getAccount());
		try {
			return goodArriveService.deleteGoodArrive(map);
		}catch(ParameterException ee) {
			return JsonResult.getConveyResult("400", ee.getTipMessage());
		}catch (Exception e) {
			return JsonResult.getConveyResult("400", "撤销失败");
		}
	}
	
	/**
	 * 批量确认到货
	 * @author fusen.feng
	 */
	@RequestMapping("/batchGoodArrive")
	@ResponseBody
	public JsonResult batchGoodArrive(@RequestBody RequestJsonEntity map) {
		User user = ContextHepler.getCurrentUser();
		map.put("company", user.getCompany());
		map.put("grid", user.getAccount());
		JsonResult jsonResult = this.getJsonResult();
		try {
			jsonResult.putAll(goodArriveService.batchGoodArrive(map));
		}catch(ParameterException ee) {
			return JsonResult.getConveyResult("400", ee.getTipMessage());
		}catch(Exception ee) {
			jsonResult = this.putJsonResult(jsonResult, "400", "系统异常!", ee.getMessage());
		}
		return jsonResult;
	}
	
	
	/**
	 * 批量撤销到货
	 * @author fusen.feng
	 */
	@RequestMapping("/batchRepealGoodArrive")
	@ResponseBody
	public JsonResult batchRepealGoodArrive(@RequestBody RequestJsonEntity map) {
		User user = ContextHepler.getCurrentUser();
		map.put("company", user.getCompany());
		map.put("grid", user.getAccount());
		map.put("account", user.getAccount());
		try {
			return goodArriveService.batchRepealGoodArrive(map);
		}catch(Exception ee) {
			return JsonResult.getConveyResult("400", "撤销失败");
		}
	}
		
}
