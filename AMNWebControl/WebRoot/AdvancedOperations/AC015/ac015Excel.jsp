<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.model.StaffMachineinfo"/>
<jsp:directive.page import="com.amani.action.AdvancedOperations.AC015Action"/>
<jsp:directive.page import="com.amani.service.AdvancedOperations.AC015Service"/>
<%
String path = request.getContextPath();
%>
<%
	try{
	
		AC015Action action=new AC015Action();
		action.setStrCurCompId((String)request.getAttribute("strCurCompId"));
		action.setStrCurCompName((String)request.getAttribute("strCurCompName"));
		action.setStrFromDate((String)request.getAttribute("strFromDate"));
		action.setStrToDate((String)request.getAttribute("strToDate"));
		action.setAc015Service((AC015Service)request.getAttribute("ac015Service"));
		action.setLsDateSet((List<StaffMachineinfo>)request.getAttribute("lsDateSet"));
		String fname=action.getStrCurCompId()+"["+action.getStrCurCompName()+"]"+"员工考勤";
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
