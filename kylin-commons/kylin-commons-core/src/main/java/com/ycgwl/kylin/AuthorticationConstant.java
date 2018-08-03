package com.ycgwl.kylin;
/**
 * 权限
 * <p>
 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-03-08 10:05:34
 */
public interface AuthorticationConstant {

	String MESSAGE = "无权限,请与系统管理员申请开通！";
	
	/**冲红权限**/
	String CWPZHCH = "CWPZH_CH";
	
	/**修改成本权限**/
	String EDIT_COST = "EDIT_COST";
	
	/**送货派车单相关权限**/
	String PC_SHOUT = "PC_SHOUT";
	
	/**送货派车查询权限**/
	String PC_SHOUT_QRY = "PC_SHOUT_QRY";
	
	/**提货签收单权限**/
	String THQSHD = "THQSHD";
	
	/**维护的权限**/
	String TMSWEIHUMEN_1 = "TMSWEIHUMEN_1";
	
	/**收货派车权限**/
	String PC_SHIN = "PC_SHIN";
	
	/**调度派车权限**/
	String PC_DDPC = "PC_DDPC";
	
	/**取货派车结算权限**/
	String TJYHS = "TJYHS";
	
	/**装载清单查询**/
	String QRY_HCZZQD = "QRY_HCZZQD";
	
	/**装载清单录入**/
	String HCZZQD = "HCZZQD";
		
	/**运单查询**/
	String QRY_HWYD = "QRY_HWYD";
	
	/**运单录入**/
	String HWYD = "HWYD";
	
	/**签收**/
	String KHQS_IN = "KHQS_IN";
	
	/**收钱**/
	String LRCWPZH = "LRCWPZH";	
		
	/**到货**/
	String EX_LRDHXX = "EX_LRDHXX";
	
	/**运单批量录入**/
	String Batch_IMPORT = "Batch_IMPORT";	
	
	/**生成分理收据权限**/
	String CWSJ_FENLI = "CWSJ_FENLI";

	/**分理收据收钱**/
	String FLSJSQ = "FLSJSQ";

	/**分理收据冲红**/
	String CWSJ_CH = "CWSJ_CH";

	/**修改运单权限**/
	String MODIFY_HWYD = "MODIFY_HWYD";

	/**
	 * 撤销装载权限
	 */
	String HCZZQD_CANCEL = "HCZZQD_CANCEL";
	
	/**
	 * 单据撤销超级权限
	 */
	String SUP_CANCEL_RIGHT = "SUP_CANCEL_RIGHT";
	
	/**
	 * 托运人发货指令权限
	 */
	String HWYD_DDFH_LISHI = "HWYD_DDFH_LISHI";
	
	/**
	 * 设置托运人发货指令权限
	 */
	String HWYD_DDFH_DENGDAI = "HWYD_DDFH_DENGDAI";
	
	/**
	 * 通知放货权限
	 */
	String HWYD_DDFH_FANGHUO = "HWYD_DDFH_FANGHUO";
	
	/** 
	 * 货运记录录入权限
	 */
	String HYJL_IN = "HYJL_IN";
	
	/**
	 * 装车到站/发站事故描述权限
	 */
	String HYJL_ZDYJ = "HYJL_ZDYJ";
	
	/**
	 * 装车到站/发站运作负责人意见  
	 */
	String HYJLYZYJ = "HYJLYZYJ";
	
	/**
	 * 装车到站/发站单位负责人意见  
	 */
	String HYJLDWYJ = "HYJLDWYJ";
	
	/**
	 * 运营中心处理意见  
	 */
	String HYJL_YYYJ = "HYJL_YYYJ";
	
	/**
	 * 返单录入权限
	 */
	String FDQK_IN = "FDQK_IN";
	
	/**
	 * 返单参数管理权限
	 */
	String FD_YJ_CANSHU = "FD_YJ_CANSHU";
	
	/**
	 * 判断货运记录  GOOD_RECORD_FIND
	 */
	String GOOD_RECORD_FIND = "GOOD_RECORD_FIND";
	
	/**
	 * 发站基础资料维护
	 */
	String BASE_DZ = "BASE_DZ";
	
	/**
	 * 周期性结款查看权限
	 */
	String YSK_CUSFIND = "YSK_CUSFIND";
	
	/**
	 * 周期性结款设置权限
	 */
	String YSK_CUSEDIT = "YSK_CUSEDIT";
}
