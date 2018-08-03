package com.ycgwl.kylin.web.transport.controller;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;

import com.ycgwl.kylin.transport.entity.BundleReceipt;
import com.ycgwl.kylin.transport.entity.BundleReceiptSearch;
import com.ycgwl.kylin.transport.entity.TransportOrder;
import com.ycgwl.kylin.transport.entity.adjunct.ConveyWay;
import com.ycgwl.kylin.transport.entity.adjunct.VehicleInfo;
import com.ycgwl.kylin.transport.service.api.AdjunctSomethingService;
import com.ycgwl.kylin.transport.service.api.BundleReceiptService;
import com.ycgwl.kylin.transport.service.api.ITransportOrderService;
import com.ycgwl.kylin.transport.service.api.TransportOrderDetailService;
import com.ycgwl.kylin.web.transport.BaseController;
import com.ycgwl.kylin.web.transport.entity.BundleReceiptSearchModel;
import com.ycgwl.kylin.web.transport.util.ConveyUtils;
import com.ycgwl.kylin.web.transport.util.DateRangeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

/**
 * 装载管理web层
 * @developer Create by <a href="mailto:110686@ycgwl.com">wanyj</a> at
 *            2017年5月31日
 */
@Controller
@RequestMapping("/transport/bundle")
public class BundleController extends BaseController {

	@Resource
	private BundleReceiptService bundleReceiptService;
	@Resource
	private AdjunctSomethingService adjunctSomethingService;
	@Resource
	private ITransportOrderService transportOrderService;
	@Resource
	private TransportOrderDetailService detailService;


	/**
	 * 查询要装载的运单号
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-06 09:02:47
	 * @param ydbhid
	 * @param iType
	 * @return
	 */
	@RequestMapping(value="/query/convey/{ydbhid}/{iType}")
	@ResponseBody
	public JsonResult queryBundleConvey(@PathVariable("ydbhid")String ydbhid, @PathVariable("iType")String iType) {
		try {
			String companyName = ContextHepler.getCompanyName().trim();

			return bundleReceiptService.queryBundleConvey(ydbhid.trim(),iType.trim(),companyName);
		} catch (BusinessException e) {
			return JsonResult.getConveyResult("500", "服务器异常");
		}
	}

