package com.ycgwl.kylin.web.transport.controller;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.service.api.UndoLoadingService;
import com.ycgwl.kylin.web.transport.BaseController;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 撤销装载的
 * <p>
 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017年11月28日
 */
@Controller
@RequestMapping("/transport/undo")
public class UndoLoadingController extends BaseController {

	@Resource
	private UndoLoadingService loadingService;
	
	
	@RequestMapping("/manage")
	public String manage(){
		return "transport/undo/manage";
	}
	
	/**
	 * search the conveys that ready for delete;
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017年11月28日
	 * @param ydbhid
	 * @return
	 */
	@RequestMapping("/query/{ydbhid}")
	@ResponseBody
	public JsonResult queryBundle(@PathVariable String ydbhid){
		try{
			return loadingService.queryBundle(ydbhid);
		}catch(Exception e){
			return JsonResult.getConveyResult("500", "查询失败,请刷新页面后");
		}
	}
	
	
	/**
	 * operate for delete
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017年11月28日
	 * @return
	 */
	@ResponseBody
	@Deprecated
	public JsonResult deleteBundles(@RequestBody RequestJsonEntity[] xuhaos){
		//假设可以使用list来接受前台传递过来的数据
		List<String> collect = Arrays.asList(xuhaos).stream().map(xuhao->xuhao.getString("xuhao")).collect(Collectors.toList());
		User user = ContextHepler.getCurrentUser();
		logger.debug("用户操作行为日志:操作->撤销装载清单;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		try {
			return loadingService.deleteBundle(user.getCompany(),user.getAccount(),user.getUserName(),collect);
		} catch(ParameterException e) {
			return JsonResult.getConveyResult("400", e.getTipMessage());
		}catch(BusinessException e) {
			return JsonResult.getConveyResult("400", e.getTipMessage());
		}catch (Exception e) {
			return JsonResult.getConveyResult("400", "撤销装载失败");
		}
	}
	
	/**
	 * 删除装载清单权限按钮
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("undo:loading:delete")
	@ResponseBody
	public JsonResult deleteBundle(@RequestBody RequestJsonEntity[] xuhaos){
		List<String> collect = Arrays.asList(xuhaos).stream().map(xuhao->xuhao.getString("xuhao")).collect(Collectors.toList());
		User user = ContextHepler.getCurrentUser();
		logger.debug("用户操作行为日志:操作->撤销装载清单;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		try {
			return loadingService.deleteBundle(user.getCompany(),user.getAccount(),user.getUserName(),collect);
		} catch(ParameterException e) {
			return JsonResult.getConveyResult("400", e.getTipMessage());
		}catch(BusinessException e) {
			return JsonResult.getConveyResult("400", e.getTipMessage());
		}catch (Exception e) {
			return JsonResult.getConveyResult("400", "撤销装载失败");
		}
	}
}
