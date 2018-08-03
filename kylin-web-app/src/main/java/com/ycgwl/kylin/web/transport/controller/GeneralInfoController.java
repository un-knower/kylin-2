package com.ycgwl.kylin.web.transport.controller;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.Company;
import com.ycgwl.kylin.transport.service.api.AdjunctSomethingService;
import com.ycgwl.kylin.transport.service.api.GeneralInfoService;
import com.ycgwl.kylin.web.transport.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用信息查询
 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @since 2018-01-23
 */
@Controller
@RequestMapping("/transport/generalInfo")
public class GeneralInfoController extends BaseController {
	
	@Resource
	private AdjunctSomethingService adjunctSomethingService;
	
	@Resource
	private GeneralInfoService generalInfoService;
	
	
	@RequestMapping("/getCompanyListAndCurrCompanyInfo")
	@ResponseBody
	public JsonResult getCompanyListAndCurrCompanyInfo(HttpServletRequest request) {
		try {
			User user = ContextHepler.getCurrentUser();
			Collection<Company> list = (Collection<Company>) request.getSession().getAttribute("COMPANY_LIST_ALL");
			if(list == null) {
				list = adjunctSomethingService.queryStationCompany();
				request.getSession().setAttribute("COMPANY_LIST_ALL",list);
			}
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("COMPANY_LIST", list);
			jsonResult.put("CURR_COMPANY_NAME", user.getCompany());
			jsonResult.put("CURR_COMPANY_CODE", user.getCompanyCode());
			jsonResult.put("resultCode", "200");
			jsonResult.put("reason", "查询成功");
			return jsonResult;
		} catch (Exception e) {
			return JsonResult.getConveyResult("500", "用户登录超时");
		}
	}
	
	
	
