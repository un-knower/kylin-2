package com.ycgwl.kylin;
/**
  * @Description: 配置key值
  * @author <a href="mailto:工号@ycgwl.com">姓名全拼</a>
  * @date 2017年9月25日 下午3:13:25
  * @version 需求对应版本号
  *
 */
public interface SystemKey {
	/**
	 *	四川远成
	 */
	String SEND_MESSAGE_COMPANY1 ="四川远成";
	
	/**
	 * 沱牌项目的发送短信的客户编码
	 */
	String KHBM = "MYSHS0067##";
	
	/**
	 * 快运短信接口
	 */
	String PREFIXURL = "transportation.prefix";
	
	/**
	 * 短信接口登陆用户名
	 */
	String USERNAME = "transportation.username";
	
	/**
	 * 短信接口登陆密码
	 */
	String PASSWORD="transportation.password";
	
	/**
	 * 短信接口用户角色
	 */
	String ROLES = "transportation.roles";
	
	/**
	 * 回单图片上传路径
	 */
	String REORDER_IMAGE_PATH = "reorder.image.path";
	
	/**
	 * 图片服务器地址
	 */
	String FILE_SERVER_URL = "file.server.url";
	
	/**
	 * 单点登录
	 */	
	String singleLogin = "transportation.login";
	
	/**
	 * 登出
	 */	
	String singleLoginOut="transportation.loginOut";
}
