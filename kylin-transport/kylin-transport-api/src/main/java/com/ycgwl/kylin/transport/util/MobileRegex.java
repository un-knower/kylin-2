package com.ycgwl.kylin.transport.util;
/**
 * 
  * @Description: 验证手机号是否能够正确匹配
  * @author <a href="mailto:工号108252">万玉杰</a>
  * @date 2017年9月12日 下午2:26:51
  * @version 需求对应版本号
  *
 */
public class MobileRegex {
//	private static String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
	//方便与js校验一致
	private static String regExp = "^1[3457869]\\d{9}$"; 
	
	public static Boolean regexMobile(String tel){
		return tel.matches(regExp);
	}
	
	public static void main(String[] args) {
		String tel = "13232132145";
		System.out.println(regexMobile(tel));
	}
}
