package com.amani.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.amani.service.AdvancedOperations.AC002Service;
import com.amani.tools.CommonTool;

public class LoadMsgInfoServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoadMsgInfoServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
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
		Connection con=null;
        Statement stmt=null;
        ResultSet rs=null;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String call_no = request.getParameter("call_no"); 
        String called_no = request.getParameter("called_no"); 
        String agent_num = request.getParameter("agent_num");  
        String offer_time = request.getParameter("offer_time");  
        String strUserId="";
        MsgInfoTool tool=new MsgInfoTool();
        try
        {
        	String strSql=" select userno from sysuserinfo where callcenterinterface='"+agent_num+"' ";
        	con=tool.getConnection();
        	stmt=con.createStatement();
        	rs=stmt.executeQuery(strSql);
        	if(rs!=null && rs.next())
        	{
        		strUserId=rs.getString("userno");
        	}
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        	
        }
        finally
        {
        	try
        	{
        		if(rs!=null)
        		{
        			rs.close();
        		}
        		if(stmt!=null)
        		{
        			stmt.close();
        		}
        		if(con!=null)
        		{
        			con.close();
        		}
        		rs=null;
        		stmt=null;
        		con=null;
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        		
        	}
        }
        
        System.out.println("进入Servlert....");
        System.out.println(call_no);
        System.out.println(called_no);
        System.out.println(agent_num);
        System.out.println(offer_time);
        System.out.println(strUserId);
        String strSql="insert callwaiting (callbillid,calluserid,callon,calledon,agentnum,offertime,calltype,callstate) values("
        	+CommonTool.quotedStr("0")
    		+","
    		+CommonTool.quotedStr(strUserId)
    		+","
        	+CommonTool.quotedStr(call_no)
			+","
			+CommonTool.quotedStr(called_no)
			+","
			+CommonTool.quotedStr(agent_num)
			+","
			+CommonTool.quotedStr(offer_time)
			+","
			+CommonTool.FormatInteger(0)
			+","
			+CommonTool.FormatInteger(0) +")";
//        String strSql="insert callswaiting(callon,calledon,agentnum,offertime,callstate) values("
//			+CommonTool.quotedStr(call_no)
//			+","
//			+CommonTool.quotedStr(called_no)
//			+","
//			+CommonTool.quotedStr(agent_num)
//			+","
//			+CommonTool.quotedStr(offer_time)
//			+","
//			+CommonTool.FormatInteger(0) +")";
        
        try
        {
        	con=tool.getConnection();
        	stmt=con.createStatement();
        	System.out.println(strSql);
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
		// Put your code here
	}

}
