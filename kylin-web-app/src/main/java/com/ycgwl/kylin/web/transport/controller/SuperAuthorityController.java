package com.ycgwl.kylin.web.transport.controller;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.service.api.BundleReceiptService;
import com.ycgwl.kylin.web.transport.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 超级权限相关
 *
 * @author   chenxm
 * @version [V2.0 2018-06-11 13:15]
 */
@Controller
@RequestMapping("/superAuthority/")
public class SuperAuthorityController extends BaseController {

    @Resource
    private BundleReceiptService bundleReceiptService;
    
    
	@RequestMapping(value = "/toRevokingGoods")
	public String toClientPriceRegister() {
		return "transport/revokingGoods/SuperAuthority";
	}
    

    /**
     * 撤销到货物入库操作
     * @param ydbhid  运单编号
     */
    @RequestMapping("undoCargoStorage/{ydbhid}")
    @ResponseBody
    public JsonResult undoCargoStorage(@PathVariable(value = "ydbhid") String ydbhid){
        // 获取当前用户信息
        User user = ContextHepler.getCurrentUser();
        // 撤销到货物入库操作
        JsonResult jsonResult = null;
        try {
            jsonResult = bundleReceiptService.undoCargoStorage(user.getCompany(), user.getUserName(), user.getAccount(), ydbhid);
        } catch (Exception e) {
            jsonResult.put("400", "操作失败");
        }
        return jsonResult;
    }

}
