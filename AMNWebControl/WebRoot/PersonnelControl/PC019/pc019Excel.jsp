<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.model.Mstafftargetinfo"/>
<jsp:directive.page import="com.amani.action.PersonnelControl.PC019Action"/>
<jsp:directive.page import="com.amani.service.PersonnelControl.PC019Service"/>
<%
String path = request.getContextPath();
%>
<%
	try{
	
		PC019Action action=new PC019Action();
		action.setStrCurCompId((String)request.getAttribute("strCurCompId"));
		action.setStrFromDate((String)request.getAttribute("strFromDate"));
		action.setBillstate((Integer)request.getAttribute("billstate"));
		action.setPc019Service((PC019Service)request.getAttribute("pc019Service"));
		action.setLsDataSet((List<Mstafftargetinfo>)request.getAttribute("lsDataSet"));
		
		String fname="人事薪资录入统计";
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