	/**
	 * 基础数据接口（transport_basic_data表维护）
	 */
	@RequestMapping("/queryBasicInfoByName/{name}")
	@ResponseBody
	public JsonResult queryBasicInfoByName(@PathVariable("cnName")String cnName){
		JsonResult jsonResult = new JsonResult();
		try {
			List<TransportBasicData> result = generalInfoService.queryBasicInfoByName(cnName);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController queryBasicInfoByName() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController queryBasicInfoByName() 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
	
	@RequestMapping("/findBasicInfoByName")
	@ResponseBody
	public JsonResult findBasicInfoByName(@RequestBody HashMap<String,String> map){
		JsonResult jsonResult = new JsonResult();
		try {
			List<TransportBasicData> data = generalInfoService.queryBasicInfoByName(map.get("keyName"));
			jsonResult.put("succsess", true);
			jsonResult.put("data", data);
		} catch (Exception e) {
			jsonResult.put("succsess", false);
			logger.error("##ERROR -----GeneralInfoController queryBasicInfoByName() 报错：" + e.getCause());
		}
		return jsonResult;	
	}
	
	/**
	 * 基础数据接口（transport_basic_data表维护）
	 */
	@RequestMapping("/queryBasicInfoByParentName/{parentname}")
	@ResponseBody
	public JsonResult queryBasicInfoByParentName(@PathVariable("parentname")String parentname){
		JsonResult jsonResult = new JsonResult();
		try {
			List<TransportBasicData> result = generalInfoService.queryBasicInfoByParentName(parentname);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController queryBasicInfoByName() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController queryBasicInfoByName() 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
	
	/**
	 * 基础数据接口（transport_basic_data表维护）
	 */
	@RequestMapping("/queryAllBasicInfo")
	@ResponseBody
	public JsonResult queryAllBasicInfo(){
		JsonResult jsonResult = new JsonResult();
		try {
			List<TransportBasicData> result = generalInfoService.queryAllBasicInfo();
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController queryBasicInfoByName() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController queryBasicInfoByName() 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
	
	
	/**
	 * 录入取货指令-取货里程
	 */
	@RequestMapping("/pickGoodsMileage")
	@ResponseBody
	public JsonResult pickGoodsMileage(){
		JsonResult jsonResult = new JsonResult();
		try {
			List<BundleArriveMileage> result = generalInfoService.pickGoodsMileage();
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController pickGoodsMileage() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController pickGoodsMileage() 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
	
	
	/**
	 * 公司-目的站
	 */
	@RequestMapping("/destinationStation")
	@ResponseBody
	public JsonResult destinationStation(){
		JsonResult jsonResult = new JsonResult();
		try {
			List<CompanyInfo> result = generalInfoService.destinationStation();
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController destinationStation() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController destinationStation() 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
	
	
	/**
	 * 公司-到站网点
	 */
	@RequestMapping("/destinationPointStation/{companyCode}")
	@ResponseBody
	public JsonResult destinationPointStation(@PathVariable("companyCode")String companyCode){
		JsonResult jsonResult = new JsonResult();
		try {
			List<DestinationNetPoint> result = generalInfoService.destinationPointStation(companyCode);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController destinationStation() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController destinationStation() 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
	
	/**
	 * 公司-到站网点
	 */
	@RequestMapping("/destPointByCompanyName")
	@ResponseBody
	public JsonResult destPointByCompanyName(@RequestBody Map<String,Object> param){
		String companyName = (String) param.get("companyName");
		JsonResult jsonResult = new JsonResult();
		try {
			List<DestinationNetPoint> result = generalInfoService.destinationPointStationByConpanyName(companyName);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController destinationPointStationByConpanyName() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController destinationPointStationByConpanyName() 报错：" + e.getCause());
		}catch(Exception e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "服务器异常");
			logger.debug("GeneralInfoController destinationPointStationByConpanyName() 报错：" + e.getCause());
		}
		return jsonResult;	
	}
	
	/**
	 * 录入取货指令-取货地（显示： 考核区段，送货里程，目的地）
	 */
	@RequestMapping("/pickGoodsAddress")
	@ResponseBody
	public JsonResult pickGoodsAddress(){
		JsonResult jsonResult = new JsonResult();
		User user = null;
		try {
			user = ContextHepler.getCurrentUser();
		} catch (Exception e) {
			logger.debug("用户操作行为日志:操作->废除运单查询");
			return JsonResult.getConveyResult("500", "登录已过期，请重新登录！");
		}
		logger.debug("用户操作行为日志:操作->运单信息查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		try {
			String companyName = user.getCompany();
			List<BundleArriveMileage> result = generalInfoService.pickGoodsAddress(companyName);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController pickGoodsAddress() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController pickGoodsAddress() 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
	
	/**
	 * 查询司机信息（根据状态公司和编码）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryDriverInfo")
	@ResponseBody
	public JsonResult queryDriverInfo(){
		User user = ContextHepler.getCurrentUser();
		JsonResult jsonResult = new JsonResult();
		try {
			Integer zt = 1;//在职
			String bm = "车管部";
			String gs = user.getCompany();
			List<Driver> result = generalInfoService.queryDriverByGsAndBm(zt, gs, bm);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController queryDriverByGsAndBm() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController queryDriverByGsAndBm() 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
	
	/**
	 * 查询派车提成趟次
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
	 * @return
	 */
	@RequestMapping("/queryCarOutBaseTc")
	@ResponseBody
	public JsonResult queryCarOutBaseTc(){
		JsonResult jsonResult = new JsonResult();
		try {
			List<CarOutBaseTc> result = generalInfoService.queryCarOutBaseTc();
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController queryCarOutBaseTc() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController queryCarOutBaseTc() 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
	
	/**
	 * 查询派车考核对象
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
	 * @return
	 */
	@RequestMapping("/querCarOutBaseKh")
	@ResponseBody
	public JsonResult querCarOutBaseKh(){
		JsonResult jsonResult = new JsonResult();
		try {
			List<CarOutBaseKh> result = generalInfoService.querCarOutBaseKh();
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController querCarOutBaseKh() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("GeneralInfoController querCarOutBaseKh() 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
}
