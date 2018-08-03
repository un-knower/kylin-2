package com.ycgwl.kylin.web.master.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.KylinTUserGsEntity;
import com.ycgwl.kylin.security.service.api.IKylinTUserGsService;


/**
 * @Descrption 登录相关
 * @email <a href="109668@ycgwl.com">lihuixia</a>
 * @date 2018年04月02日 上午08:27:15
 */
@Controller
public class SysLoginController {

	@Resource
	private IKylinTUserGsService kylinTUserGsService;

	@RequestMapping("/login")
	public String logPage(){
		return  "loginPage";
	}


	@RequestMapping("/companyChoice")
	public String homePage(ModelMap modelMap){
		List<KylinTUserGsEntity> companyList = new ArrayList<KylinTUserGsEntity>();
		companyList = kylinTUserGsService.queryList(ContextHepler.getCurrentUser().getAccount());
		modelMap.put("companyList", companyList);
		return  "companyChoice";
	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}
		return "redirect:login";
	}
	
}