	@RequestMapping(value = "/income/save")
	@RequiresPermissions("income:bundle:save")
	@ResponseBody
	public JsonResult createIncome(@RequestBody RequestJsonEntity entity) {
		User user = ContextHepler.getCurrentUser();
		entity.put("username", user.getUserName());
		entity.put("account",user.getAccount());
		entity.put("company", user.getCompany());
		try {
			return bundleReceiptService.createIncome(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/isExistChxhAndZhchrq")
	@ResponseBody
	public JsonResult isExistChxhAndZhchrq(@RequestBody RequestJsonEntity entity) {
		Boolean isExist = bundleReceiptService.isExistChxhAndZhchrq(entity.getString("chxh"),entity.getString("zhchrq"));
		JsonResult result = new JsonResult();
		result.put("resultCode", "200");
		result.put("reason", "查询成功");
		result.put("isExist", isExist);
		return result;
	}
	

	/**
	 * 装载逻辑修改后的保存
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-06 08:44:15
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/insert/save")
	@ResponseBody
	public JsonResult saveBundle(@RequestBody RequestJsonEntity requestentity) {
		User user = ContextHepler.getCurrentUser();
		
		// 获取装载方式 干线中装的不中转和配送运输 发站必须是 当前登陆公司 
		if ( (Integer.parseInt(requestentity.getString("iType")) == 0 && "不中转".equals(requestentity.getString("istransfer")))
				|| Integer.parseInt(requestentity.getString("iType")) == 1  ) {
			if (!user.getCompany().equals(requestentity.getString("fazhan"))) {
				return  JsonResult.getConveyResult("400", "发站信息错误,请检查！");
			}
		}
		
		requestentity.put("company", user.getCompany());
		requestentity.put("grid", user.getAccount());
		requestentity.put("hyy", user.getUserName());
		requestentity.put("zhipiao2", user.getUserName());
		try {
			return bundleReceiptService.saveBundle(requestentity);
		}catch(ParameterException pe) {
			return JsonResult.getConveyResult("400", pe.getTipMessage());
		}catch(BusinessException be) {
			return JsonResult.getConveyResult("400", be.getTipMessage());
		}catch (Exception e) {
			return JsonResult.getConveyResult("500", "服务器异常");
		}
	}
	/**
	 * 跳转首页
	 * @developer Create by <a href="mailto:110686@ycgwl.com">wanyj</a> at
	 *            2017年6月2日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manage")
	public String manage(HttpServletRequest request, Model model) {
		LocalDate now = LocalDate.now();
		StringBuilder sb = new StringBuilder("");
		String fcdate = sb.append(now.minusMonths(1).toString()).append(" 至 ").append(now.plusDays(1)).toString();
		model.addAttribute("fcdate",fcdate );
		return "transport/bundle/manage";
	}
	
	/**
	 * 查看装载清单
	 * @developer Create by <a href="mailto:110686@ycgwl.com">wanyj</a> at 2017年6月2日
	 * @param request
	 * @param chxh		车牌号
	 * @param fchrq		发车日期
	 * @return	查看装载清单页面
	 * 
	 */
	@RequestMapping(value = "/manage/check/{chXh}/{fchrq}")
	public String check(BundleReceiptSearchModel searchModel, @PathVariable("chXh")String chXh, @PathVariable("fchrq")Date fchrq, Model model) {
		try {
			String chxhresult = java.net.URLDecoder.decode(chXh, "utf-8").trim();
			BundleReceipt bundleReceipt = bundleReceiptService.getBundleReceipt(chxhresult, fchrq);
			try{
				if(bundleReceipt.getElseCost()==null)	//从tms录单的时候,其他成本并不保存在装载表中,需要从成本表中获取
					bundleReceipt.setElseCost(adjunctSomethingService.getElseCost(chxhresult,bundleReceipt.getZhchrq()));
			}catch(Exception exp){
				bundleReceipt.setElseCost(new BigDecimal(0));//防止tms录单的时候可能会报错,如果报错就设置成0
			}
			model.addAttribute("receipt", bundleReceipt);
			model.addAttribute("searchModel", searchModel);
			return "transport/bundle/check";
		} catch (Exception e) {
			exception(e, model);
			return "transport/bundle/check";
		}
	}

	/**
	 * 首页的装载查询    
	 *  这里是用于跳转的时候可能携带参数准备的方法,与上面的不冲突
	 * <p>	
	 * @developer Create by <a href="mailto:110686@ycgwl.com">wanyj</a> at 2017年6月2日
	 * @param searchModel    模糊查询参数
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manage/query")
	@ResponseBody
	public JsonResult queryManage(@RequestBody BundleReceiptSearchModel searchModel) {
		User user = null;
		try {
			user = ContextHepler.getCurrentUser(); 
			logger.debug("用户操作行为日志:操作->装载清单查询;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
		}catch(Exception e) {
			return JsonResult.getConveyResult("500", "登录已过期，请重新登录！");
		}
		BundleReceiptSearch receiptsearch = revertBean(searchModel).setCompanyName(user.getCompany());
		try {
			JsonResult result = JsonResult.getConveyResult("200", "查询成功");
			if(searchModel.getSize()==null){
				searchModel.setSize(10);
			}
			result.put("page", bundleReceiptService.searchBundleReceiptV2(receiptsearch, searchModel.getNum(), searchModel.getSize()));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.getConveyResult("400", "装载查询异常");
		}
	}

	/**
	 * 跳转到新增页面
	 * <p>
	 * 
	 * @developer Create by <a href="mailto:110686@ycgwl.com">wanyj</a> at
	 *            2017年6月2日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert")
	public String insertPage(HttpServletRequest request, Model model) {
		model.addAttribute("ysfsList", adjunctSomethingService.listConveyWays());// 运输方式
		String allDaozhan = (String) request.getSession().getAttribute("ALL_DAOZHAN_BY_COMPANY");
		if(!StringUtils.isNotBlank(allDaozhan)) {
			List<String> daozhanList = adjunctSomethingService.getAllDaozhan(ContextHepler.getCompanyName());
			String daozhan = StringUtils.join(daozhanList,",");
			request.getSession().setAttribute("ALL_DAOZHAN_BY_COMPANY",daozhan);
		}
		model.addAttribute("company", ContextHepler.getCompanyName());
		return "transport/bundle/insert";
	}
	public String getEndPlaceNameFromTransportOrder(TransportOrder order){
		StringBuilder shrProvinces = new StringBuilder("");
		if(StringUtils.isNotBlank(order.getDhShengfen())){
			shrProvinces.append(order.getDhShengfen());
		}
		if(StringUtils.isNotBlank(order.getDhChengsi())){
			if(shrProvinces.length() > 0){
				shrProvinces.append("-");
			}
			shrProvinces.append(order.getDhChengsi());
		}
		if(StringUtils.isNotBlank(order.getDhAddr())){
			if(shrProvinces.length() > 0){
				shrProvinces.append("-");
			}
			shrProvinces.append(order.getDhAddr());
		}
		return shrProvinces.toString();
	}
	/**
	 * @Description: 页面跳转 由于需求更改不需要页面跳转,该方法暂时不再使用 @param request @param
	 * response @param model @return @exception
	 */
	@Deprecated
	@RequestMapping(value = "/manage/skip")
	public String skipPage(HttpServletRequest request, HttpServletResponse response, RedirectAttributes model) {
		String pageType = request.getParameter("pageType");
		if ("convey".equals(pageType)) {
			model.addAttribute("ydbhid", request.getParameter("ydbhid"));
			model.addAttribute("beginPlacename", request.getParameter("beginPlacename"));
			model.addAttribute("daozhan", request.getParameter("daozhan"));
			model.addAttribute("yshhm", request.getParameter("yshhm"));
			model.addAttribute("searchdate", request.getParameter("searchdate"));
			model.addAttribute("status", request.getParameter("status"));

			model.addAttribute("num", request.getParameter("num"));
			model.addAttribute("size", request.getParameter("size"));
			return "redirect:/transport/convey/manage/search";
		}
		return "redirect:/transport/bundle/manage";
	}


	@RequestMapping("/wxName/{wxName}")
	@ResponseBody
	public Object getWxMessage(@PathVariable("wxName")String wxName) {
		try{
			wxName=java.net.URLDecoder.decode(wxName,"utf-8");
			return adjunctSomethingService.listForeignRouteByName(wxName);
		}catch(Exception e){
			return Collections.emptyList();
		}
	}
	@RequestMapping("/chxh/{chxh}")
	@ResponseBody
	public Object getChxh(@PathVariable("chxh")String chxh) {
		try{
			chxh=java.net.URLDecoder.decode(chxh,"utf-8");
			Collection<VehicleInfo> vehicleList = adjunctSomethingService.listVehicleInfoByNumber(chxh);
			List<VehicleInfo> resultChxh = new ArrayList<>();
			for (VehicleInfo vehicleInfo : vehicleList) {
				resultChxh.add(vehicleInfo);
			}
			return resultChxh;
		}catch(Exception e){
			return Collections.emptyList();
		}
	}

	@RequestMapping("/conveyWays")
	@ResponseBody
	public JsonResult getYsfs(HttpServletRequest request) {
		Collection<ConveyWay> list = (Collection<ConveyWay>) request.getSession().getAttribute("YSFS_DATA");
		if(list == null) {
			list = adjunctSomethingService.listConveyWays();
			request.getSession().setAttribute("YSFS_DATA", list);//获取运输方式
		}
		JsonResult result = new JsonResult();
		result.put("reason", "操作成功");
		result.put("resultCode", 200);
		result.put("conveyWays", list);
		return result;
	}
	
	/**
	 * 修改成本信息(财务总监权限)
	 */
	@RequestMapping("/modifyIncomeManager")
	@RequiresPermissions("income:modify:manager")
	@ResponseBody
	public JsonResult modifyIncomeManager(@RequestBody RequestJsonEntity entity) {
		try {
			User user = ContextHepler.getCurrentUser();
			user.getRoles();
			entity.put("account", user.getAccount());
			entity.put("grid", user.getUserName());
			entity.put("company", user.getCompany());
			return bundleReceiptService.modifyIncome(entity,3);
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "修改成本失败,请重试!");
		}
	}

	/**
	 * 修改成本信息(普通权限）
	 */
	@RequestMapping("/modifyIncomeCommon")
	@RequiresPermissions("income:modify:common")
	@ResponseBody
	public JsonResult modifyIncomeCommon(@RequestBody RequestJsonEntity entity) {
		try {
			User user = ContextHepler.getCurrentUser();
			user.getRoles();
			entity.put("account", user.getAccount());
			entity.put("grid", user.getUserName());
			entity.put("company", user.getCompany());
			return bundleReceiptService.modifyIncome(entity,1);
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "修改成本失败,请重试!");
		}
	}
	
	
	/**
	 * 修改成本信息
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-01-06 15:15:32
	 * @return
	 */
	@RequestMapping("/modifyIncomeNoRight")
	@RequiresPermissions("income:modify:noright")
	@ResponseBody
	public JsonResult modifyIncomeNoRight(@RequestBody RequestJsonEntity entity) {
		try {
			User user = ContextHepler.getCurrentUser();
			user.getRoles();
			entity.put("account", user.getAccount());
			entity.put("grid", user.getUserName());
			entity.put("company", user.getCompany());
			return bundleReceiptService.modifyIncome(entity,0);
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "修改成本失败,请重试!");
		}
	}

