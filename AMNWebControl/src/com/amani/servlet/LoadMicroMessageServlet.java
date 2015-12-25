package com.amani.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amani.tools.CommonTool;

public class LoadMicroMessageServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoadMicroMessageServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
		// Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
//		主叫 被叫 发起时间 挂机时间 录音名    
		String callons=request.getParameter("callons");
		String calledons=request.getParameter("calledons"); 
		String starttime=request.getParameter("starttime"); 
		String endtime=request.getParameter("endtime");
		String agentnums=request.getParameter("agentnums");
		String recordname=request.getParameter("recordname"); 
        System.out.println("进入Servlert....");
        System.out.println(callons);
        System.out.println(calledons);
        System.out.println(starttime);
        System.out.println(endtime);
        System.out.println(recordname);
        MsgInfoTool tool=new MsgInfoTool();
        String strSql="insert recordinfos(callbillids,callons,calledons,agentnums,starttime,endtime,recordname,recordstate) values("
 			+CommonTool.quotedStr(starttime)
			+","
			+CommonTool.quotedStr(callons)
			+","
			+CommonTool.quotedStr(calledons)
			+","
			+CommonTool.quotedStr(agentnums)
			+","
			+CommonTool.quotedStr(starttime)
			+","
			+CommonTool.quotedStr(endtime)
			+","
			+CommonTool.quotedStr(recordname)
			+","
			+CommonTool.FormatInteger(0) +")";
        Connection con=null;
        Statement stmt=null;
        try
        {
        	con=tool.getConnection();
        	stmt=con.createStatement();
        	tool.executeSql(con, stmt, strSql);
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        	
        }
        finally
        {
        	try
        	{
        		if(stmt!=null)
        		{
        			stmt.close();
        		}
        		if(con!=null)
        		{
        			con.close();
        		}
        		stmt=null;
        		con=null;
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        		
        	}
        }
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
	}

}
