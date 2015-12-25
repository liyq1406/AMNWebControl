
package com.amani.web;
import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @author LiuJie Jun 24, 2013 10:55:00 AM
 * @version: 1.0
 * @Copyright: AMN
 */
public class AMNServletContextListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent event) {
		
		ServletContext servletContext = event.getServletContext();
	
		String serverMac = com.amani.tools.CommonTool.getMACAddressEx();
		servletContext.setAttribute("server_mac", serverMac);
		
		
		
	}
	
}
