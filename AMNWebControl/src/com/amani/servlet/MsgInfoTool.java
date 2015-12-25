package com.amani.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MsgInfoTool 
{
	//加载连接
	public Connection getConnection()
	{
		try
		 { 
				Class.forName("net.sourceforge.jtds.jdbc.Driver"); 
				Connection con=DriverManager.getConnection("jdbc:jtds:sqlserver://10.0.0.9:1433;databaseName=MasterDatabase2014","sa","RUz4mRt?LuEy=@uG");
				return con;
		 }
		catch(Exception e)
		{ 
				e.printStackTrace();
				return null;
		}
	}
	
	//释放连接
	public boolean closeConnection(Connection con)
	{
		try
		 { 
				if(con==null)
					return true;
				con.close();
				return true;
		 }
		catch(Exception e)
		{ 
				e.printStackTrace();
				return false;
		}
	}
	
	public boolean executeSql(Connection con,Statement stmt,String strSql)
	{
			try
			{ 
				stmt=con.createStatement();
				return stmt.execute(strSql);
				
			}
			catch(Exception e)
			{ 
				e.printStackTrace();
				return false;
			}
			
	}
	
	public static ResultSet loadResultSet(Connection con,Statement stmt,String strSql)
	{
		
		try
		{ 
	
			return stmt.executeQuery(strSql);
			 
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
			return null;
		}
		
	}
	public  int update(Connection con,Statement stmt,String strSql){
		int i=0;
		try {
			
			i= stmt.executeUpdate(strSql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		
		return i;
			
	}
	
	public static void main(String[] args) {
		MsgInfoTool wt = new MsgInfoTool();
		
		System.out.println(wt.valdateInfoBand("123123"));
	}
	
	 public  boolean valdateInfoBand(String userId)
	    {
		 	System.out.println(userId);
		 	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	    	MsgInfoTool wxTool=new MsgInfoTool();
	    	Connection con=null;
	    	ResultSet rs=null;
	    	Statement stmt=null;
	    	try
	    	{
	    	
	    		con=wxTool.getConnection();
	    		stmt=con.createStatement();

	        	String strSql="select 1 from userinfo where userid='"+userId+"' and  isnull(bandflag,0)=1 ";
	        	rs=wxTool.loadResultSet(con,stmt, strSql);
	        	if(rs!=null && rs.next())
	        	{
	        		return true;
	        	}
	        	else
	        	{
	        		return false;
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
	    			if(stmt!=null)
	    				stmt.close();
	    			stmt=null;
	    			if(rs!=null)
	    				rs.close();
	    			rs=null;
	    			wxTool.closeConnection(con);
	    			con=null;
	        	}
	    		catch(Exception ex)
	    		{
	    			ex.printStackTrace();
	    		}
	    	}
	    	return true;
	    }
	 
	
}
