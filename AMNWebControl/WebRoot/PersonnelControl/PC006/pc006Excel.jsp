<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.model.Staffchangeinfo"/>
<jsp:directive.page import="com.amani.action.PersonnelControl.PC006Action"/>
<jsp:directive.page import="com.amani.service.PersonnelControl.PC006Service"/>
<%
String path = request.getContextPath();
%>
<%
	try{
	
		PC006Action action=new PC006Action();
		action.setStrCurCompId((String)request.getAttribute("strCurCompId"));
		action.setStrMonth((String)request.getAttribute("strMonth"));
		action.setPc006Service((PC006Service)request.getAttribute("pc006Service"));
		action.setLsDataSetA((List<Staffchangeinfo>)request.getAttribute("lsDataSetA"));
		action.setLsDataSetB((List<Staffchangeinfo>)request.getAttribute("lsDataSetB"));
		action.setLsDataSetC((List<Staffchangeinfo>)request.getAttribute("lsDataSetC"));
		action.setLsDataSetD((List<Staffchangeinfo>)request.getAttribute("lsDataSetD"));
		action.setLsDataSetE((List<Staffchangeinfo>)request.getAttribute("lsDataSetE"));
		String fname="门店人事统计";
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
