package com.amani.servlet;

import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.amani.dao.AMN_DaoImp;
import com.amani.tools.AcceptMsg;

public class MsgServlet extends HttpServlet {
	public void init() throws ServletException {
		super.init();
		AcceptMsg acceptMsg=new AcceptMsg();
		//WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		//ApplicationContext ac=new FileSystemXmlApplicationContext(this.getServletConfig().getServletContext()+"WEB-INF/applicationContext.xml");
		//AMN_DaoImp amn_DaoImp= (AMN_DaoImp)ac.getBean("amn_Dao");
		//AMN_DaoImp amn_DaoImp=(AMN_DaoImp)context.getBean("amn_Dao");
		//amn_DaoImp.executeQuery("select 1");
		//acceptMsg.setContext(getServletContext());
		//acceptMsg.setStrPath(this.getServletContext().getRealPath("/"));
		Timer timer=new Timer();
		timer.schedule(acceptMsg, 10000,70000);
	}
	@Override
	public void destroy() {
		super.destroy();
	}
	
	
}
