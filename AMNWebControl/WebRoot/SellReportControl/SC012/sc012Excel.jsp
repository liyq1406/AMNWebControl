<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.bean.SC012Bean"/>
<jsp:directive.page import="com.amani.action.SellReportControl.SC012Action"/>
<jsp:directive.page import="com.amani.service.SellReportControl.SC012Service"/>
<%
String path = request.getContextPath();
%>
<%
	try{
	
		SC012Action action=new SC012Action();
		action.setStrCurCompId((String)request.getAttribute("strCurCompId"));
		action.setStrCurCompName((String)request.getAttribute("strCurCompName"));
		action.setStrFromDate((String)request.getAttribute("strFromDate"));
		action.setStrToDate((String)request.getAttribute("strToDate"));
		action.setSearchType((Integer)request.getAttribute("searchType"));
		action.setSc012Service((SC012Service)request.getAttribute("sc012Service"));
		action.setLsDataSet((List<SC012Bean>)request.getAttribute("lsDataSet"));
		String fname=action.getStrCurCompId()+"["+action.getStrCurCompName()+"]"+"会员异动明细";
		OutputStream os = response.getOutputStream();//取得输出流
		response.reset();//清空输出流
    	//下面是对中文文件名的处理
		response.setCharacterEncoding("UTF-8");
    	fname = java.net.URLEncoder.encode(fname, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(fname.getBytes("UTF-8"), "GBK") + ".xls");
    	response.setContentType("application/msexcel");//定义输出类型
		
		//传入os
		action.setOs(os);
		action.createExcel();
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<body>
	
	</body>
</html>
