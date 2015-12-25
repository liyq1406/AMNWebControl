<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.bean.ProjectTypeAnalysis"/>
<jsp:directive.page import="com.amani.action.SellReportControl.SC011Action"/>
<jsp:directive.page import="com.amani.service.SellReportControl.SC011Service"/>
<%
String path = request.getContextPath();
%>
<%
	try{
	
		SC011Action action=new SC011Action();
		action.setStrCurCompId((String)request.getAttribute("strCurCompId"));
		action.setStrFromDate((String)request.getAttribute("strFromDate"));
		action.setStrToDate((String)request.getAttribute("strToDate"));
		action.setSc011Service((SC011Service)request.getAttribute("sc011Service"));
		action.setLsDataSet((List<ProjectTypeAnalysis>)request.getAttribute("lsDataSet"));
		String fname="项目消费业绩统计";
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
