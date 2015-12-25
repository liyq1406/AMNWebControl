package com.amani.action;

import java.util.Map;

import org.apache.log4j.Logger;

import com.amani.tools.UserInformation;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


/**
 * 
 * @author LiuJie Jun 24, 2013 10:18:17 AM
 * @version: 1.0
 * @Copyright: AMN
 */
public class ValidateLoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	
	private transient Logger logger = Logger.getLogger( ValidateLoginInterceptor.class.getName() );

	public static final String LOGIN_PAGE = "login";

	public String intercept(ActionInvocation actionInvocation) throws Exception {

		logger.debug("begin check login interceptor!");

		// 对LoginAction不做该项拦截
		
//		userInfo
		
		

		Object action = actionInvocation.getAction();

		if ( action instanceof LoginAction ) {

			logger.debug(" this is login action ");

			return actionInvocation.invoke();

		}

		// 确认Session中是否存在LOGIN

		Map session = actionInvocation.getInvocationContext().getSession();
		
		Object objUserInfo = session.get( "userInfo" );
		if(objUserInfo == null)
			return LOGIN_PAGE;
		
		UserInformation userInfo = (UserInformation) objUserInfo;
//		SystemInformation sysInfo = (SystemInformation) session.get( sys_Info );
		

		if ( userInfo != null ) {

			// 存在的情况下进行后续操作。

			logger.debug("already login!");

			return actionInvocation.invoke();

		} else {

			// 否则终止后续操作，返回LOGIN

			logger.debug("no login, forward login page!");

			return LOGIN_PAGE;

		}
	}

}