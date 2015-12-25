<%@page import="com.amani.bean.SC015Bean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.bean.GoodsInserTypeAnalysisBean"/>
<jsp:directive.page import="com.amani.action.SellReportControl.SC015Action"/>
<%
String path = request.getContextPath();
%>
<%
	try{
	
		SC015Action action=new SC015Action();
		String fname="年卡销售统计表";
		OutputStream os = response.getOutputStream();//取得输出流
		response.reset();//清空输出流
		action.setLsBeans((List<SC015Bean>)request.getAttribute("lsBeans"));
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
