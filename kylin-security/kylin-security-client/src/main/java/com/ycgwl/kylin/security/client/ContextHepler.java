/**
 * kylin-admin-webapp
 */
package com.ycgwl.kylin.security.client;

import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.security.entity.User;
import org.apache.shiro.SecurityUtils;

/**
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-05-04 14:28:11
 */
public abstract class ContextHepler {
	
	//========================================================================
	
	public static User getCurrentUser() throws BusinessException{
		
	   org.apache.shiro.subject.Subject subject=	SecurityUtils.getSubject();
	   User   user = (User) subject.getPrincipal();
	   String company=	user.getCompany();
	
	   //StringUtils.
	   if(user == null){
			throw new BusinessException("用户未登录");
		}
		return user ;

	}
	
	
	public static String getCompanyName() throws BusinessException{
		System.out.println("huo=>");
		System.out.println(getCurrentUser().getCompany());
		return getCurrentUser().getCompany();
	}

}
