package com.ycgwl.kylin.web.master.controller;

import com.ycgwl.kylin.entity.R;
import com.ycgwl.kylin.exception.RRException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.KylinGongSiEntity;
import com.ycgwl.kylin.security.entity.KylinTUserGsEntity;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.security.service.api.IKylinTUserGsService;
import com.ycgwl.kylin.security.service.api.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * <p>
 * Title: SysUserController.java
 * </p>
 * 
 * @date 2018年6月13日
 * @author ltao 112656
 * @version 1.0
 */
@Controller
@RequestMapping("sys/user")
public class SysUserController {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private IKylinTUserGsService kylinTUserGsService;

	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	@ResponseBody
	public R info() {
		
		User ygzl = ContextHepler.getCurrentUser();;
		return R.ok().put("user", ContextHepler.getCurrentUser());
	}
	
	/**
	 * 
	 * @param grid
	 * @return
	 */
	/*@RequestMapping("/companyList")
	@ResponseBody
	public R companyList(HttpServletRequest request) {
		List<KylinTUserGsEntity> companyList= new  ArrayList<KylinTUserGsEntity>();
	
		    companyList = kylinTUserGsService.queryList(ContextHepler.getCurrentUser().getAccount());
				return R.ok().put("companyList", companyList);

	}
	*/

	@PostMapping(value = "/headInfo")
	public String headInfo(HttpServletRequest request, Model model) {
		User ygzl = ContextHepler.getCurrentUser();
		String company = request.getParameter("coms");
		

		// 查询账号公司信息，并将公司信息填充到当前登陆用户中
		List<KylinTUserGsEntity> getGsList = kylinTUserGsService.queryList(ContextHepler.getCurrentUser().getAccount());
		boolean flag = false;
		for (KylinTUserGsEntity kylinTUserGsEntity : getGsList) {
			if (kylinTUserGsEntity.equals(company)) {
				flag = true;
				break;
			}
		}

	KylinGongSiEntity gs=	sysUserService.queryByName(company);
	    ygzl.setCompanyCode(gs.getBh());
		ygzl.setCompany(company);
		// modify(ygzl);//修改shiro登录信息
		model.addAttribute("ygzl", ygzl);
		return "index";
	}

	/**
	 * 根据用户的grid获取到
	 * 
	 * @param grid
	 * @return
	 */
	@RequestMapping("/searchByGrid")
	@ResponseBody
	public R searchByGrid(@RequestBody String grid) {

		R r = new R();
		try {

			User getygzl = sysUserService.queryByGrid(grid);
			r.put("code", 200);
			r.put("msg", "查询成功");
			r.put("ygzl", getygzl);
		} catch (RRException e) {
			r.put("code", 400);
			r.put("msg", e.getMsg());
		}
		return r;
	}

	/**
	 * 
	 * @param grid
	 * @return
	 */
	@RequestMapping("/permission/{grid}")
	@ResponseBody
	public R permission(@PathVariable("grid") String grid) {
		R r = new R();
		User sysYgzl = new User();

		try {
			sysYgzl = sysUserService.queryByGrid(grid);

		} catch (RRException e) {
			r.put("code", 400);
			r.put("msg", e.getMsg());

		}
		List<Integer> list = sysUserService.queryAllMenuId(grid);

		sysYgzl.setMenuIdList(list);

		return R.ok().put("sysYgzl", sysYgzl);
	}

}