	@RequestMapping(value = "/manage/print",produces = "text/html;charset=UTF-8")
	public void printExcel(BundleReceiptSearchModel searchModel, HttpServletRequest request,
                           HttpServletResponse response) throws UnsupportedEncodingException {
		List<HashMap<String,Object>> list = bundleReceiptService.searchBundleReceiptForPrint(revertBean(searchModel).setCompanyName(ContextHepler.getCompanyName()));
		String excelPath = request.getSession().getServletContext().getRealPath("/") + "static" + File.separator
				+ "upload" + File.separator + "template" + File.separator + "装载清单.xls";
		logger.debug("开始读取excle模板,路径为" + excelPath);
		HSSFWorkbook workBook;
		try {
			workBook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(excelPath)));
			HSSFSheet sheet = workBook.getSheetAt(0);
			for (int i = 0; i < list.size(); i++) {
				logger.debug((String) list.get(i).get("YDBHID"));
				HSSFRow row = sheet.createRow(i + 1);// 第二行开始输出
				ConveyUtils.buildBundleRow(row, list.get(i));
			}
			response.reset();
			response.setContentType("application/x-msdownload;charset=UTF-8");
			String fileName = java.net.URLEncoder.encode("装载清单" + LocalDate.now(), "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ServletOutputStream out = response.getOutputStream();
			workBook.write(out);
			out.close();
			workBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public BundleReceiptSearch revertBean(BundleReceiptSearchModel searchModel) {
		BundleReceiptSearch receiptsearch = new BundleReceiptSearch();
		// 设置公司名称,只搜索本公司的装载清单
		receiptsearch.setChxh(searchModel.getChxh()).setDaozhan(searchModel.getDaozhan())
			.setFazhan(searchModel.getFazhan()).setClientName(searchModel.getClientName())
			.setWxName(searchModel.getWxName())
			.setYdbhid("运单号/外线电话".equals(searchModel.getYdbhid())?null:searchModel.getYdbhid());
		String[] fcdates = DateRangeUtil.finds(searchModel.getFcdate(), 2);
		if (fcdates.length > 0) {
			receiptsearch.setFchstarttime(fcdates[0]);
			if (fcdates.length > 1) 
				receiptsearch.setFchendtime(LocalDate.parse(fcdates[1]).plusDays(1).toString());
		}
		// 预计达到时间
		String[] dddates = DateRangeUtil.finds(searchModel.getDddate(), 2);
		if (dddates.length > 0) {
			receiptsearch.setYjstarttime(dddates[0]);
			if (dddates.length > 1) 
				receiptsearch.setYjendtime(LocalDate.parse(dddates[1]).plusDays(1).toString());
		}
		// 录入时间
		String[] lrdates = DateRangeUtil.finds(searchModel.getLrdate(), 2);
		if (lrdates.length > 0) {
			receiptsearch.setLrsjstarttime(lrdates[0]);
			if (lrdates.length > 1) 
				receiptsearch.setLrsjendtime(LocalDate.parse(lrdates[1]).plusDays(1).toString());
		}
		return receiptsearch;
	}



}
